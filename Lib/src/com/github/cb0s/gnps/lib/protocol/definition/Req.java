package com.github.cb0s.gnps.lib.protocol.definition;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.github.cb0s.gnps.lib.event.EventBus;
import com.github.cb0s.gnps.lib.event.protocol.RequestEvent;

/**
 * Represents the Request-command for this protocol.<br>
 * <br>
 * The payload contains a command which the receiver is supposed to answer.<br>
 * These commands can be different and must therefore be parsed by the Handler using the library.<br>
 * <br>
 * <em>An example of this message: "REQ>X>MATCH" with MATCH being the special command for the handler.</em>
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public final class Req extends Command {

	/**
	 * See {@link Command#Command(InetAddress, byte, String)} for more details.
	 */
	public Req(InetSocketAddress ip, byte id, String payload) {
		super(ip, id, payload);
		checkPayloadLowerBound(1);
	}

	@Override
	public boolean handle(EventBus bus) {
		RequestEvent re = new RequestEvent(ip, id, payload);
		bus.publish(re);
		return true;
	}
	
}
