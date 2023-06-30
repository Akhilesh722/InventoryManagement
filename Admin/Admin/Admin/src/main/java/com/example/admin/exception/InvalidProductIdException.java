package com.example.admin.exception;

@SuppressWarnings("serial")
public class InvalidProductIdException extends RuntimeException {

	public InvalidProductIdException() {

	}

	public InvalidProductIdException(String msg) {
		super(msg);
	}

}
