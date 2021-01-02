package com.github.cb0s.gnps.lib.protocol.definition;

public enum ErrorCodes {
	SERVER_ERROR(503);
	
	public final int statusCode;
	
	private ErrorCodes(int code) {
		statusCode = code;
	}

	public static ErrorCodes getCode(int code) {
		return null;
	}
}
