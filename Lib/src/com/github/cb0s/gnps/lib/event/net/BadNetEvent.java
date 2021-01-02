package com.github.cb0s.gnps.lib.event.net;

import java.net.InetSocketAddress;

import com.github.cb0s.gnps.lib.event.ErrorEvent;
import com.github.cb0s.gnps.lib.protocol.definition.MessageType;

/**
 * 
 * @author Cedric Boes
 * @version 1.0
 *
 * @param <T>
 */
public class BadNetEvent<T extends Throwable> extends ErrorEvent<T> implements NetBaseEvent {

	/**
	 * 
	 * @param ip
	 * @param id
	 * @param t
	 * @param err
	 * @param payload
	 */
	public BadNetEvent(InetSocketAddress ip, byte id, T t, MessageType err, String payload) {
		super(t);
	}

	@Override
	public InetSocketAddress getSenderAddress() {
		return null;
	}

}
