package com.github.cb0s.gnps.lib.event.protocol;

import com.github.cb0s.gnps.lib.event.net.PacketEvent;
import com.github.cb0s.gnps.lib.net.NetPacket;

public class SendingEvent extends PacketEvent{

	public SendingEvent(NetPacket msg) {
		super(msg);
	}
}
