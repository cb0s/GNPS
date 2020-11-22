package com.sjgs.ping.lib.event.protocol;

import java.net.InetSocketAddress;

public class ResponseEvent extends NetEvent {
	public ResponseEvent(InetSocketAddress ip, byte parsedId, String realPayload) {
		super(ip);
	}
}
