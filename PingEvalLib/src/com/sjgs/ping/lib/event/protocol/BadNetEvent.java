package com.sjgs.ping.lib.event.protocol;

import java.net.InetSocketAddress;

import com.sjgs.ping.lib.protocol.definition.MessageType;

public class BadNetEvent extends NetEvent {
	public BadNetEvent(InetSocketAddress ip, byte id, Throwable t, MessageType err, String payload) {
		super(ip);
	}
}
