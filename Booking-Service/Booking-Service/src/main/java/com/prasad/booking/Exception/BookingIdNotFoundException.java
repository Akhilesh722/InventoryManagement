package com.prasad.booking.Exception;

@SuppressWarnings("serial")
public class BookingIdNotFoundException extends RuntimeException {

	public BookingIdNotFoundException(String msg) {
		super(msg);
	}

}
