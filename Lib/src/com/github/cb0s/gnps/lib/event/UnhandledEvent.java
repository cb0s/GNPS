package com.github.cb0s.gnps.lib.event;

public class UnhandledEvent implements Event {
	
	private final Event e;
	private final Class<? extends Event> type;
	
	public UnhandledEvent(Event e, Class<? extends Event> type) {
		this.e = e;
		this.type = type;
	}
	
	public Event getEvent() {
		return e;
	}
	
	public Class<? extends Event> getType() {
		return type;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Event> T getCasted() {
		return (T) type.cast(e);
	}
}
