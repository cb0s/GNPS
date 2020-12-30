package com.gnps.lib.event.protocol;

import java.net.InetSocketAddress;

import com.gnps.lib.event.net.NetEvent;

public class RegisterEvent extends NetEvent {
	
	public RegisterEvent(InetSocketAddress ip, byte id, InetSocketAddress regAddress) {
		super(ip);
	}
	
}
