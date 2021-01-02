package com.github.cb0s.gnps.lib.net;

import java.net.InetSocketAddress;

import com.github.cb0s.gnps.lib.protocol.definition.MessageType;

/**
 * A custom implementation of {@link Thread} which as long as the Thread is alive
 * (<code>!{@link #isInterrupted()}</code>) calls {@link #receive()}.<br>
 * <br>
 * Further implementations must still implement {@link #send(InetSocketAddress, MessageType, String)},
 * {@link #onReceive(String)} and {@link #receive()}.<br>
 * <br>
 * The advantage of this is that the event-like structure of {@link NetReceiver} is already implemented.
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public abstract class NetAdapter extends Thread implements NetReceiver, NetSender {
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			receive();
		}
		Thread.currentThread().interrupt();
	}
}
