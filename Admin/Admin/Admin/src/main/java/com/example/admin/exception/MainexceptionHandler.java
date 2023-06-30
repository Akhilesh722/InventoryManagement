package com.example.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.admin.pojos.ErrorResponse;

@ControllerAdvice
public class MainexceptionHandler {

	@ExceptionHandler(NoAdminFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(NoAdminFoundException ex) {
		ErrorResponse err = new ErrorResponse(ex.getMessage());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidProductIdException.class)
	public ResponseEntity<ErrorResponse> handleException(InvalidProductIdException ex) {
		ErrorResponse err = new ErrorResponse(ex.getMessage());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(InvalidBookingIdException.class)
	public ResponseEntity<ErrorResponse> handleException(InvalidBookingIdException ex){
		ErrorResponse err=new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(InvalidCustomerIDException.class)
	public ResponseEntity<ErrorResponse> handleException(InvalidCustomerIDException ex){
		ErrorResponse err=new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
	
}
