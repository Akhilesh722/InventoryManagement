package com.example.admin.exception;

@SuppressWarnings("serial")
public class AdminAlreadyExistsException extends RuntimeException {
public AdminAlreadyExistsException(String msg) {
	super(msg);
}
}
