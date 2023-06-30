package com.prasad.booking.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.prasad.booking.Entity.Booking;
import com.prasad.booking.Repository.BookingRepository;
import com.prasad.booking.dto.CustomerDto;
import com.prasad.booking.dto.ProductDto;
import com.prasad.booking.dto.ResponseDto;

import com.prasad.booking.Exception.*;

@Service
public class BookingServiceImpl implements IBookingService {

	@Autowired
	BookingRepository bookingRepo;

	@Autowired
	RestTemplate restTemplate;
	
	

	// method to book the products

	@Override
	public Booking addBooking(Booking booking) {
		Booking newBooking = new Booking();
		int productId = booking.getProductId();
		int customerId = booking.getCustomerId();

		try {
			ResponseEntity<ProductDto> responseEntity = restTemplate
					.getForEntity("http://localhost:8087/viewProduct/" + productId, ProductDto.class);
			ProductDto productDto = responseEntity.getBody();

			if (productDto != null) {
				newBooking.setProductId(productDto.getProductId());
				newBooking.setCharges(productDto.getCharges());
				newBooking.setCategory(productDto.getCategory());
				newBooking.setSize(productDto.getSize());
				newBooking.setName(productDto.getName());
			} else {
				throw new InvalidProductIdException("Product not found with the given Id");
			}
		} catch (HttpClientErrorException ex) {
			throw new InvalidProductIdException("Product not found with the given Id");
		}

		try {
			ResponseEntity<CustomerDto> responseEntity2 = restTemplate
					.getForEntity("http://localhost:8082/getCustomerByid/" + customerId, CustomerDto.class);
			CustomerDto customerDto = responseEntity2.getBody();

			if (customerDto != null) {
				newBooking.setCustomerId(customerDto.getCustomerId());
			} else {
				throw new CustomerNotFoundException("Customer not found with the given Id!");
			}
		} catch (HttpClientErrorException ex) {
			throw new CustomerNotFoundException("Customer not found with the given Id!");
		}

		newBooking.setDateTime(booking.getDateTime());
		newBooking = bookingRepo.save(newBooking);

		return newBooking;
	}

	// method to get the bookingDetails
	@Override
	public ResponseDto getBookingById(int bookingId) {

		Optional<Booking> opt = bookingRepo.findById(bookingId);
		ResponseDto responseDto = new ResponseDto();
		if (opt.isPresent()) {
			Booking b = opt.get();

			try {
				// Make a request to the product repository microservice
				ResponseEntity<ProductDto> responseEntity = restTemplate
						.getForEntity("http://localhost:8087/viewProduct/" + b.getProductId(), ProductDto.class);
				ProductDto productDto = responseEntity.getBody();
				responseDto.setProduct(productDto);
			} catch (InvalidProductIdException ex) {
				System.out.println("Invalid product ID");

			}

			try {
				// Make a request to the customer repository microservice
				ResponseEntity<CustomerDto> responseEntity2 = restTemplate
						.getForEntity("http://localhost:8082/getCustomerByid/" + b.getCustomerId(), CustomerDto.class);

				CustomerDto customerDto = responseEntity2.getBody();

				responseDto.setCustomer(customerDto);

			} catch (InvalidCustomerIdException ex) {
				System.out.println("Invalid customer ID");
			}
			return responseDto;

		} else {
			throw new BookingIdNotFoundException("Booking Id not found");

		}

	}

	@Override
	public String deleteById(int productId, int customerId) {

		// Check if productId and customerId are valid or not

		try {

			ResponseEntity<ProductDto> responseEntity = restTemplate
					.getForEntity("http://localhost:8087/viewProduct/" + productId, ProductDto.class);
		}

		catch (HttpClientErrorException ex) {

			throw new InvalidProductIdException("Invalid Product Id");

		}

		try {
			// Make a request to the customer repository microservice
			ResponseEntity<CustomerDto> responseEntity2 = restTemplate
					.getForEntity("http://localhost:8082/getCustomerByid/" + customerId, CustomerDto.class);
		} catch (HttpClientErrorException ex) {

			throw new InvalidCustomerIdException("Customer Id Not Found");

		}

		Iterable<Booking> bookings = bookingRepo.findAll();
		for (Booking booking : bookings) {
			if (booking.getProductId() == productId && booking.getCustomerId() == customerId) {
				bookingRepo.delete(booking);
				return "Booking deleted successfully";
			}
		}

		throw new InvalidBookingException("Booking not found");

	}

	@Override
	public Booking updateBooking(int bookingId, Booking updatedBooking) {
		Booking existingBooking = bookingRepo.findById(bookingId)
				.orElseThrow(() -> new BookingIdNotFoundException("Booking not found with the given ID"));

		int productId = updatedBooking.getProductId();
		int customerId = updatedBooking.getCustomerId();

		try {
			ResponseEntity<ProductDto> responseEntity = restTemplate
					.getForEntity("http://localhost:8087/viewProduct/" + productId, ProductDto.class);
			ProductDto productDto = responseEntity.getBody();

			if (productDto != null) {
				existingBooking.setProductId(productDto.getProductId());
				existingBooking.setCharges(productDto.getCharges());
			} else {
				throw new InvalidProductIdException("Product not found with the given ID");
			}
		} catch (HttpClientErrorException ex) {
			throw new InvalidProductIdException("Product not found with the given ID");
		}

		try {
			ResponseEntity<CustomerDto> responseEntity2 = restTemplate
					.getForEntity("http://localhost:8082/getCustomerByid/" + customerId, CustomerDto.class);
			CustomerDto customerDto = responseEntity2.getBody();

			if (customerDto != null) {
				existingBooking.setCustomerId(customerDto.getCustomerId());
			} else {
				throw new CustomerNotFoundException("Customer not found with the given ID");
			}
		} catch (HttpClientErrorException ex) {
			throw new CustomerNotFoundException("Customer not found with the given ID");
		}

		existingBooking.setDateTime(updatedBooking.getDateTime());

		existingBooking = bookingRepo.save(existingBooking);

		return existingBooking;
	}

	@Override
	public double calculateBill(int customerId) {

		List<Booking> bookings = bookingRepo.findAllByCustomerId(customerId);

		if (bookings.isEmpty()) {
			throw new NoProductsBookedException("You have not booked any product");
		}

		double sum = 0;

		for (Booking b : bookings) {

			sum = sum + b.getCharges();

		}
		
		// calculating tax
		double cgst = sum * 0.025; // 2.5% CGST
		double sgst = sum * 0.025; // 2.5% SGST

		sum = (cgst + sgst + sum);
		return sum;

		
		
	}

	@Override
	public List<Booking> getBooking() {
		return (List<Booking>) bookingRepo.findAll();
	}
	
	

}
