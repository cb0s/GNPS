package com.github.cb0s.gnps.lib.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;

import com.github.cb0s.gnps.lib.Specifications;
import com.github.cb0s.gnps.lib.event.CriticalErrorEvent;
import com.github.cb0s.gnps.lib.event.ErrorEvent;
import com.github.cb0s.gnps.lib.event.EventBus;
import com.github.cb0s.gnps.lib.event.net.PacketEvent;
import com.github.cb0s.gnps.lib.protocol.MessageIdGenerator;
import com.github.cb0s.gnps.lib.protocol.definition.MessageType;

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
	 * MessageIdGenerator[send, receive]
	 */
	private final HashMap<InetSocketAddress, MessageIdGenerator[]> idGens;
	
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
		incomingBuffer = new byte[Specifications.MAX_PACKET_SIZE];
		incomingPacket = new DatagramPacket(incomingBuffer, incomingBuffer.length);
		idGens = new HashMap<InetSocketAddress, MessageIdGenerator[]>();
		
		this.bus = bus;
	}

	/**
	 * Overrides {@link NetReceiver#receive()}. -> publishes
	 * @return 
	 */
	@Override
	public NetPacket receive() {
		NetPacket packet = new NetPacket(null, null);

		if (socket.isClosed()) {
			System.err.println("UDP-Adapter is already closed!");
			bus.priorityPublish(new CriticalErrorEvent<IOException>(new IOException("UDP-Adapter is already closed!")));
		} else {
			for (int i = 0; i < 4; i++) {
				try {
					socket.receive(incomingPacket);
					InetSocketAddress addr = new InetSocketAddress(incomingPacket.getAddress(),
							incomingPacket.getPort());
					String msg = new String(incomingPacket.getData(), incomingPacket.getOffset(),
							incomingPacket.getLength());
					packet = new NetPacket(addr, msg);
					break;
				} catch (IOException e) {
					if (i < 3) {
						if (i == 0) {
							System.err.println("UDP-Receiving Error!");
						}
						
						ErrorEvent<IOException> ee = new ErrorEvent<IOException>((IOException) e);
						bus.publish(ee);
					} else {
						// is this necessary?
						System.err.println("Critical UDP-Error! Network-Socket was unable to read!");
						
						CriticalErrorEvent<IOException> udpError = new CriticalErrorEvent<IOException>(e);
						bus.priorityPublish(udpError);
					}
				}
			}
			
			bus.publish(new PacketEvent(packet));
		}

		return packet;
	}

	/**
	 * 
	 */
	@Override
	public byte send(InetSocketAddress address, MessageType type, String payload) {
		if (socket.isClosed()) {
			System.err.println("UDP-Adapter is already closed!");
			bus.priorityPublish(new CriticalErrorEvent<IOException>(new IOException("UDP-Adapter is already closed!")));
		}
		
		byte id = getNewMessageId(address, false);
		String raw = type.name() + ">" + id + payload != null ? ">" + payload : "";
		byte[] bytes = raw.getBytes();
		DatagramPacket p = new DatagramPacket(bytes, bytes.length, address);
		
		for (int i = 0; i < 4; i++) {
			try {
				socket.send(p);
				break;
			} catch (IOException e) {
				if (i == 3) {
					if (i == 0) {
						System.err.println("UDP-Sending Error! (Packet \"" + payload + "\"->" + address.toString() +
								")");
					}
					ErrorEvent<IOException> ee = new ErrorEvent<IOException>(e);
					bus.publish(ee);
				} else {
					// is this necessary?
					System.err.println("Critical UDP-Error! Network-Socket was unable to send!");
					
					CriticalErrorEvent<IOException> udpError = new CriticalErrorEvent<IOException>(e);
					bus.priorityPublish(udpError);
				}
			}
		}
		
		return id;
	}
	
	/**
	 * 
	 * @param address
	 * @param receive
	 * @return
	 */
	private byte getNewMessageId(InetSocketAddress address, boolean receive) {
		if (!idGens.containsKey(address)) {
			MessageIdGenerator[] newGens = new MessageIdGenerator[] {
					new MessageIdGenerator(),	// send
					new MessageIdGenerator()	// receive
			};
			idGens.put(address, newGens);
		}
		
		return (receive ? idGens.get(address)[1] : idGens.get(address)[0]).getNewId();
	}
}
