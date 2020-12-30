package com.github.cb0s.gnps.lib;

/**
 * 
 * @author Cedric Boes
 * @version 1.0
 */
public class Specifications {
	// General
	public static final String	NAME = "Global-Network-Positioning-System (GNPS)";
	public static final String	GITHUB_PAGE = "https://github.com/cb0s/GNPS.git";
	public static final String	LIB_VERSION = "v1.0.0-alpha";
	
	// Protocol
	public static final short	MAX_PACKET_SIZE = 256;
	public static final byte	MESSAGE_ID_SIZE = 1;
	public static final String	PROTOCOL_NAME = "Global-Network-Positioning-System-Protocol (GNPSP)";
	
	/**
	 * There shall be no objects of this class.
	 */
	private Specifications() {}
}
