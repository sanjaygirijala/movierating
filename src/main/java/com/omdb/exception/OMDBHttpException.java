package com.omdb.exception;

public class OMDBHttpException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int code;
	
	public OMDBHttpException(String message){
		super(message);
	}

}
