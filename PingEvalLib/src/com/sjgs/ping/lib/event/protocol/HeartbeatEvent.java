package com.sjgs.ping.lib.event.protocol;

import java.net.InetSocketAddress;

public class HeartbeatEvent extends NetEvent {

	public HeartbeatEvent(InetSocketAddress ip) {
		super(ip);
	}

}
