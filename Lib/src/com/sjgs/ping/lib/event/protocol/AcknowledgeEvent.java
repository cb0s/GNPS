package com.sjgs.ping.lib.event.protocol;

import java.net.InetSocketAddress;

public class AcknowledgeEvent extends NetEvent {
	public AcknowledgeEvent(InetSocketAddress ip, byte id) {
		super(ip);
	}
}
