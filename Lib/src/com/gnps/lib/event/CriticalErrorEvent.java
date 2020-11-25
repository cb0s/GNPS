package com.gnps.lib.event;

public class CriticalErrorEvent<T extends Throwable> extends ErrorEvent<T> {
	public final boolean stopProgram;
	
	public CriticalErrorEvent(T error) {
		this(error, true);
	}
	
	public CriticalErrorEvent(T error, boolean stopProgram) {
		super(error);
		this.stopProgram = stopProgram;
	}
	
	public boolean stopProgram() {
		return stopProgram;
	}
}
