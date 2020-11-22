package com.sjgs.ping.lib.protocol.definition;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.sjgs.ping.lib.event.EventBus;
import com.sjgs.ping.lib.event.protocol.AcknowledgeEvent;
import com.sjgs.ping.lib.event.protocol.BadNetEvent;

/**
 * Represents the Acknowledge message of the protocol.<br>
 * The payload should simply contain the id of the successful message.<br>
 * <br>
 * <em>An example is e.g. "ACK>X>X"</em>
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public final class Ack extends Command {

	/**
	 * See {@link Command#Command(InetAddress, byte, String)} for more details.<br>
	 * Payload-length must be exactly 1.
	 */
	public Ack(InetSocketAddress ip, byte id, String payload) {
		super(ip, id, payload);
		checkPayloadInBounds(1, 1);
	}

	@Override
	public boolean handle(EventBus bus) {
		try {
			byte parsedId = (byte) payload.charAt(0);
			AcknowledgeEvent ack = new AcknowledgeEvent(ip, parsedId);
			bus.publish(ack);
			return true;
		} catch(NumberFormatException e) {
			BadNetEvent be = new BadNetEvent(ip, id, e, MessageType.ACK, payload);
			bus.publish(be);
			return false;
		}
	}
}
