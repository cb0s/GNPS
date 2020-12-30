package com.gnps.lib.protocol.definition;

/**
 * Enum for the available {@link Command commands} of this protocol.<br>
 * From an enum-entry the {@link #repr} can be retained which allows access to the {@link Command cmd-class} itself.
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public enum MessageType {
	/**
	 * Last package acknowledged. A Package has been received successfully.
	 */
	ACK(Ack.class),
	/**
	 * An error has occurred.<br>
	 * Payload can contain more information.
	 */
	ERR(Err.class),
	/**
	 * Requesting an action.<br>
	 * Payload contain more information about what is requested.
	 */
	REQ(Req.class),
	/**
	 * Response to the {@link REQ request}.<br>
	 * The actual response is contained as payload.
	 */
	RES(Res.class),
	/**
	 * Actual Ping-Message.<br>
	 * Payload is the id of the ping.
	 */
	PING(Ping.class),
	/**
	 * Response to Ping-Message<br>
	 * The id of the answered ping is included as payload.
	 */
	PONG(Pong.class),
	/**
	 * Packet is for data-transfer.<br>
	 * Payload can differ for each result.
	 */
	SEND(Send.class),
	/**
	 * Registering Packages between Clients.<br>
	 * Payload contains the IP of the Client which will connect.<br>
	 * <br>
	 * This is a security feature that only packets from a list of a few senders will be accepted.
	 */
	REG(Reg.class),
	/**
	 * Special packet which lets the server know that a client although it is waiting is still alive.
	 * This will be sent every second so after 4 seconds clients are considered to be timed out.<br>
	 * <code>ALIVE</code> does not contain any payload!
	 */
	LIFE(Life.class);
	
	/**
	 * Getter for {@link #repr}.
	 * 
	 * @return {@link #repr}
	 */
	public Class<? extends Command> getRepr() {
		return repr;
	}
	
	/**
	 * Returns the {@link MessageType} linked to the given Class.<br>
	 * Returns <code>null</code> if no {@link MessageType} with the given {@link #repr} exists.
	 * 
	 * @param repr of the requested {@link MessageType}
	 * @return {@link MessageType} with the given Class-representation
	 */
	public static MessageType getFromRepr(Class<? extends Command> repr) {
		for (MessageType t : MessageType.values()) {
			if (repr.equals(t.repr)) {
				return t;
			}
		}
		return null;
	}
	
	/**
	 * {@link Class} which is linked to one {@link MessageType} exclusively.
	 */
	private final Class<? extends Command> repr;
	
	/**
	 * Constructor for {@link MessageType}.<br>
	 * Sets the {@link #repr} for each object.
	 */
	private MessageType(Class<? extends Command> repr) {
		if (getFromRepr(repr) == null) {
			this.repr = repr;
		} else {
			throw new InstantiationError("Each class representation must be unique!");
		}
	}
}
