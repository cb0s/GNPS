package com.sjgs.ping.lib.event.protocol;

import java.net.InetSocketAddress;

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
