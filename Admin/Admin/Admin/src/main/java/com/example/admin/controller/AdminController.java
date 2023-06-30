package com.example.admin.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.admin.Entity.Admin;
import com.example.admin.exception.NoAdminFoundException;
import com.example.admin.modal.BookingDetailsDto;
import com.example.admin.modal.BookingDto;
import com.example.admin.modal.CustomerDto;
import com.example.admin.modal.ProductDto;
import com.example.admin.modal.ResponseDto;
import com.example.admin.service.AdminServiceImpl;
//import com.prasad.customer.Entity.Customer;

import org.apache.logging.log4j.Logger;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
	@Autowired
	AdminServiceImpl impl;

	private static Logger logger = LogManager.getLogger();

	@PostMapping("/addAdmin")
	public ResponseEntity<Admin> insertAdmin(@RequestBody Admin admin) {
		logger.info("sending request to Add Admin");
		Admin save = impl.createAdmin(admin);
		logger.info("Admin added");
		return new ResponseEntity<>(save, HttpStatus.OK);
	}

	@DeleteMapping("/deleteAdmin/{id}")
	public ResponseEntity<String> deleteAdmin(@PathVariable int id) {
		logger.info("sending request to delete Admin");
		String save = impl.deleteAdmin(id);
		logger.info("Admin Deleted");
		return new ResponseEntity<>(save, HttpStatus.OK);

	}

	@GetMapping("/getAdmin/{id}")
	public ResponseEntity<Admin> getAdminbyId(@PathVariable int id) {
		logger.info("sending request to get Admin details");
		Admin save = impl.getAdminbyid(id);
		logger.info("Fetched Admin details");
		return new ResponseEntity<>(save, HttpStatus.OK);
	}

	@GetMapping("/viewAllProducts")
	public List<ProductDto> getAllProducts() {
		logger.info("sending request to view all products");
		return impl.viewAllProducts();

	}
//    @GetMapping("/getProductID")
//    public int[] viewIDofProduct() {
//    	return impl.getProductID();
//    }
//    @PostMapping("/addProduct")
//    public ProductResponse addingProduct(@RequestBody Product product) {
//    	return impl.addProduct(product);
//    }

	@GetMapping("/getProduct/{id}")
	public ResponseEntity<ProductDto> getProductbyID(@PathVariable int id) {
		logger.info("sending request to get product by id");
		ProductDto save = impl.getProductById(id);
		logger.info("fetched product details succesfully");
		return new ResponseEntity<>(save, HttpStatus.OK);
	}

	@GetMapping("/viewAllCustomers")
	public List<CustomerDto> getAllCustomers() {
		logger.info("sending request to view all customers");
		return impl.viewAllCustomers();
	}

//	@GetMapping("/getCustomerbyID/{id}")
//	public Mono<CustomerResponse> getCustomerbyId(@PathVariable("id") int id){
//		return impl.getCustomerById(id);
//	}
//}
	@GetMapping("/getBookingById/{id}")
	public ResponseEntity<BookingDetailsDto> getBookingbyID(@PathVariable int id) {
		logger.info("sending request to get booking by id");
		BookingDetailsDto save = impl.getBookingById(id);
		logger.info("fetched booking details");
		return new ResponseEntity<>(save, HttpStatus.OK);
	}
	
	@GetMapping("/getAdminByEmail/{email}")
	public ResponseEntity<Admin> getAdminbyEmail(@PathVariable String email) {
		Admin save = impl.getAdminbyEmail(email);
		return new ResponseEntity<>(save, HttpStatus.CREATED);
}
	@GetMapping("/getcustomerbyId/{id}")
	public ResponseEntity<CustomerDto> getCustomerbyEmail(@PathVariable int id){
		CustomerDto s=impl.getCustomerById(id);
		return new ResponseEntity<>(s,HttpStatus.CREATED);
	}
}
