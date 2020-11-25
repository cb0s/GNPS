package com.gnps.lib.event.net;

import java.net.InetSocketAddress;

import com.gnps.lib.event.Event;

interface NetBaseEvent extends Event {
	public InetSocketAddress getSenderAddress();
}
