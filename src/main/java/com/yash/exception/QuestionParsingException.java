package com.yash.exception;

public class QuestionParsingException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public QuestionParsingException(String message) {
		super(message);
	}

	public String getMessage() {
		return message;
	}
	

}
