package com.sjgs.ping.lib.protocol.definition;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import com.sjgs.ping.lib.event.EventBus;
import com.sjgs.ping.lib.event.protocol.BadNetEvent;
import com.sjgs.ping.lib.event.protocol.RegisterEvent;

/**
 * Represents the Register message for this protocol. It registers different Clients between each other for
 * P2P-Pings.<br>
 * <br>
 * The payload is IP:PORT for the Client that must be registered.<br>
 * <em>Example: "REG>X>[IPv4 or IPv6]:PORT"</em>
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public final class Reg extends Command {

	/**
	 * See {@link Command#Command(InetSocketAddress, byte, String)} for more details.
	 */
	public Reg(InetSocketAddress ip, byte id, String payload) {
		super(ip, id, payload);
		// shortest: [::1]:4 and longest [<45 parts>]:42424
		checkPayloadInBounds(7, 53);
	}

	@Override
	public boolean handle(EventBus bus) {
		try {
			int port = Integer.parseInt(payload.substring(payload.lastIndexOf(':') + 1));
			// Complex for difference between IPv4 and IPv6
			InetAddress address = InetAddress.getByName(
					payload.substring(payload.contains("[") ? 1 : 0, payload.indexOf(']')));
			
			RegisterEvent re = new RegisterEvent(ip, id, new InetSocketAddress(address, port));
			bus.publish(re);
			
			return true;
		} catch (NumberFormatException | StringIndexOutOfBoundsException
				| SecurityException | UnknownHostException e) {
			BadNetEvent be = new BadNetEvent(ip, id, e, MessageType.REG, payload);
			bus.publish(be);
			return false;
		}		
	}

}
