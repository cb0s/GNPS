package com.github.cb0s.gnps.lib.event.net;

import com.github.cb0s.gnps.lib.net.NetPacket;

/**
 * 
 * @author Cedric Boes
 *
 */
public final class PacketReceiveEvent extends PacketEvent {

	/**
	 * 
	 * 
	 */
	private final byte incomingId;
	
	/**
	 * 
	 * @param msg
	 * @param incomingId
	 */
	public PacketReceiveEvent(NetPacket msg, byte incomingId) {
		super(msg);
		this.incomingId = incomingId;
	}
	
	/**
	 * 
	 * @return
	 */
	public byte getIncomingId() {
		return incomingId;
	}

}
