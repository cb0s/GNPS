package com.gnps.lib.event.protocol;

import java.net.InetSocketAddress;

import com.gnps.lib.event.net.NetEvent;
import com.gnps.lib.protocol.ErrorCodes;

public class ProtocolErrorEvent extends NetEvent {
	public ProtocolErrorEvent(InetSocketAddress ip, byte id, ErrorCodes[] codes) {
		super(ip);
	}
}
