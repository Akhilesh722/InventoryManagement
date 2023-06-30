package com.prasad.Login.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MainExceptionHandler {

	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(EmailNotFoundException ex) {
		ErrorResponse err = new ErrorResponse(ex.getMessage());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ErrorResponse> handleException(InvalidPasswordException ex) {
		ErrorResponse err = new ErrorResponse(ex.getMessage());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}

}
