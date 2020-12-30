package com.gnps.lib.exception;

import com.gnps.lib.event.Event;

/**
 * Indicates that an {@link Event} was not handled by the user of this library.<br>
 * All {@link Event} that might occur must be handled!
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public class UnhandledEventException extends RuntimeException {

	/**
	 * ID for serialization.
	 */
	private static final long serialVersionUID = -6007634503252730665L;

	/**
	 * Creates a new {@link UnhandledEventException}.<br>
	 * Overrides {@link RuntimeException#RuntimeException() super()}.
	 */
	public UnhandledEventException() {
		super();
	}

	/**
	 * Creates a new {@link UnhandledEventException}.<br>
	 * Overrides {@link RuntimeException#RuntimeException(String, Throwable, boolean, boolean) super(String, Throwable, boolean, boolean)}.
	 */
	public UnhandledEventException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Creates a new {@link UnhandledEventException}.<br>
	 * Overrides {@link RuntimeException#RuntimeException(String, Throwable) super(String, Throwable)}.
	 */
	public UnhandledEventException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new {@link UnhandledEventException}.<br>
	 * Overrides {@link RuntimeException#RuntimeException(String) super(String)}.
	 */
	public UnhandledEventException(String message) {
		super(message);
	}

	/**
	 * Creates a new {@link UnhandledEventException}.<br>
	 * Overrides {@link RuntimeException#RuntimeException(Throwable) super(Throwable)}.
	 */
	public UnhandledEventException(Throwable cause) {
		super(cause);
	}
	
}
