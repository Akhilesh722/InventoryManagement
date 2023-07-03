package com.example.customer;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.customer.Dto.ProductDto;
import com.example.customer.Entity.Customer;
import com.example.customer.Exception.ProductNotFoundException;
import com.example.customer.Service.ICustomerService;

@RestController
@CrossOrigin("http://localhost:3000")
public class CustomerController {

	@Autowired
	ICustomerService customerservice;

	@PostMapping("/addCustomer")
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
		Customer p1 = customerservice.addCustomer(customer);
		return new ResponseEntity<>(p1, HttpStatus.CREATED);

	}
	

	@GetMapping("/getCustomerByid/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable int id) {
		Customer p1 = customerservice.getCustomerByid(id);
		return new ResponseEntity<>(p1, HttpStatus.CREATED);

	}

	@DeleteMapping("/deleteCustomer/{userid}")

	public ResponseEntity<Customer> deleteCustomer(@PathVariable int userid) {
		Customer p1 = customerservice.deleteCustomer(userid);
		return new ResponseEntity<>(p1, HttpStatus.CREATED);

	}

	@PutMapping("/updateCustomer/{userid}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable int userid, @RequestBody Customer customer) {
		Customer c1 = customerservice.updateCustomer(userid, customer);
		return new ResponseEntity<>(c1, HttpStatus.CREATED);

	}

	@GetMapping("/getCustomers/")
	public
	ResponseEntity<List<Customer>> getCustomers() {
		List<Customer> p1 = customerservice.getCustomers();
		return new ResponseEntity<>(p1, HttpStatus.CREATED);

	}

	@GetMapping("/getEmail/{customerId}")
	public ResponseEntity<String> getEmail(@PathVariable int customerId) {
		String save = customerservice.getCustomerEmail(customerId);
		return new ResponseEntity<>(save, HttpStatus.CREATED);

	}

	@GetMapping("/getPassword/{customerId}")
	public ResponseEntity<String> getPassword(@PathVariable int customerId) {
		String save = customerservice.getCustomerPassword(customerId);
		return new ResponseEntity<>(save, HttpStatus.CREATED);
	}

	@GetMapping("/getCustomerByEmail/{email}")
	public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
		Customer save = customerservice.getCustomerByEmail(email);
		return new ResponseEntity<>(save, HttpStatus.CREATED);

	}

	@GetMapping("/viewProductById/{id}")
	public ResponseEntity<ProductDto> viewProductById(@PathVariable int id) throws ProductNotFoundException {
		ProductDto save = customerservice.viewProductById(id);
		return new ResponseEntity<>(save, HttpStatus.CREATED);

	}
	@GetMapping("/viewAllProducts/")
	ResponseEntity<List<ProductDto>> viewAllProducts() {
		List<ProductDto> p1 = customerservice.viewAllProducts();
		return new ResponseEntity<>(p1, HttpStatus.CREATED);

	}
	
	@PutMapping("/updateCustomerByEmail/{email}")
	public ResponseEntity<Customer> updateCustomerByEmail(@PathVariable String email, @RequestBody Customer customer) {
		Customer c1 = customerservice.updateCustomerByEmail(email, customer);
		return new ResponseEntity<>(c1, HttpStatus.CREATED);

	}

}
