package com.prasad.booking.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MainExceptionHandler {

	@ExceptionHandler(InvalidProductIdException.class)
	public ResponseEntity<ErrorResponse> handleException(InvalidProductIdException ex) {
		ErrorResponse err = new ErrorResponse(ex.getMessage());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(InvalidCustomerIdException.class)
	public ResponseEntity<ErrorResponse> handleException(InvalidCustomerIdException ex) {
		ErrorResponse err = new ErrorResponse(ex.getMessage());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(BookingIdNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(BookingIdNotFoundException ex) {
		ErrorResponse err = new ErrorResponse(ex.getMessage());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(CustomerNotFoundException ex) {
		ErrorResponse err = new ErrorResponse(ex.getMessage());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(NoProductsBookedException.class)
	public ResponseEntity<ErrorResponse> handleException(NoProductsBookedException ex) {
		ErrorResponse err = new ErrorResponse(ex.getMessage());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(InvalidBookingException.class)
	public ResponseEntity<ErrorResponse> handleException(InvalidBookingException ex) {
		ErrorResponse err = new ErrorResponse(ex.getMessage());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}

}
