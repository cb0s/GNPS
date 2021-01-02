package com.github.cb0s.gnps.lib.event.net;

import com.github.cb0s.gnps.lib.net.NetPacket;

public class PacketEvent extends NetEvent {

	private final String raw;
	
	public PacketEvent(NetPacket msg) {
		super(msg.getSenderAdress());
		this.raw = msg.getRawMessage();
	}
	
	public String getRawMessage() {
		return raw;
	}
	
	public NetPacket toNetPacket() {
		return new NetPacket(addr, raw);
	}

}
