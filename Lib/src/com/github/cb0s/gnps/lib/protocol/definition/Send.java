package com.github.cb0s.gnps.lib.protocol.definition;

import java.net.InetSocketAddress;

import com.github.cb0s.gnps.lib.event.EventBus;
import com.github.cb0s.gnps.lib.event.protocol.SendingEvent;
import com.github.cb0s.gnps.lib.net.NetPacket;

/**
 * Represents the Sending-messages for this protocol.<br>
 * This type of message will be used to send data which was not requested and differs from {@link Res responses} in
 * this sense.<br>
 * <br>
 * The payload can differ to a great extend.<br>
 * <em>Example: "SEND>X>SOME_CUSTOM_PAYLOAD"</em>
 * 
 * @author Cedric Boes
 * @version 1.1
 */
public final class Send extends Command {

	/**
	 * See {@link Command#Command(InetSocketAddress, byte, String)} for more details.
	 */
	public Send(InetSocketAddress ip, byte id, String payload) {
		super(ip, id, payload);
		checkPayloadLowerBound(1);
	}

	@Override
	public boolean handle(EventBus bus) {
		SendingEvent se = new SendingEvent(new NetPacket(ip, payload));
		bus.publish(se);
		
		return true;
	}

}
