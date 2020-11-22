package com.sjgs.ping.lib.protocol.definition;

import java.net.InetSocketAddress;

import com.sjgs.ping.lib.event.EventBus;
import com.sjgs.ping.lib.event.protocol.BadNetEvent;
import com.sjgs.ping.lib.event.protocol.PongEvent;

/**
 * The answer for the {@link Ping}.<br>
 * <br>
 * The payload is the id of the sent Ping-package.<br>
 * <em>Example: "PONG>X>X"</em>
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public class Pong extends Command {
	
	/**
	 * See {@link Command#Command(InetSocketAddress, byte, String)} for more details.
	 */
	public Pong(InetSocketAddress ip, byte id, String payload) {
		super(ip, id, payload);
		checkPayloadInBounds(1, 1);
	}

	@Override
	public boolean handle(EventBus bus) {
		try {
			byte parsedId = (byte) payload.charAt(0);
			
			PongEvent pe = new PongEvent(ip, parsedId);
			bus.publish(pe);
			
			return true;
		} catch (Exception e) {
			BadNetEvent be = new BadNetEvent(ip, id, e, MessageType.PONG, payload);
			bus.publish(be);
			return false;
		}
	}

}
