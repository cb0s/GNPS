package com.sjgs.ping.lib.exception;

import com.sjgs.ping.lib.event.Event;

/**
 * Indicates that an {@link Event} was not handled by the user of this library.<br>
 * All that can occur must be handled!
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public class UnhandledEventException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6007634503252730665L;

	public UnhandledEventException() {
		super();
	}

	public UnhandledEventException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnhandledEventException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnhandledEventException(String message) {
		super(message);
	}

	public UnhandledEventException(Throwable cause) {
		super(cause);
	}

	
	
}
