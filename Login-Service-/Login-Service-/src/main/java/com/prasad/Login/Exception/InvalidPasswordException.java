package com.prasad.Login.Exception;

@SuppressWarnings("serial")
public class InvalidPasswordException  extends RuntimeException{
	
	public InvalidPasswordException() {
		
	}
	public InvalidPasswordException(String msg) {
		super(msg);
	}

}
