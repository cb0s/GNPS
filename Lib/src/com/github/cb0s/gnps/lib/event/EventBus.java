package com.github.cb0s.gnps.lib.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.github.cb0s.gnps.lib.event.protocol.PingEvent;
import com.github.cb0s.gnps.lib.exception.UnhandledEventException;

/**
 * A simple implementation of an Event-Bus which notifies all {@link EventHandler handlers} linked to a specific
 * {@link Event}.<br>
 * This allows for a reactive programming style which leads to minimal occupation of resources if being idle.<br>
 * <br>
 * To handle the internal {@link ThreadPoolExecutor thread-pool} it is recommended to make use of the {@link #close()}
 * before exiting the application!
 * 
 * @author Cedric Boes
 * @version 1.2
 */
public class EventBus implements AutoCloseable {

	/**
	 * Map of the List of all {@link EventHandler handlers} linked to the handled {@link Event event-class}.
	 */
	private final HashMap<Class<? extends Event>, List<EventHandler<? extends Event>>> handlers;
	/**
	 * Map of the List of all {@link EventHandler handlers} linked to the handled {@link Event event-class} which can
	 * also run as high-priority (do not need a designated {@link Thread}).
	 */
	private final HashMap<Class<? extends Event>, List<EventHandler<? extends Event>>> highPrioHandlers;
	/**
	 * A Pool of {@link Thread threads} which is used to handle the incoming {@link Event events}
	 */
	private final ThreadPoolExecutor threadPool;

	/**
	 * {@link EventHandler} for all unhandled {@link Event events} for this {@link EventBus}.<br>
	 * To register this handler use {@link #registerHandleUnhandled(EventHandler)}.
	 */
	private EventHandler<Event> unhandledHandler;
	
	/**
	 * Indicates if {@link #close(int)} has been called, yet.
	 */
	private boolean closed;

	/**
	 * Creates a new {@link EventBus} with a new {@link ThreadPoolExecutor ThreadPool} and the given handlers.<br>
	 * <br>
	 * <em>Note: All parameters are final and must not be changed during runtime!</em>
	 * 
	 * @param handlers			used to initialize {@link #handlers} and {@link #highPrioHandlers}
	 * @param parallelThreads	constant amount of {@link Thread threads} in the {@link #threadPool}
	 * @param maxThreads		maximum amount of {@link Thread threads} in the {@link #threadPool}
	 */
	public EventBus(List<EventHandler<? extends Event>> handlers, int parallelThreads, int maxThreads) {
		this(handlers, new ThreadPoolExecutor(
				parallelThreads, maxThreads, 5, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(50)));
	}
	
	/**
	 * Creates a new {@link EventBus} with the given {@link ThreadPoolExecutor ThreadPool} and the given handlers.<br>
	 * <br>
	 * <em>Note: All parameters are final and must not be changed during runtime!</em>
	 * 
	 * @param handlers 		used to initializes {@link #handlers} and {@link #highPrioHandlers}
	 * @param threadPool 	used to initialize {@link #threadPool}
	 * @throws IllegalArgumentException if either one of the given arguments is <code>null</code> or handlers is empty
	 */
	public EventBus(List<EventHandler<? extends Event>> handlers, ThreadPoolExecutor threadPool) {
		if (handlers == null || threadPool == null || handlers.size() == 0) {
			throw new IllegalArgumentException("The given ThreadPool and the EventHandler-List must not be null or "
					+ "empty!");
		}
		
		this.threadPool = threadPool;

		this.handlers = new HashMap<Class<? extends Event>, List<EventHandler<? extends Event>>>();
		this.highPrioHandlers = new HashMap<Class<? extends Event>, List<EventHandler<? extends Event>>>();

		handlers.stream()
		.forEach(eh -> {
			addToList(this.handlers, eh);
			if (eh.canRunHighPrio()) {
				addToList(this.highPrioHandlers, eh);
			}
		});
		
		this.closed = false;
	}

	/**
	 * Notifies all {@link EventHandler handlers} that can handle the given {@link Event}.<br>
	 * <br>
	 * If the given {@link Event} cannot be handled by the {@link EventBus} an {@link UnhandledEventException} will be
	 * thrown in order to inform about the problem. To avoid this behavior register the {@link #unhandledHandler}.<br>
	 * <br>
	 * As for reasons described <a href=https://stackoverflow.com/questions/24054773/java-8-streams-multiple-filters-vs-complex-condition>
	 * here</a> this does not use parallel streams as it is very unlikely to get above 10_000
	 * {@link EventHandler handlers} for one {@link EventBus}.<br>
	 * <br>
	 * The difference to {@link #priorityPublish(Event)} is that this does use Threads managed by {@link #threadPool} to
	 * run the {@link EventHandler handlers} in order to clear the {@link Thread#currentThread()}.
	 * 
	 * @param event to handle
	 * @throws UnhandledEventException if there is no {@link EventHandler} for the given {@link Event} and no
	 * 			{@link #unhandledHandler} has been registered
	 */
	public void publish(Event event) {
		// Uses Handler Threads to do the work -> might be a little bit more latency but overall better performance
		List<? extends EventHandler<? extends Event>> handlerList = handlers.get(event.getClass());
		if (handlerList == null || handlerList.isEmpty()) {
			if (unhandledHandler == null) {
				throw new UnhandledEventException("No EventHandler for " + event.getClass().getName() + " registered!");
			} else {
				unhandledHandler.callHandler(event);
			}
		}

		handlerList.stream().forEach(
				eh -> threadPool.submit(new EventHandlerRunner(eh, event)));
	}

