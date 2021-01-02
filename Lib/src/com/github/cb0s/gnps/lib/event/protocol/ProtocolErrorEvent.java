package com.github.cb0s.gnps.lib.event.protocol;

import java.net.InetSocketAddress;

import com.github.cb0s.gnps.lib.event.net.NetEvent;
import com.github.cb0s.gnps.lib.protocol.definition.ErrorCodes;

public class ProtocolErrorEvent extends NetEvent {
	public ProtocolErrorEvent(InetSocketAddress ip, byte id, ErrorCodes[] codes) {
		super(ip);
	}
}
