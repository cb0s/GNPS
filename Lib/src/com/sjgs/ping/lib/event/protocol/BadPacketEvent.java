package com.sjgs.ping.lib.event.protocol;

import java.net.InetSocketAddress;

public class BadPacketEvent extends NetEvent {

	public BadPacketEvent(InetSocketAddress ip, String rawMsg) {
		super(ip);
	}
	
}
