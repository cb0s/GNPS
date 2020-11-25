package com.sjgs.ping.lib.protocol.definition;

import java.net.InetSocketAddress;

import com.sjgs.ping.lib.event.EventBus;
import com.sjgs.ping.lib.event.protocol.HeartbeatEvent;

/**
 * Represents the {@link MessageType#ALIVE Life}-Command.<br>
 * This command does not come with payload.
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public final class Life extends Command {

	/**
	 * See {@link Command#Command(InetSocketAddress, byte, String)} for more details.
	 */
	public Life(InetSocketAddress ip, byte id, String payload) {
		super(ip, id, payload);
		checkPayloadUpperBound(0);
	}

	@Override
	public boolean handle(EventBus bus) {
		HeartbeatEvent he = new HeartbeatEvent(ip);
		bus.publish(he);
		
		return true;
	}

}
