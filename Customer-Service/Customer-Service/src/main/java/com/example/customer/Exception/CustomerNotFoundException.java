package com.example.customer.Exception;

@SuppressWarnings("serial")
public class CustomerNotFoundException extends RuntimeException {
	
	public CustomerNotFoundException() {
		
	}
	
	public CustomerNotFoundException(String msg) {
		super(msg);
	}

}
