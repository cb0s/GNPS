package com.gnps.lib.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

import com.gnps.lib.Specifications;
import com.gnps.lib.event.CriticalErrorEvent;
import com.gnps.lib.event.ErrorEvent;
import com.gnps.lib.event.EventBus;
import com.gnps.lib.event.net.PacketEvent;
import com.gnps.lib.protocol.definition.MessageType;

/**
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public final class UdpAdapter extends NetAdapter {
	
	/**
	 * 
	 */
	private final DatagramSocket socket;
	
	/**
	 * 
	 */
	private final byte[] incomingBuffer;
	
	/**
	 * 
	 */
	private final DatagramPacket incomingPacket;
	
	/**
	 * 
	 */
	private final EventBus bus;
	
	/**
	 * 
	 * @param port
	 * @throws SocketException
	 */
	public UdpAdapter(int port, EventBus bus) throws SocketException {
		this(port, null, bus);
	}
	
	/**
	 * 
	 * @param port
	 * @param address
	 * @throws SocketException
	 */
	public UdpAdapter(int port, InetAddress address, EventBus bus) throws SocketException {
		socket = new DatagramSocket(port, address);
		incomingBuffer = new byte[Specifications.PACKET_SIZE];
		incomingPacket = new DatagramPacket(incomingBuffer, incomingBuffer.length);
		
		this.bus = bus;
	}

	/**
	 * Overrides {@link NetReceiver#receive()}. -> publishes
	 * @return 
	 */
	@Override
	public NetPacket receive() {
		boolean success = false;
		int count = 0;
		NetPacket packet = new NetPacket(null, null);
		
		do {
			try {
				socket.receive(incomingPacket);
				InetSocketAddress addr = new InetSocketAddress(incomingPacket.getAddress(), incomingPacket.getPort());
				String msg =
						new String(incomingPacket.getData(), incomingPacket.getOffset(), incomingPacket.getLength());
				packet = new NetPacket(addr, msg);
				success = true;
			} catch (IOException e) {
				if (count++ < 3) {
					ErrorEvent<IOException> ee = new ErrorEvent<IOException>((IOException) e);
					bus.publish(ee);
				} else {
					// is this necessary?
					System.err.println("Critical UDP-Error! Network-Socket was unable to read!");
					
					CriticalErrorEvent<IOException> udpError = new CriticalErrorEvent<IOException>(e);
					bus.priorityPublish(udpError);
				}
			}
		} while(!Thread.currentThread().isInterrupted() && success);

		bus.publish(new PacketEvent(packet));
		return packet;
	}

	@Override
	public byte send(InetSocketAddress address, MessageType type, String payload) {
		String raw = type.name() + ">" + payload;
		return 0;
	}
}
