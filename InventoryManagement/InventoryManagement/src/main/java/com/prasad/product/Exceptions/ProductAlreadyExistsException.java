package com.prasad.product.Exceptions;

@SuppressWarnings("serial")
public class ProductAlreadyExistsException extends RuntimeException {
	
	public ProductAlreadyExistsException() {
		
	}

	public ProductAlreadyExistsException(String msg) {
		super(msg);
	}

}
