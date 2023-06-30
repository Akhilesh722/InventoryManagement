package com.example.admin.exception;

@SuppressWarnings("serial")
public class InvalidCustomerIDException  extends RuntimeException {

	public InvalidCustomerIDException() {

	}

	public InvalidCustomerIDException(String msg) {
		super(msg);
	}

}