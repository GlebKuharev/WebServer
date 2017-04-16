package com.epam.service;

public class HttpFormatException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public HttpFormatException() {
		super();
	}

	public HttpFormatException(String message) { 
		super(message);
	}

	public HttpFormatException(Exception e) { 
		super(e);
	}

	public HttpFormatException(String message, Exception e) { 
		super(message, e);
	}

}
