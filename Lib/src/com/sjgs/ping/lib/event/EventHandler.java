package com.sjgs.ping.lib.event;

import java.lang.reflect.ParameterizedType;

/**
 * The EventHandler is designed to handle one specific {@link Event event-type} which means for each incoming
 * {@link Event} of the specified type on the {@link EventBus} {@link #handle(Event)} will be called.<br>
 * <br>
 * Usually {@link #handle(Event)}-call will be outsourced to a new {@link Thread} but for the cases where it gets
 * called from {@link EventBus#priorityPublish(Event)} if this is supported ({@link #runHighPrio} must be
 * <code>true</code> for this).
 * 
 * @author Cedric Boes
 * @version 1.0
 *
 * @param <T> Implementation of the {@link Event} which can be handled by this handler
 */
public abstract class EventHandler<T extends Event> {
	
	/**
	 * Class of the {@link Event event-implementation} which is referenced by the generic type <code>T</code> in
	 * {@link EventHandler}.
	 */
	private final Class<T> eventClazz;
	/**
	 * Indicates if this {@link EventHandler} can run as high priority task in the {@link Thread} publishing the
	 * {@link Event} to the {@link EventBus}.<br>
	 * <br>
	 * High-Priority Tasks should be light-weight because they block the publishing {@link Thread} until they are
	 * handled!
	 */
	private final boolean runHighPrio;
	
	/**
	 * Creates a new non-priority {@link EventHandler} ({@link #runHighPrio} will be <code>false</code>).<br>
	 * {@link EventHandler Handlers} being created by this constructor cannot be used to handle high-priority tasks.
	 */
	public EventHandler() {
		this(false);
	}
	
	/**
	 * Creates a new {@link EventHandler}.<br>
	 * {@link #runHighPrio} will be set to the given value.
	 * 
	 * @param runHighPrio {@link #runHighPrio}
	 */
	@SuppressWarnings("unchecked")
	public EventHandler(boolean runHighPrio) {
		this.runHighPrio = runHighPrio;
		// Let's try this otherwise we will need to use parameter of constructor
		this.eventClazz = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/**
	 * This method will be called if a new Event occurs.
	 * 
	 * @param event to be handled
	 */
	public abstract void handle(T event);
	
	/**
	 * Getter for {@link #eventClazz}.
	 * 
	 * @return {@link #eventClazz}
	 */
	public Class<T> getEventClass() {
		return eventClazz;
	}
	
	/**
	 * Getter for {@link #runHighPrio}
	 * 
	 * @return {@link #runHighPrio}
	 */
	public boolean canRunHighPrio() {
		return runHighPrio;
	}
	
	/**
	 * Calls {@link #handle(Event)} with the given {@link Event} casted to the right type to handle.<br>
	 * If this class is called with the wrong Event-Class (not {@link #eventClazz}) an {@link IllegalArgumentException}
	 * will be thrown.
	 * 
	 * @param event of type {@link #eventClazz}
	 * @throws IllegalArgumentException if the given {@link Event} is not of type {@link #eventClazz}
	 */
	@SuppressWarnings("unchecked")
	void callHandler(Event event) {
		if (eventClazz.isInstance(event)) {
			handle((T) event);
		} else {
			throw new IllegalArgumentException("Given Event must match given eventClazz!");
		}
	}
}