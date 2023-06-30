package com.prasad.booking.Service;

import java.util.List;

import com.prasad.booking.Entity.Booking;
import com.prasad.booking.dto.ResponseDto;


public interface IBookingService {

	// method to get the bookingId to book the products
	public Booking addBooking(Booking booking);

	// method to get the booking details
	public ResponseDto getBookingById(int bookingId);

    //method to delete the booking
	public String deleteById(int productId, int customerId);
	
	// method to update the booking
	public Booking updateBooking(int bookingId, Booking updatedBooking);

	// method to calculate the entire bill of the product
	public double calculateBill(int bookingId);
	
	
	public List<Booking> getBooking();

	
	
	
	
	

}
