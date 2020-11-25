package com.sjgs.ping.lib.event.protocol;

import java.net.InetSocketAddress;

public class UnknownCommandEvent extends NetEvent {

	public UnknownCommandEvent(InetSocketAddress ip, String cmd, String id, String payload) {
		super(ip);
	}
	
}
