package com.github.cb0s.gnps.lib.net;

import java.net.InetSocketAddress;

import com.github.cb0s.gnps.lib.protocol.definition.MessageType;

/**
 * NetSender-Implementations are Classes which can handle the Network-Sending process for a specific protocol or via a
 * specific technology.<br>
 * As the {@link NetAdapter} already implements the NetSender either this one or an own implementation can be used.
 * Using the adapter however has the advantage of already implementing the {@link NetReceiver} as well which is needed
 * for the receiving process.<br>
 * Currently implemented {@link NetAdapter NetAdapters} are:
 * <ul>
 * 	<li>{@link UdpAdapter}</li>
 * </ul>
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public interface NetSender {
	/**
	 * Method which a {@link NetSender} must implement.<br>
	 * <br>
	 * This method is used to send a message to an {@link InetSocketAddress}.<br>
	 * It must automatically assign the message-id as specified in the GNPSP-documentation.
	 * 
	 * @param address	destination of a message
	 * @param type		{@link MessageType} of the message
	 * @param payload	raw payload
	 * @return created message id
	 */
	public byte send(InetSocketAddress address, MessageType type, String payload);
}
