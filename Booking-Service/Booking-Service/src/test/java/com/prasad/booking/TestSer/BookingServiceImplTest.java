package com.prasad.booking.TestSer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.prasad.booking.Entity.Booking;
import com.prasad.booking.Exception.BookingIdNotFoundException;
import com.prasad.booking.Exception.CustomerNotFoundException;
import com.prasad.booking.Exception.InvalidBookingException;
import com.prasad.booking.Exception.InvalidCustomerIdException;
import com.prasad.booking.Exception.InvalidProductIdException;
import com.prasad.booking.Repository.BookingRepository;
import com.prasad.booking.dto.CustomerDto;
import com.prasad.booking.dto.ProductDto;
import com.prasad.booking.dto.ResponseDto;
import com.prasad.booking.Service.BookingServiceImpl;
import com.prasad.booking.Exception.NoProductsBookedException;

class BookingServiceImplTest {

	@Mock
	private BookingRepository bookingRepository;

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private BookingServiceImpl bookingService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddBooking_Success() {
		// Mocking the response from the product repository microservice
		ProductDto mockProductDto = new ProductDto();
		mockProductDto.setProductId(1);
		mockProductDto.setName("Product 1");
		mockProductDto.setCharges(10.0f);
		mockProductDto.setCategory("Category 1");
		mockProductDto.setSize("Size 1");

		ResponseEntity<ProductDto> mockProductResponse = new ResponseEntity<>(mockProductDto, HttpStatus.OK);
		when(restTemplate.getForEntity(anyString(), eq(ProductDto.class))).thenReturn(mockProductResponse);

		// Mocking the response from the customer repository microservice
		CustomerDto mockCustomerDto = new CustomerDto();
		mockCustomerDto.setCustomerId(1);
		mockCustomerDto.setCustomerName("John Doe");
		mockCustomerDto.setMobileNumber("1234567890");
		mockCustomerDto.setEmail("john.doe@example.com");

		ResponseEntity<CustomerDto> mockCustomerResponse = new ResponseEntity<>(mockCustomerDto, HttpStatus.OK);
		when(restTemplate.getForEntity(anyString(), eq(CustomerDto.class))).thenReturn(mockCustomerResponse);

		// Mocking the booking repository save method
		Booking mockBooking = new Booking();
		mockBooking.setBookingId(1);
		mockBooking.setProductId(1);
		mockBooking.setCustomerId(1);
		mockBooking.setDateTime(LocalDateTime.now());
		mockBooking.setCharges(10.0f);

		when(bookingRepository.save(any(Booking.class))).thenReturn(mockBooking);

		// Creating a new booking
		Booking newBooking = new Booking();
		newBooking.setProductId(1);
		newBooking.setCustomerId(1);
		newBooking.setDateTime(LocalDateTime.now());

		// Calling the addBooking method
		Booking result = bookingService.addBooking(newBooking);

		// Verifying the result
		assertNotNull(result);
		assertEquals(1, result.getBookingId());
		assertEquals(1, result.getProductId());
		assertEquals(1, result.getCustomerId());
		assertEquals(10.0f, result.getCharges());
	}

	
	@Test
	void testAddBooking_InvalidProductId() {
		// Mocking the response from the product repository microservice
		when(restTemplate.getForEntity(anyString(), eq(ProductDto.class)))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

		// Creating a new booking with an invalid product ID
		Booking newBooking = new Booking();
		newBooking.setProductId(100); // Invalid product ID
		newBooking.setCustomerId(1);
		newBooking.setDateTime(LocalDateTime.now());

		// Calling the addBooking method and asserting that it throws an InvalidProductIdException
		assertThrows(InvalidProductIdException.class, () -> bookingService.addBooking(newBooking));
	}
	@Test
	void testGetBookingById_Success() {
		// Mocking the booking repository
		Booking mockBooking = new Booking();
		mockBooking.setBookingId(1);
		mockBooking.setProductId(1);
		mockBooking.setCustomerId(1);

		when(bookingRepository.findById(1)).thenReturn(Optional.of(mockBooking));

		// Mocking the response from the product repository microservice
		ProductDto mockProductDto = new ProductDto();
		mockProductDto.setProductId(1);
		mockProductDto.setName("Product 1");
		mockProductDto.setCharges(10.0f);
		mockProductDto.setCategory("Category 1");
		mockProductDto.setSize("Size 1");

		ResponseEntity<ProductDto> mockProductResponse = new ResponseEntity<>(mockProductDto, HttpStatus.OK);
		when(restTemplate.getForEntity(eq("http://localhost:8087/viewProduct/1"), eq(ProductDto.class)))
				.thenReturn(mockProductResponse);

		// Mocking the response from the customer repository microservice
		CustomerDto mockCustomerDto = new CustomerDto();
		mockCustomerDto.setCustomerId(1);
		mockCustomerDto.setCustomerName("John Doe");
		mockCustomerDto.setMobileNumber("1234567890");
		mockCustomerDto.setEmail("john.doe@example.com");

		ResponseEntity<CustomerDto> mockCustomerResponse = new ResponseEntity<>(mockCustomerDto, HttpStatus.OK);
		when(restTemplate.getForEntity(eq("http://localhost:8082/getCustomerByid/1"), eq(CustomerDto.class)))
				.thenReturn(mockCustomerResponse);

		// Calling the getBookingById method
		ResponseDto result = bookingService.getBookingById(1);

		// Verifying the result
		assertNotNull(result);
		assertNotNull(result.getProduct());
		assertEquals(1, result.getProduct().getProductId());
		assertEquals("Product 1", result.getProduct().getName());
		assertNotNull(result.getCustomer());
		assertEquals(1, result.getCustomer().getCustomerId());
		assertEquals("John Doe", result.getCustomer().getCustomerName());
	}
	@Test
	void testGetBookingById_BookingIdNotFound() {
		// Mocking the booking repository
		when(bookingRepository.findById(1)).thenReturn(Optional.empty());

		// Calling the getBookingById method and asserting that it throws a BookingIdNotFoundException
		assertThrows(BookingIdNotFoundException.class, () -> bookingService.getBookingById(1));
	}
//	@Test
//	void testGetBookingById_InvalidProductId() {
//		// Mocking the booking repository
//		Booking mockBooking = new Booking();
//		mockBooking.setBookingId(1);
//		mockBooking.setProductId(1);
//		mockBooking.setCustomerId(1);
//
//		when(bookingRepository.findById(1)).thenReturn(Optional.of(mockBooking));
//
//		// Mocking the response from the product repository microservice
//		when(restTemplate.getForEntity(eq("http://localhost:8087/viewProduct/1"), eq(ProductDto.class)))
//				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
//
//		// Calling the getBookingById method
//		assertThrows(InvalidProductIdException.class, () -> bookingService.getBookingById(1));
//	}

	
//	@Test
//	void testGetBookingById_InvalidCustomerId() {
//		// Mocking the booking repository
//		Booking mockBooking = new Booking();
//		mockBooking.setBookingId(1);
//		mockBooking.setProductId(1);
//		mockBooking.setCustomerId(1);
//
//		when(bookingRepository.findById(1)).thenReturn(Optional.of(mockBooking));
//
//		// Mocking the response from the customer repository microservice
//		when(restTemplate.getForEntity(eq("http://localhost:8082/getCustomerByid/1"), eq(CustomerDto.class)))
//				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
//
//		// Calling the getBookingById method
//		assertThrows(InvalidCustomerIdException.class, () -> bookingService.getBookingById(1));
//	}
	
