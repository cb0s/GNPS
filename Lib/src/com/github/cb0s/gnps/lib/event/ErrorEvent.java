package com.github.cb0s.gnps.lib.event;

public class ErrorEvent<T extends Throwable> implements Event {
	public final T error;
	
	public ErrorEvent(T error) {
		this.error = error;
	}
	
	public T getError() {
		return error;
	}
}
