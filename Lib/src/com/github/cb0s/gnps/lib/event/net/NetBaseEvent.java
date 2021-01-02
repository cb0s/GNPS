package com.github.cb0s.gnps.lib.event.net;

import java.net.InetSocketAddress;

import com.github.cb0s.gnps.lib.event.Event;

/**
 * 
 * @author Cedric Boes
 * @version 1.0
 */
interface NetBaseEvent extends Event {
	/**
	 * 
	 * @return
	 */
	public InetSocketAddress getSenderAddress();
}
