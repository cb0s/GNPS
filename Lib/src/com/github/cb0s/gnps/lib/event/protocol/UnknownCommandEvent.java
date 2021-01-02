package com.github.cb0s.gnps.lib.event.protocol;

import java.net.InetSocketAddress;

import com.github.cb0s.gnps.lib.event.net.NetEvent;

public class UnknownCommandEvent extends NetEvent {

	public UnknownCommandEvent(InetSocketAddress ip, String cmd, String id, String payload) {
		super(ip);
	}
	
}
