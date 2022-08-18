package com.yash.exception;

public class QuizRepositoryException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;

	public QuizRepositoryException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
