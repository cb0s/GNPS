package com.gnps.lib.protocol.definition;

import java.net.InetSocketAddress;

import com.gnps.lib.event.EventBus;
import com.gnps.lib.event.net.BadNetEvent;
import com.gnps.lib.event.protocol.ResponseEvent;

/**
 * Represents the response for {@link Req requests} in this protocol.<br>
 * <br>
 * The payload contains:
 * <ul>
 * 	<li>first byte: ID of the message that gets response</li>
 * 	<li>rest: actual payload which can differ depending on the {@link Req request}</li>
 * </ul>
 * The different parts of the payload are not separated but the msg-id is always just the first byte.<br>
 * <br>
 * <em>Messages would look somehow like this: "RES>X>XSOME_DATA"</em>
 * 
 * @author Cedric Boes
 * @version 1.1
 */
public final class Res extends Command {

	/**
	 * See {@link Command#Command(InetSocketAddress, byte, String)} for more details.
	 */
	public Res(InetSocketAddress ip, byte id, String payload) {
		super(ip, id, payload);
		checkPayloadLowerBound(2);
	}

	@Override
	public boolean handle(EventBus bus) {
		try {
			byte parsedId = (byte) payload.charAt(0);
			String realPayload = payload.substring(1);
			
			ResponseEvent re = new ResponseEvent(ip, parsedId, realPayload);
			bus.publish(re);
			
			return true;
		} catch (NumberFormatException e) {
			BadNetEvent<NumberFormatException> be =
					new BadNetEvent<NumberFormatException>(ip, id, e, MessageType.RES, payload);
			bus.publish(be);
			return false;	
		}
	}
	
}