	@Test
	void testDeleteById_Success() {
		// Mocking the response from the product repository microservice
		ProductDto mockProductDto = new ProductDto();
		mockProductDto.setProductId(1);
		mockProductDto.setName("Product 1");
		mockProductDto.setCharges(10.0f);
		mockProductDto.setCategory("Category 1");
		mockProductDto.setSize("Size 1");

		ResponseEntity<ProductDto> mockProductResponse = new ResponseEntity<>(mockProductDto, HttpStatus.OK);
		when(restTemplate.getForEntity(eq("http://localhost:8087/viewProduct/1"), eq(ProductDto.class)))
				.thenReturn(mockProductResponse);

		// Mocking the response from the customer repository microservice
		CustomerDto mockCustomerDto = new CustomerDto();
		mockCustomerDto.setCustomerId(1);
		mockCustomerDto.setCustomerName("John Doe");
		mockCustomerDto.setMobileNumber("1234567890");
		mockCustomerDto.setEmail("john.doe@example.com");

		ResponseEntity<CustomerDto> mockCustomerResponse = new ResponseEntity<>(mockCustomerDto, HttpStatus.OK);
		when(restTemplate.getForEntity(eq("http://localhost:8082/getCustomerByid/1"), eq(CustomerDto.class)))
				.thenReturn(mockCustomerResponse);

		// Mocking the booking repository
		Booking mockBooking = new Booking();
		mockBooking.setBookingId(1);
		mockBooking.setProductId(1);
		mockBooking.setCustomerId(1);

		when(bookingRepository.findAll()).thenReturn(Collections.singletonList(mockBooking));

		// Calling the deleteById method
		String result = bookingService.deleteById(1, 1);

		// Verifying the result
		assertEquals("Booking deleted successfully", result);
		verify(bookingRepository, times(1)).delete(mockBooking);
	}
	

