package com.prasad.booking.Exception;

@SuppressWarnings("serial")
public class InvalidBookingException extends RuntimeException {
	
	public InvalidBookingException() {
		
	}
	
	public InvalidBookingException(String msg) {
		super(msg);
	}

}
