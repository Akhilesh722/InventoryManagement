package com.prasad.product.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;import com.prasad.product.Entity.Product;
import com.prasad.product.pojos.ErrorResponse;

@ControllerAdvice
public class MainExceptionHandler {

	@ExceptionHandler(ProductAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleException(ProductAlreadyExistsException ex) {
		ErrorResponse err = new ErrorResponse(ex.getMessage());

		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);

	}
	
	@ExceptionHandler(NoSuchProductExistsException.class)
	public ResponseEntity<ErrorResponse> handleException(NoSuchProductExistsException ex){
		ErrorResponse err = new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ZeroChargeException.class)
	public ResponseEntity<ErrorResponse> handleException(ZeroChargeException ex){
		ErrorResponse err = new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(SizeNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleException(SizeNotFoundException ex){
		ErrorResponse err = new ErrorResponse(ex.getMessage());
		return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
		
	}
	


}
