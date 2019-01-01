package com.omdb.exception;

public class OMDBInvalidInputException extends RuntimeException {

	/**
	 * 
	 */
	
	private String code;
	private static final long serialVersionUID = 1L;
	
	public OMDBInvalidInputException( String message){
		super(message);
	}

}
