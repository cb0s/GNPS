package com.gnps.lib.net;

import java.net.InetSocketAddress;

/**
 * NetReceiver-Implementations are Classes which can handle the Network-Receiving process for a specific protocol or via
 * a specific technology.<br>
 * As the {@link NetAdapter} already implements the NetReceiver either this one or an own implementation can be used.
 * Using the adapter however has the advantage of already implementing the {@link NetSender} as well which is needed
 * for the receiving process.<br>
 *  
 * @author Cedric Boes
 * @version 1.0
 */
public interface NetReceiver {
	/**
	 * All different technologies based on an I/O-Protocol contain some type of receive() message.<br>
	 * To avoid those blocking the main-thread while waiting for new messages, they can be wrapped into this method.<br>
	 * <br>
	 * This method must be constantly called from the Thread handling incoming messages.
	 * 
	 * @return result 	{@link NetPacket} composed of the result of a receive() and the corresponding
	 * 					{@link InetSocketAddress sender}
	 */
	public NetPacket receive();
}
