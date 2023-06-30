package com.prasad.product.Exceptions;

@SuppressWarnings("serial")
public class ZeroChargeException extends RuntimeException {

	public ZeroChargeException() {

	}

	public ZeroChargeException(String message) {
		super(message);
	}

}
