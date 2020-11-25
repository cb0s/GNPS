package com.sjgs.ping.lib.event;

/**
 * {@link Runnable Runnable-Wrapper} for the {@link EventHandler} which allows it to be used by a designated
 * {@link Thread}.<br>
 * The {@link #run() run()-method} simply calls {@link EventHandler#callHandler(Event) callHandler(Event)}.
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public class EventHandlerRunner implements Runnable {
	/**
	 * {@link EventHandler} which is handled by this wrapper.
	 */
	private final EventHandler<? extends Event> handler;
	/**
	 * {@link Event} which must be handled by {@link #handler}.
	 */
	private final Event event;
	
	/**
	 * Creates a new {@link Runnable}-object which can be used to initialize a new {@link Thread}.
	 * 
	 * @param handler {@link #handler}
	 * @param event {@link #event}
	 */
	public EventHandlerRunner(EventHandler<? extends Event> handler, Event event) {
		this.handler = handler;
		if (handler.getEventClass().isInstance(event)) {
			this.event = event;
		} else {
			throw new IllegalArgumentException("The handler responsible for event does not support the given type!");
		}
	}
	
	@Override
	public void run() {
		handler.callHandler(event);
	}
}