	@Test
	void testDeleteById_InvalidProductId() {
		// Mocking the response from the product repository microservice
		when(restTemplate.getForEntity(eq("http://localhost:8087/viewProduct/1"), eq(ProductDto.class)))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

		// Calling the deleteById method and asserting that it throws an InvalidProductIdException
		assertThrows(InvalidProductIdException.class, () -> bookingService.deleteById(1, 1));
	}

	@Test
	void testDeleteById_InvalidCustomerId() {
		// Mocking the response from the customer repository microservice
		when(restTemplate.getForEntity(eq("http://localhost:8082/getCustomerByid/1"), eq(CustomerDto.class)))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

		// Calling the deleteById method and asserting that it throws an InvalidCustomerIdException
		assertThrows(InvalidCustomerIdException.class, () -> bookingService.deleteById(1, 1));
	}

	@Test
	void testDeleteById_BookingNotFound() {
		// Mocking the response from the product repository microservice
		ProductDto mockProductDto = new ProductDto();
		mockProductDto.setProductId(1);
		mockProductDto.setName("Product 1");
		mockProductDto.setCharges(10.0f);
		mockProductDto.setCategory("Category 1");
		mockProductDto.setSize("Size 1");

		ResponseEntity<ProductDto> mockProductResponse = new ResponseEntity<>(mockProductDto, HttpStatus.OK);
		when(restTemplate.getForEntity(eq("http://localhost:8087/viewProduct/1"), eq(ProductDto.class)))
				.thenReturn(mockProductResponse);

		// Mocking the response from the customer repository microservice
		CustomerDto mockCustomerDto = new CustomerDto();
		mockCustomerDto.setCustomerId(1);
		mockCustomerDto.setCustomerName("John Doe");
		mockCustomerDto.setMobileNumber("1234567890");
		mockCustomerDto.setEmail("john.doe@example.com");

		ResponseEntity<CustomerDto> mockCustomerResponse = new ResponseEntity<>(mockCustomerDto, HttpStatus.OK);
		when(restTemplate.getForEntity(eq("http://localhost:8082/getCustomerByid/1"), eq(CustomerDto.class)))
				.thenReturn(mockCustomerResponse);

		// Mocking the booking repository
		when(bookingRepository.findAll()).thenReturn(Collections.emptyList());

		// Calling the deleteById method and asserting that it throws an InvalidBookingException
		assertThrows(InvalidBookingException.class, () -> bookingService.deleteById(1, 1));
	}
//	@Test
//	void testUpdateBooking_Success() {
//		// Mocking the booking repository
//		Booking mockExistingBooking = new Booking();
//		mockExistingBooking.setBookingId(1);
//		mockExistingBooking.setProductId(1);
//		mockExistingBooking.setCustomerId(1);
//		mockExistingBooking.setDateTime(LocalDateTime.now());
//
//		when(bookingRepository.findById(1)).thenReturn(Optional.of(mockExistingBooking));
//
//		// Mocking the response from the product repository microservice
//		ProductDto mockProductDto = new ProductDto();
//		mockProductDto.setProductId(2);
//		mockProductDto.setName("Updated Product");
//		mockProductDto.setCharges(20.0f);
//		mockProductDto.setCategory("Updated Category");
//		mockProductDto.setSize("Updated Size");
//
//		ResponseEntity<ProductDto> mockProductResponse = new ResponseEntity<>(mockProductDto, HttpStatus.OK);
//		when(restTemplate.getForEntity(eq("http://localhost:8087/viewProduct/2"), eq(ProductDto.class)))
//				.thenReturn(mockProductResponse);
//
//		// Mocking the response from the customer repository microservice
//		CustomerDto mockCustomerDto = new CustomerDto();
//		mockCustomerDto.setCustomerId(3);
//		mockCustomerDto.setCustomerName("Updated Customer");
//		mockCustomerDto.setMobileNumber("9876543210");
//		mockCustomerDto.setEmail("updated.customer@example.com");
//
//		ResponseEntity<CustomerDto> mockCustomerResponse = new ResponseEntity<>(mockCustomerDto, HttpStatus.OK);
//		when(restTemplate.getForEntity(eq("http://localhost:8082/getCustomerByid/3"), eq(CustomerDto.class)))
//				.thenReturn(mockCustomerResponse);
//
//		// Creating the updated booking
//		Booking updatedBooking = new Booking();
//		updatedBooking.setProductId(2);
//		updatedBooking.setCustomerId(3);
//		updatedBooking.setDateTime(LocalDateTime.now().plusHours(1));
//
//		// Calling the updateBooking method
//		Booking result = bookingService.updateBooking(1, updatedBooking);
//
//		// Verifying the result
//		assertNotNull(result);
//		assertEquals(1, result.getBookingId());
//		assertEquals(2, result.getProductId());
//		assertEquals(3, result.getCustomerId());
//		assertEquals(updatedBooking.getDateTime(), result.getDateTime());
//	}

