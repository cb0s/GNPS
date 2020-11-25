package com.gnps.lib.event.protocol;

import com.gnps.lib.event.net.PacketEvent;
import com.gnps.lib.net.NetPacket;

public class SendingEvent extends PacketEvent{

	public SendingEvent(NetPacket msg) {
		super(msg);
	}
}
