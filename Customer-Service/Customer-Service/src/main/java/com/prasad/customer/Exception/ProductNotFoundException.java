package com.prasad.customer.Exception;

@SuppressWarnings("serial")
public class ProductNotFoundException extends RuntimeException {
	public ProductNotFoundException(String msg) {
		super(msg);

	}

}