	@Test
	void testUpdateBooking_BookingIdNotFound() {
		// Mocking the booking repository
		when(bookingRepository.findById(1)).thenReturn(Optional.empty());

		// Creating the updated booking
		Booking updatedBooking = new Booking();
		updatedBooking.setProductId(2);
		updatedBooking.setCustomerId(3);
		updatedBooking.setDateTime(LocalDateTime.now().plusHours(1));

		// Calling the updateBooking method and asserting that it throws a BookingIdNotFoundException
		assertThrows(BookingIdNotFoundException.class, () -> bookingService.updateBooking(1, updatedBooking));
	}

	@Test
	void testUpdateBooking_InvalidProductId() {
		// Mocking the booking repository
		Booking mockExistingBooking = new Booking();
		mockExistingBooking.setBookingId(1);
		mockExistingBooking.setProductId(1);
		mockExistingBooking.setCustomerId(1);
		mockExistingBooking.setDateTime(LocalDateTime.now());

		when(bookingRepository.findById(1)).thenReturn(Optional.of(mockExistingBooking));

		// Mocking the response from the product repository microservice
		when(restTemplate.getForEntity(eq("http://localhost:8087/viewProduct/2"), eq(ProductDto.class)))
				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

		// Creating the updated booking
		Booking updatedBooking = new Booking();
		updatedBooking.setProductId(2);
		updatedBooking.setCustomerId(3);
		updatedBooking.setDateTime(LocalDateTime.now().plusHours(1));

		// Calling the updateBooking method and asserting that it throws an InvalidProductIdException
		assertThrows(InvalidProductIdException.class, () -> bookingService.updateBooking(1, updatedBooking));
	}

//	@Test
//	void testUpdateBooking_InvalidCustomerId() {
//		// Mocking the booking repository
//		Booking mockExistingBooking = new Booking();
//		mockExistingBooking.setBookingId(1);
//		mockExistingBooking.setProductId(1);
//		mockExistingBooking.setCustomerId(1);
//		mockExistingBooking.setDateTime(LocalDateTime.now());
//
//		when(bookingRepository.findById(1)).thenReturn(Optional.of(mockExistingBooking));
//
//		// Mocking the response from the customer repository microservice
//		when(restTemplate.getForEntity(eq("http://localhost:8082/getCustomerByid/3"), eq(CustomerDto.class)))
//				.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
//
//		// Creating the updated booking
//		Booking updatedBooking = new Booking();
//		updatedBooking.setProductId(2);
//		updatedBooking.setCustomerId(3);
//		updatedBooking.setDateTime(LocalDateTime.now().plusHours(1));
//
//		// Calling the updateBooking method and asserting that it throws a CustomerNotFoundException
//		assertThrows(CustomerNotFoundException.class, () -> bookingService.updateBooking(1, updatedBooking));
//	}

//	@Test
//	void testCalculateBill_Success() {
//		// Mocking the booking repository
//		List<Booking> mockBookings = new ArrayList<>();
//		Booking booking1 = new Booking();
//		booking1.setBookingId(1);
//		booking1.setProductId(1);
//		booking1.setCustomerId(1);
//		booking1.setCharges((float) 10.0);
//		mockBookings.add(booking1);
//
//		Booking booking2 = new Booking();
//		booking2.setBookingId(2);
//		booking2.setProductId(2);
//		booking2.setCustomerId(1);
//		booking2.setCharges((float) 20.0);
//		mockBookings.add(booking2);
//
//		when(bookingRepository.findAllByCustomerId(1)).thenReturn(mockBookings);
//
//		// Calling the calculateBill method
//		double result = bookingService.calculateBill(1);
//
//		// Verifying the result
//		assertEquals(32.5, result);
//	}

	@Test
	void testCalculateBill_NoProductsBooked() {
		// Mocking the booking repository to return an empty list
		when(bookingRepository.findAllByCustomerId(1)).thenReturn(Collections.emptyList());

		// Calling the calculateBill method and asserting that it throws a NoProductsBookedException
		assertThrows(NoProductsBookedException.class, () -> bookingService.calculateBill(1));
	}



	
}
