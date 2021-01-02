package com.github.cb0s.gnps.lib.event.net;

import com.github.cb0s.gnps.lib.net.NetPacket;

public class BadPacketEvent extends PacketEvent {

	public BadPacketEvent(NetPacket msg) {
		super(msg);
	}
	
}
