package com.github.cb0s.gnps.lib.protocol.definition;

import java.net.InetSocketAddress;

import com.github.cb0s.gnps.lib.event.EventBus;
import com.github.cb0s.gnps.lib.event.net.NetEvent;
import com.github.cb0s.gnps.lib.event.protocol.UnknownCommandEvent;
import com.github.cb0s.gnps.lib.net.NetSender;
import com.github.cb0s.gnps.lib.protocol.MessageConverter;

/**
 * The super class for all the commands from the UDP-Packets which are supported by the {@link MessageConverter}.<br>
 * Commands are the first part of an incoming message. These will be split as following:<br>
 * Format: <code>CMD>ID>PAYLOAD</code>
 * <ul>
 * 	<li><code>CMD</code> is the command which just indicates different types of payloads and information needed for
 * 						further parsing (if unknown an {@link UnknownCommandEvent} will triggered)</li>
 * 	<li><code>ID</code> is a unique byte for each message and host and will only be repeated after 255 sent
 * 						messages (will be initialized with 1; if unknown use {@link #INVALID_ID})</li>
 * 	<li><code>PAYLOAD</code> are the parts of the message which must be parsed by the users of this library
 * 							(if empty utilize {@link #EMPTY_PAYLOAD})</li>
 * </ul>
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public abstract class Command {
	
	/**
	 * Indicates that there was no valid {@link #payload} included into the packet.
	 */
	public static final String EMPTY_PAYLOAD = String.valueOf(0x0);
	/**
	 * Indicates that the {@link #id} could not be retrieved.
	 */
	public static final byte INVALID_ID = (byte) 0x0;
	/**
	 * Indicates that somehow the {@link #ip} of the sender could not be retrieved.
	 */
	public static final InetSocketAddress UNKNOWN_HOST = new InetSocketAddress("255.255.255.255", 0);
	
	
	/**
	 * IP where the command came from.<br>
	 * If unknown or invalid use {@link #UNKNOWN_HOST}.
	 */
	protected final InetSocketAddress ip;
	/**
	 * The id of the packet.<br>
	 * If unknown or invalid use {@link #INVALID_ID}.
	 */
	protected final byte id;
	/**
	 * The actual payload of the message.<br>
	 * If empty, unknown or invalid use {@link #EMPTY_PAYLOAD}.
	 */
	protected final String payload;
	
	/**
	 * Creates a new {@link Command} with the given IP-address, the package id and the payload.<br>
	 * <br>
	 * IP and payload must not be <code>null</code> else an {@link IllegalArgumentException} will be thrown.
	 * To indicate undefined values use {@link #EMPTY_PAYLOAD}, {@link #INVALID_ID} or {@link #UNKNOWN_HOST}.
	 * 
	 * @param ip {@link #ip}
	 * @param id {@link #id}
	 * @param payload {@link #payload}
	 */
	protected Command(InetSocketAddress ip, byte id, String payload) {
		if (ip == null || payload == null) {
			throw new IllegalArgumentException("IP and payload both must not be null!");
		}
		this.ip = ip;
		this.id = id;
		this.payload = payload;
	}
	
	/**
	 * Starts parsing and handling the given raw payload which was part of a received UDP-packet sent by the given
	 * {@link #ip}.<br>
	 * This packet must be parsed and handled according to the specifications.<br>
	 * If those require an answer, they must be sent by whoever handles the events which were triggered
	 * by the handlers.
	 * <br>
	 * A payload is considered to be handled if the correct {@link NetEvent Events} have been triggered. 
	 * 
	 * @param bus {@link EventBus} to which {@link NetEvent Events} can be published
	 * @return if handling was successful or the received payload was invalid
	 */
	public abstract boolean handle(EventBus bus);
	
	/**
	 * Converting this object into the right format so it can be sent to {@link #ip}.<br>
	 * The message ID cannot be set, this will be done by the {@link NetSender} automatically.<br>
	 * <br>
	 * Possible Exceptions will not be caught!<br>
	 * Exception that can occur:
	 * <ul>
	 * 	<li>{@link IllegalArgumentException}: if this {@link Command} is not supported by the protocol</li>
	 * </ul>
	 * 
	 * @param sender {@link NetSender} for sending
	 * @return if sending was successful or an exception occurred
	 */
	public byte send(NetSender sender) {
		MessageType type = MessageType.getFromRepr(this.getClass());
		if (type != null) {
			return sender.send(ip, type, payload);
		} else {
			throw new IllegalArgumentException("There is no MessageType called " + this.getClass().getName() + "!");
		}
	}
	
	/**
	 * Returns whether the {@link #ip} matches the {@link #UNKNOWN_HOST}.
	 * 
	 * @return whether {@link #ip} equals {@link #UNKNOWN_HOST}
	 */
	public boolean isIpUnknown() {
		return ip.equals(UNKNOWN_HOST);
	}
	
	/**
	 * Returns whether the {@link #id} matches the {@link #INVALID_ID}.
	 * 
	 * @return whether {@link #id} == {@link #INVALID_ID}
	 */
	public boolean isIdInvalid() {
		return id == INVALID_ID;
	}
	
	/**
	 * Returns whether the {@link #payload} matches {@link #EMPTY_PAYLOAD}.
	 * 
	 * @return whether {@link #payload} equals {@link #EMPTY_PAYLOAD}
	 */
	public boolean isPayloadEmpty() {
		return payload.equals(EMPTY_PAYLOAD);
	}
	
	/**
	 * Getter for {@link #ip}.
	 * 
	 * @return {@link #ip}
	 */
	public InetSocketAddress getIp() {
		return ip;
	}
	
	/**
	 * Getter for {@link #id}.
	 * 
	 * @return {@link #id}
	 */
	public byte getId() {
		return id;
	}
	
	/**
	 * Getter for {@link #payload}.
	 * 
	 * @return {@link #payload}
	 */
	public String getPayload() {
		return payload;
	}
	
	// Often used restrictions for parsing
	/**
	 * Checks if the payload's length is too small. Throws an {@link IllegalArgumentException} if so.
	 * 
	 * @param lowerBound for the payload (inclusive)
	 * @throws IllegalArgumentException if the payload's length is too small
	 */
	protected void checkPayloadLowerBound(int lowerBound) {
		if (lowerBound > payload.length() && !payload.equals(EMPTY_PAYLOAD)) {
			throw new IllegalArgumentException("The payload's length is too low (must be at least "
												+ lowerBound + ")!");
		}
	}
	
	/**
	 * Checks if the payload's length is too big. Throws an {@link IllegalArgumentException} if so.
	 * 
	 * @param upperBound for the payload (inclusive)
	 * @throws IllegalArgumentException if the payload's length is too big
	 */
	protected void checkPayloadUpperBound(int upperBound) {
		if (upperBound < payload.length() && !payload.equals(EMPTY_PAYLOAD)) {
			throw new IllegalArgumentException("The payload's length is too big (must not be greater than "
												+ upperBound + ")!");
		}
	}
	
	/**
	 * Checks if the payload's length is in the correct bounds and throws an {@link IllegalArgumentException} otherwise.
	 * 
	 * @param lowerBound for the payload (inclusive)
	 * @param upperBound for the payload (inclusive)
	 * @throws IllegalArgumentException if the payload's length is not the correct bounds
	 */
	protected void checkPayloadInBounds(int lowerBound, int upperBound) {
		checkPayloadLowerBound(lowerBound);
		checkPayloadUpperBound(upperBound);
	}
}
