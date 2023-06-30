package com.prasad.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.prasad.booking.Entity.Booking;
import com.prasad.booking.Repository.BookingRepository;
import com.prasad.booking.Service.BookingServiceImpl;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookingServiceTest {

	@Mock
	private BookingRepository bookingRepo;

	@InjectMocks
	private BookingServiceImpl impl;

	Booking booking;

	@BeforeEach
	public void setUp() {
	    // Initialize test data
	    booking = new Booking();
	    booking.setBookingId(12);
	    booking.setProductId(1);
	    booking.setCustomerId(1);
	    booking.setCharges(123);
	    booking.setCategory("accessories");
	    booking.setName("I-PHONE");
	    booking.setSize("Medium");
	    booking.setDateTime(null);
	}

	@Test
	public void testAddBooking() {
	    // Mock the repository method to return the booking when saved
	    when(bookingRepo.save(any(Booking.class))).thenReturn(booking);

	    // Call the service method
	    Booking addedBooking = impl.addBooking(booking);

	    // Verify the result
	    assertNotNull(addedBooking);
	    assertEquals(booking, addedBooking);

	    // Verify that the repository method was called with the booking
	    verify(bookingRepo, times(1)).save(booking);
	}


}
