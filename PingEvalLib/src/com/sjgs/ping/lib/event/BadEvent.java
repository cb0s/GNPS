package com.sjgs.ping.lib.event;

import java.net.InetSocketAddress;

import com.sjgs.ping.lib.protocol.definition.MessageTypes;

public class BadEvent extends Event {
	public BadEvent(Throwable t, InetSocketAddress ip, MessageTypes err, byte id, String payload) {
		
	}
}
