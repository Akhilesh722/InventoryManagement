package com.prasad.product.Service;

import java.util.List;

import com.prasad.product.Entity.Product;
import com.prasad.product.Exceptions.SizeNotFoundException;


public interface IProductService {
	
	public Product addProduct(Product product);
	
	public Product updateProduct(int id,Product product);
	
	public String deleteProduct(int id);
	
	public Product getProduct(int id);
	
	public List<Product> viewAllProducts();
	
	public List<Product> viewProductsByCharges(float charges);
	
	public List<Product> viewProductsBySize(String size) throws SizeNotFoundException;
	
	

}
