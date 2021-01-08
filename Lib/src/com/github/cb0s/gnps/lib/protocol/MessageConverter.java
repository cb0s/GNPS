package com.github.cb0s.gnps.lib.protocol;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;

import com.github.cb0s.gnps.lib.event.EventBus;
import com.github.cb0s.gnps.lib.net.NetPacket;
import com.github.cb0s.gnps.lib.protocol.definition.Command;
import com.github.cb0s.gnps.lib.protocol.definition.MessageType;

/**
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public final class MessageConverter {
	/**
	 * 
	 * @param msg
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NumberFormatException 
	 * @throws IllegalStateException
	 */
	public static void parseCommand(NetPacket packet, EventBus bus, byte incomingId) throws NoSuchMethodException, SecurityException, NumberFormatException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String[] parts = packet.getRawMessage().split(">");
		
		if (parts.length < 2 || parts.length > 3) {
			throw new IllegalStateException("Raw messages must be in the format 'CMD>ID[>PAYLOAD]'!");
		}
		
		byte id = Byte.parseByte(parts[1]);
		if (id != incomingId) {
			throw new IllegalStateException();
		}
		
		Class<? extends Command> cmd = MessageType.valueOf(parts[0]).getRepr();
		Constructor<? extends Command> con = cmd.getConstructor(InetSocketAddress.class, byte.class, String.class);
		Command command = con.newInstance(
				packet.getSenderAdress(),
				id,
				parts.length == 2 ? Command.EMPTY_PAYLOAD : parts[2]);
		command.handle(bus);
	}
	
	/**
	 * There shall be no objects of this class!
	 */
	private MessageConverter() {}
}
