package com.prasad.customer.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class MainExceptionHandler {
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(CustomerNotFoundException ex) {
		ErrorResponse err = new ErrorResponse(ex.getMessage());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(ProductNotFoundException ex) {
		ErrorResponse err = new ErrorResponse(ex.getMessage());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}


}
