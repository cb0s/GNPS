package com.sjgs.ping.lib.event.protocol;

import java.net.InetSocketAddress;

import com.sjgs.ping.lib.protocol.ErrorCodes;

public class ProtocolErrorEvent extends NetEvent {
	public ProtocolErrorEvent(InetSocketAddress ip, byte id, ErrorCodes[] codes) {
		super(ip);
	}
}
