package com.example.admin.service;

import java.util.List;
import java.util.Optional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.example.admin.Entity.Admin;
import com.example.admin.Entity.Product;
import com.example.admin.repo.AdminRepo;

import reactor.core.publisher.Mono;

import com.example.admin.exception.*;
import com.example.admin.modal.BookingDetailsDto;
import com.example.admin.modal.BookingDto;
import com.example.admin.modal.CustomerDto;
import com.example.admin.modal.ProductDto;
import com.example.admin.modal.ResponseDto;

@Service
public class AdminServiceImpl implements AdminService {
	@Value("${customer.url")
	private String customerURL;
	@Value("${product.url")
	private String productURL;
	@Value("${booking.url")
	private String bookingURL;
	
	@Autowired
	AdminRepo adminRepo;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public Admin createAdmin(Admin admin) {
		Admin ad = adminRepo.findById(admin.getId()).orElse(null);
		if (ad == null) {
			return adminRepo.save(admin);

		} else {
			throw new NoAdminFoundException("Admin Already Exists");
		}
	}
 
	@Override
	public String deleteAdmin(int id) {
		Optional<Admin> opt = adminRepo.findById(id);
		if (opt.isPresent()) {
			adminRepo.deleteById(id);
			return "Deleted Successfully";
		} else {
			throw new NoAdminFoundException("Admin not found with the given id");
		}

	}

	
	@Override
	public Admin getAdminbyid(int id) {
		Optional<Admin> opt = adminRepo.findById(id);

		if (opt.isPresent()) {
			Admin a = opt.get();
			adminRepo.findById(id);
			return a;
		} else {
			throw new NoAdminFoundException("Admin not found with the given id");
		}
	}

//
//	public List<ProductResponse> viewAllProducts() {
//		List<ProductResponse> products = webClient.get().uri("/getAllProducts").retrieve()
//				.bodyToMono(new ParameterizedTypeReference<List<ProductResponse>>() {
//				}).block();
//		return products;
//	}
//
	public List<ProductDto> viewAllProducts() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<ProductDto>> response = restTemplate.exchange(productURL+"/getAllProducts/",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductDto>>() {
				});
		List<ProductDto> products = response.getBody();
		return products;
	}
//

	public ProductDto getProductById(int productId) {

		try {
			ResponseEntity<ProductDto> responseEntity = restTemplate
					.getForEntity(productURL+"/viewProduct/" + productId, ProductDto.class);
			ProductDto productDto = responseEntity.getBody();
			System.out.println(productDto.getName());
			return productDto;

		} catch (HttpClientErrorException ex) {
			throw new InvalidProductIdException("The given product id does not exist");
		}

	}

//
//	public List<CustomerResponse> viewAllCustomers() {
//		List<CustomerResponse> customers = webClient2.get().uri("/getCustomers/").retrieve()
//				.bodyToMono(new ParameterizedTypeReference<List<CustomerResponse>>() {
//				}).block();
//		return customers;
//	}
	public List<CustomerDto> viewAllCustomers() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List<CustomerDto>> response = restTemplate.exchange(customerURL+"/getCustomers/",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<CustomerDto>>() {
				});
		List<CustomerDto> customers = response.getBody();
		return customers;
	}

//
//	public Mono<CustomerResponse> getCustomerById(int customerId) {
//		return webClient2.get().uri("/getCustomerByid/{userid}", customerId).retrieve()
//				.bodyToMono(CustomerResponse.class);
//	}
	public CustomerDto getCustomerById(int customerId) {

		try {
			ResponseEntity<CustomerDto> responseEntity = restTemplate
					.getForEntity(customerURL+"/getCustomerByid/" + customerId, CustomerDto.class);
			CustomerDto customerDto = responseEntity.getBody();

			return customerDto;

		} catch (HttpClientErrorException ex) {
			throw new InvalidCustomerIDException("The given customer does not exist");
		}
	}

	public BookingDetailsDto getBookingById(int bookingId) {
		try {
			ResponseEntity<BookingDetailsDto> responseEntity = restTemplate
					.getForEntity(bookingURL+"/viewBookingDetailsById/" + bookingId, BookingDetailsDto.class);

			BookingDetailsDto responseDto = responseEntity.getBody();
			System.out.println(responseDto.getCustomerName());
			return responseDto;
		} catch (Exception ex) {
			throw new InvalidBookingIdException("Given Booking Id doesnot exist");
		}

	}

	
	@Override
	public Admin getAdminbyEmail(String email) throws NoAdminFoundException {
		// TODO Auto-generated method stub
		Optional<Admin> c = adminRepo.findByEmail(email);
		if (c.isPresent()) {
			Admin cus = c.get();
			return cus;
		} else {
			throw new NoAdminFoundException("Admin not found with given Email");
		}
	}

}
