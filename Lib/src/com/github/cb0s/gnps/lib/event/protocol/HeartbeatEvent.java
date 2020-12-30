package com.gnps.lib.event.protocol;

import java.net.InetSocketAddress;

import com.gnps.lib.event.net.NetEvent;

public class HeartbeatEvent extends NetEvent {

	public HeartbeatEvent(InetSocketAddress ip) {
		super(ip);
	}

}