	/**
	 * Notifies all {@link EventHandler handlers} that can handle the {@link Event} that also support high-priority
	 * (usually means low latency tasks).<br>
	 * <br>
	 * If the given {@link Event} cannot be handled by the {@link EventBus} an {@link UnhandledEventException} will be
	 * thrown in order to inform about the problem. To avoid this behavior register the {@link #unhandledHandler}.
	 * This one however will run in its designated {@link Thread} and will no be a priority in that sense.<br>
	 * <br>
	 * The difference between this and {@link #publish(Event)} is that {@link #priorityPublish(Event)} runs all
	 * {@link EventHandler handlers} in the current Thread, which does have an impact on overall performance.<br>
	 * The advantage however is that the latency until the {@link Event} gets handled will be reduced even more which is
	 * especially important for {@link Event events} which require ultra-low latency (e.g. {@link PingEvent}).
	 * 
	 * @param event to handle
	 * @throws UnhandledEventException if there is no ultra-low latency {@link EventHandler} for the given {@link Event}
	 */
	public void priorityPublish(Event event) {
		// FOR LIGHTWEIGHT TASKS ONLY!
		// Runs directly in the same Thread -> slightly faster but blocks the Thread for more incoming messages
		List<? extends EventHandler<? extends Event>> handlerList = highPrioHandlers.get(event.getClass());
		if (handlerList == null || handlerList.isEmpty()) {
			if (unhandledHandler == null) {
				throw new UnhandledEventException("No EventHandler supporting high priority for "
						+ event.getClass().getName() + " registered!");
			} else {
				threadPool.submit(new EventHandlerRunner(unhandledHandler, event));
			}

		}

		handlerList.stream().forEach(
				eh -> eh.callHandler(event));
	}

	/**
	 * Initiates shutdown of all Threads currently in the {@link #threadPool}.<br>
	 * Waiting for 5 sec. to complete shutdown otherwise the running tasks will be interrupted.<br>
	 * <br>
	 * Overrides {@link AutoCloseable#close() close()}.
	 */
	@Override
	public void close() {
		close(5);
	}

	/**
	 * Basically {@link #close()} with a custom timeout and a return to inform if shutdown was successful.
	 * 
	 * @param timeout (in sec.) after which all remaining Threads will be killed
	 * @returns if interrupted while waiting for the {@link EventHandler handlers} to finish
	 */
	public boolean close(int timeout) {
		threadPool.shutdown();
		closed = true;

		try {
			if (!threadPool.awaitTermination(timeout, TimeUnit.SECONDS)) {
				threadPool.shutdownNow();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Returns if {@link #closed(int)} has already been called and for that sense, whether the {@link EventBus} is still
	 * alive.
	 * 
	 * @return {@link #closed}
	 */
	public boolean isClosed() {
		return closed;
	}

	/**
	 * Registers a new {@link EventHandler} for unregistered events.<br>
	 * It will be called in every case of an unhandled {@link Event}.<br>
	 * <br>
	 * If {@link #unhandledHandler} is <code>null</code> and an Event is still unhandled an
	 * {@link UnhandledEventException} will be thrown!
	 * 
	 * @param handler new {@link #unhandledHandler}
	 */
	public void registerHandleUnhandled(EventHandler<Event> handler) {
		this.unhandledHandler = handler;
	}

	/**
	 * Handles the initialization of {@link #handlers} and {@link #highPrioHandlers}.<br>
	 * If no entry is linked to the {@link Event} handled by the {@link EventHandler} a new entry is being created and
	 * if necessary the associated {@link List} (here always {@link ArrayList} for performance reasons).
	 * 
	 * @param map either {@link #handlers} or {@link #highPrioHandlers}
	 * @param eh {@link EventHandler} to add
	 */
	private void addToList(HashMap<Class<? extends Event>, List<EventHandler<? extends Event>>> map,
			EventHandler<? extends Event> eh) {
		List<EventHandler<? extends Event>> l = this.handlers.get(eh.getEventClass());
		if (l == null) {
			this.handlers.put(eh.getEventClass(), (l = new ArrayList<EventHandler<? extends Event>>()));
		}
		l.add(eh);
	}
}
