package com.github.cb0s.gnps.lib.protocol;

import com.github.cb0s.gnps.lib.protocol.definition.Command;

/**
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public class MessageIdGenerator {
	/**
	 * 
	 */
	public static final int minId = Command.INVALID_ID + 1;
	
	/**
	 * 
	 */
	private int id;
	
	/**
	 * 
	 */
	public MessageIdGenerator() {
		id = minId;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized byte getNewId() {
		if (id > 255) {
			id = minId;
		}
		return (byte) id++;
	}
}
