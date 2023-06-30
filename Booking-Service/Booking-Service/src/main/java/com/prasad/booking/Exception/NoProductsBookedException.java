package com.prasad.booking.Exception;

@SuppressWarnings("serial")
public class NoProductsBookedException extends RuntimeException {

	public NoProductsBookedException(String msg) {
		super(msg);
	}

}
