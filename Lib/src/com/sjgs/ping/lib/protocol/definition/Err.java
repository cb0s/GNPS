package com.sjgs.ping.lib.protocol.definition;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Arrays;

import com.sjgs.ping.lib.event.EventBus;
import com.sjgs.ping.lib.event.protocol.BadNetEvent;
import com.sjgs.ping.lib.event.protocol.ProtocolErrorEvent;
import com.sjgs.ping.lib.protocol.ErrorCodes;


/**
 * Represents the Error-message for this protocol.<br>
 * <br>
 * The payload contains:
 * <ul>
 * 	<li>The message id containing the error</li>
 * 	<li>Either one or a list of pre-defined {@link ErrorCodes}</li>
 * </ul>
 * The different parts of the payload are not separated but the msg-id is always just the first byte.<br>
 * <br>
 * <em>Messages would look somehow like this: "ERR>X>X123" or "ERR>X>X[124;124;124]"</em>
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public final class Err extends Command {
	
	/**
	 * See {@link Command#Command(InetAddress, byte, String)} for more details.
	 */
	public Err(InetSocketAddress ip, byte id, String payload) {
		super(ip, id, payload);
		checkPayloadLowerBound(1);
	}

	@Override
	public boolean handle(EventBus bus) {
		try {
			byte id = (byte) payload.charAt(0);
			
			ErrorCodes[] codes = Arrays.stream(payload.substring(1).split(";"))
										.map(s -> ErrorCodes.getCode(Integer.parseInt(s)))
										.toArray(ErrorCodes[]::new);
			
			ProtocolErrorEvent ee = new ProtocolErrorEvent(ip, id, codes);
			bus.publish(ee);
			return true;
		} catch(NumberFormatException | StringIndexOutOfBoundsException e) {
			BadNetEvent be = new BadNetEvent(ip, id, e, MessageType.ERR, payload);
			bus.publish(be);
			return false;
		}
	}

}
