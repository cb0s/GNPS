package com.gnps.lib.event.protocol;

import java.net.InetSocketAddress;

import com.gnps.lib.event.net.NetEvent;

public class AcknowledgeEvent extends NetEvent {
	public AcknowledgeEvent(InetSocketAddress ip, byte id) {
		super(ip);
	}
}
