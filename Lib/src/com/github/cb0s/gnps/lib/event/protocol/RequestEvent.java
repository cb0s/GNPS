package com.github.cb0s.gnps.lib.event.protocol;

import java.net.InetSocketAddress;

import com.github.cb0s.gnps.lib.event.net.NetEvent;

/**
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public class RequestEvent extends NetEvent {
	public RequestEvent(InetSocketAddress ip, byte id, String cmd) {
		super(ip);
	}
}
