package com.sjgs.ping.lib.event.protocol;

import java.net.InetSocketAddress;

public class PingEvent extends NetEvent {
	/**
	 * This is a time-critical Event.<br>
	 * If handling takes too long (e.g. all threads of the pool are already occupied), an error regarding
	 * synchronization can be thrown an the result can be ERR(SYNCHRO_ERROR).
	 */
	private final long creationNanoTime;
	
	public PingEvent(InetSocketAddress ip) {
		super(ip);
		
		creationNanoTime = System.nanoTime();
	}
	
	public long getCreationNanoTime() {
		return creationNanoTime;
	}
}
