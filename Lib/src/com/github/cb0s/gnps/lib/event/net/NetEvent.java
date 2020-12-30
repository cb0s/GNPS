package com.gnps.lib.event.net;

import java.net.InetSocketAddress;

public abstract class NetEvent implements NetBaseEvent {
	
	protected final InetSocketAddress addr;
	
	public NetEvent(InetSocketAddress addr) {
		this.addr = addr;
	}
	
	public InetSocketAddress getSenderAddress() {
		return addr;
	}
}
