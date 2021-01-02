package com.github.cb0s.gnps.lib.event.protocol;

import java.net.InetSocketAddress;

import com.github.cb0s.gnps.lib.event.net.NetEvent;

public class HeartbeatEvent extends NetEvent {

	public HeartbeatEvent(InetSocketAddress ip) {
		super(ip);
	}

}
