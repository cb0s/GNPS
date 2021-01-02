package com.github.cb0s.gnps.lib.event.protocol;

import java.net.InetSocketAddress;

import com.github.cb0s.gnps.lib.event.net.NetEvent;

public class ResponseEvent extends NetEvent {
	public ResponseEvent(InetSocketAddress ip, byte parsedId, String realPayload) {
		super(ip);
	}
}
