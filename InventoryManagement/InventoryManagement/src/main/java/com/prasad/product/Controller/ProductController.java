package com.prasad.product.Controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.prasad.product.Entity.Product;
import com.prasad.product.Exceptions.SizeNotFoundException;
import com.prasad.product.Service.ProductServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:3000") 
public class ProductController {

	@Autowired
	ProductServiceImpl impl;
	
	private static Logger logger = LogManager.getLogger();

	@PostMapping("/addProduct")
	public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
		logger.info("Sending request to Add a product");
		Product save = impl.addProduct(product);
		logger.info("Product Added");
		return new ResponseEntity<>(save, HttpStatus.OK);

	}
	
	@PutMapping("/updateProduct/{id}")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product,@PathVariable int id){
		logger.info("Sending request to update a product");
		Product save = impl.updateProduct(id, product);
		logger.info("Product updated");
		return new ResponseEntity<>(save,HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id){
		logger.info("Sending request to delete a product");
		String save = impl.deleteProduct(id);
		logger.info("Product Deleted");
		return new ResponseEntity<>(save,HttpStatus.OK);
	}
	
	@GetMapping("/viewProduct/{id}")
	public ResponseEntity<Product> viewProduct(@PathVariable int id){
		logger.info("Sending request to view a Product by id");
		Product save = impl.getProduct(id);
		logger.info("viewing a product from the Database");
		return new ResponseEntity<>(save,HttpStatus.OK);
	}
	
	@GetMapping("/getAllProducts")
	public ResponseEntity<List<Product>> viewAllProducts(){
		logger.info("Sending request to view all Products");
		List<Product> save = impl.viewAllProducts();
		logger.info("All Products received");
		return new ResponseEntity<>(save,HttpStatus.OK);
	}
	
	@GetMapping("/getProductsByChrges/{charges}")
	public ResponseEntity<List<Product>> viewProductsByChrges(@PathVariable float charges){
		logger.info("Sending request to view all Products of same charge");
		List<Product> save = impl.viewProductsByCharges(charges);
		logger.info("Products recieved");
		return new ResponseEntity<>(save,HttpStatus.OK);
		
	}
	@GetMapping("/getProductsBySize/{size}")
	public ResponseEntity<List<Product>> viewProductsBySize(@PathVariable String size) throws SizeNotFoundException{
		logger.info("Sending request to view all Products of same size");
		List<Product> save = impl.viewProductsBySize(size);
		logger.info("Products recieved");
		return new ResponseEntity<>(save,HttpStatus.OK);
	}

}
