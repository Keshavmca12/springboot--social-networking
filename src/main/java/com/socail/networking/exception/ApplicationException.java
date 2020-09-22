package com.socail.networking.exception;

public class ApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5372359760742927942L;
	
	String message;

	public ApplicationException(String message) {
		super();
		this.message = message;
	}
	
	

}
