package com.gnps.lib.event.net;

import java.net.InetSocketAddress;

import com.gnps.lib.event.ErrorEvent;
import com.gnps.lib.protocol.definition.MessageType;

public class BadNetEvent<T extends Throwable> extends ErrorEvent<T> implements NetBaseEvent {

	public BadNetEvent(InetSocketAddress ip, byte id, T t, MessageType err, String payload) {
		super(t);
	}

	@Override
	public InetSocketAddress getSenderAddress() {
		return null;
	}

}
