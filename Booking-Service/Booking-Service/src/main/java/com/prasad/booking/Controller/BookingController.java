package com.prasad.booking.Controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.booking.Entity.Booking;
import com.prasad.booking.Service.BookingServiceImpl;
import com.prasad.booking.dto.ResponseDto;


@RestController
@CrossOrigin("http://localhost:3000")
public class BookingController {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	BookingServiceImpl impl;

	
	@PostMapping("/addBooking")
	public Booking insertBooking(@RequestBody Booking booking) {
		logger.info("Sending request to book the products");
		Booking save = impl.addBooking(booking);
		logger.info("Booking details added to the database");
		return save;

	}

	@PutMapping("/updateBooking/{bookingId}")
	public ResponseEntity<Booking> updateBooking(@PathVariable int bookingId, @RequestBody Booking updateBooking) {
		Booking save = impl.updateBooking(bookingId, updateBooking);
		return new ResponseEntity<>(save, HttpStatus.CREATED);
	}

	@DeleteMapping("/deleteById/")
	public ResponseEntity<String> deleteById(@RequestParam("productId") int productId,
			@RequestParam("customerId") int customerId) {
		String save = impl.deleteById(productId, customerId);
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@GetMapping("/getBooking/{id}")
	public ResponseEntity<ResponseDto> getBookingById(@PathVariable int id) {
		logger.info("Sending request to get the booking details");
		ResponseDto save = impl.getBookingById(id);
		logger.info("Booking details added to the database");
		return new ResponseEntity<>(save, HttpStatus.CREATED);

	}

	@GetMapping("/calculateBill/{customerId}")
	public ResponseEntity<Double> calculateBill(@PathVariable int customerId) {
		double save = impl.calculateBill(customerId);
		return new ResponseEntity<>(save, HttpStatus.CREATED);
	}
	
	@GetMapping("/viewAllBookings")
	public
	ResponseEntity<List<Booking>> getBooking() {
		List<Booking> p1 = impl.getBooking();
		return new ResponseEntity<>(p1, HttpStatus.CREATED);

	}

}
