package com.gnps.lib.net;

import java.net.InetSocketAddress;

public final class NetPacket {
	private final InetSocketAddress addr;
	private final String raw;
	/**
	 * According to https://www.javaadvent.com/2019/12/measuring-time-from-java-to-kernel-and-back.html the delay is in
	 * an acceptable range. Currently for debug reasons maybe later part of PONG if results are not promising.
	 */
	private final long nanoTime;
	
	public NetPacket(InetSocketAddress addr, String raw) {
		this(addr, raw, System.nanoTime());
	}
	
	public NetPacket(InetSocketAddress addr, String raw, long nanoTime) {
		this.addr = addr;
		this.raw = raw;
		this.nanoTime = nanoTime;
	}
	
	public InetSocketAddress getSenderAdress() {
		return addr;
	}
	
	public String getRawMessage() {
		return raw;
	}
	
	public long getTimeSignature() {
		return nanoTime;
	}
}
