package com.prasad.booking.Exception;

@SuppressWarnings("serial")
public class InvalidCustomerIdException extends RuntimeException {

	public InvalidCustomerIdException(String msg) {
		super(msg);
	}

}
