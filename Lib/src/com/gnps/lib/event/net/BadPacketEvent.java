package com.gnps.lib.event.net;

import com.gnps.lib.net.NetPacket;

public class BadPacketEvent extends PacketEvent {

	public BadPacketEvent(NetPacket msg) {
		super(msg);
	}
	
}
