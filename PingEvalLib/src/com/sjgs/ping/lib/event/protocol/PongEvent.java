package com.sjgs.ping.lib.event.protocol;

import java.net.InetSocketAddress;

public class PongEvent extends AcknowledgeEvent {

	public PongEvent(InetSocketAddress ip, byte id) {
		super(ip, id);
	}

}
