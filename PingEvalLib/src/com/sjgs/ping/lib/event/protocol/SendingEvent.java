package com.sjgs.ping.lib.event.protocol;

import java.net.InetSocketAddress;

public class SendingEvent extends NetEvent {
	public SendingEvent(InetSocketAddress ip, String payload) {
		super(ip);
	}
}
