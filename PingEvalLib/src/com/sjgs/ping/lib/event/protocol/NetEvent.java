package com.sjgs.ping.lib.event.protocol;

import java.net.InetSocketAddress;

import com.sjgs.ping.lib.event.Event;

public abstract class NetEvent implements Event {
	
	protected final InetSocketAddress ip;
	
	public NetEvent(InetSocketAddress ip) {
		this.ip = ip;
	}
	
	public InetSocketAddress getSenderIp() {
		return ip;
	}
}
