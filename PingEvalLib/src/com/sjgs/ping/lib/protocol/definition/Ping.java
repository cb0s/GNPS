package com.sjgs.ping.lib.protocol.definition;

import java.net.InetSocketAddress;

import com.sjgs.ping.lib.event.EventBus;
import com.sjgs.ping.lib.event.PingEvent;

/**
 * Represents the Ping-message. It requires a special answer, the {@link Pong pong-message}.<br>
 * <br>
 * This does not require payload.<br>
 * <em>Example: "PING>X"</em>
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public final class Ping extends Command {

	/**
	 * See {@link Command#Command(InetSocketAddress, byte, String)} for more details.
	 */
	public Ping(InetSocketAddress ip, byte id, String payload) {
		super(ip, id, payload);
		checkPayloadUpperBound(0);
	}

	@Override
	public boolean handle(EventBus bus) {
		PingEvent pe = new PingEvent(ip);
		bus.priorityPublish(pe);
		return false;
	}

}
