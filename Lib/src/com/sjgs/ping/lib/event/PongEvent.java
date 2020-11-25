package com.sjgs.ping.lib.event;

import java.net.InetSocketAddress;

public class PongEvent extends AcknowledgeEvent {

	public PongEvent(InetSocketAddress ip, byte id) {
		super(ip, id);
	}

}
