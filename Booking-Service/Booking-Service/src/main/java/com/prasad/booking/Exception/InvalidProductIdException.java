package com.prasad.booking.Exception;

@SuppressWarnings("serial")
public class InvalidProductIdException extends RuntimeException {

	public InvalidProductIdException() {

	}

	public InvalidProductIdException(String msg) {
		super(msg);
	}

}
