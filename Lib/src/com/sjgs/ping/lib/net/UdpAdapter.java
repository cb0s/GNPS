package com.sjgs.ping.lib.net;

import java.net.InetSocketAddress;

import com.sjgs.ping.lib.protocol.definition.MessageType;

/**
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public class UdpAdapter {
	
	/**
	 * 
	 * @param ip
	 * @param type
	 * @param raw
	 * @return
	 */
	public byte send(InetSocketAddress ip, MessageType type, String raw) {
		return 0x0;
	}
	
	/**
	 * 
	 * @param receiver
	 */
	public void addReceiver(UdpReceiver receiver) {
		
	}
}
