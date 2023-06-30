package com.example.admin.exception;

@SuppressWarnings("serial")
public class InvalidBookingIdException extends RuntimeException {
	public InvalidBookingIdException() {

	}

	public InvalidBookingIdException(String msg){
		super(msg);
	}

}
