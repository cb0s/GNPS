package com.gnps.lib.protocol;

public class MessageIdCreator {
	private int id;
	
	public MessageIdCreator() {
		id = 0;
	}
	
	public synchronized int getNewId() {
		return id++;
	}
}
