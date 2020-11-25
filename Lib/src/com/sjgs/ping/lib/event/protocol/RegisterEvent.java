package com.sjgs.ping.lib.event.protocol;

import java.net.InetSocketAddress;

public class RegisterEvent extends NetEvent {
	
	public RegisterEvent(InetSocketAddress ip, byte id, InetSocketAddress regAddress) {
		super(ip);
	}
	
}
