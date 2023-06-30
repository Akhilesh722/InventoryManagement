package com.prasad.product.Exceptions;

@SuppressWarnings("serial")
public class NoSuchProductExistsException  extends RuntimeException{
	
	public NoSuchProductExistsException() {
		
	}
	public NoSuchProductExistsException(String msg) {
		super(msg);
	}

}
