package com.sjgs.ping.lib.event;

import java.net.InetSocketAddress;

import com.sjgs.ping.lib.protocol.ErrorCodes;

public class ErrorEvent extends Event {
	public ErrorEvent(InetSocketAddress ip, byte id, ErrorCodes[] codes) {
		
	}
}
