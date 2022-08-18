package com.yash.exception;

public class UserAlreadyExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	public UserAlreadyExistException(String message) {
		super();
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
