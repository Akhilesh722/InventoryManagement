package com.prasad.product.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prasad.product.Entity.Product;
import com.prasad.product.Exceptions.NoSuchProductExistsException;
import com.prasad.product.Exceptions.ProductAlreadyExistsException;
import com.prasad.product.Exceptions.SizeNotFoundException;
import com.prasad.product.Exceptions.ZeroChargeException;
import com.prasad.product.Repository.IProductRepository;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	IProductRepository productRepo;

	@Override
	public Product addProduct(Product product) {
		// Product pr = productRepo.findById(product.getProductId()).orElse(null);
		return productRepo.save(product);

	}

	@Override
	public Product updateProduct(int id, Product product) {
		Optional<Product> productopt = productRepo.findById(id);

		if (productopt.isPresent()) {
			Product p1 = productopt.get();
			p1.setName(product.getName());
			p1.setCharges(product.getCharges());
			p1.setQuantity(product.getQuantity());

			return productRepo.save(p1);

		}
		throw new NoSuchProductExistsException("product not found");

	}

	@Override
	public String deleteProduct(int id) {
		Optional<Product> productopt = productRepo.findById(id);
		if (productopt.isPresent()) {
			Product p1 = productopt.get();
			productRepo.delete(p1);

			return "Product deleted successfully";
		} else {
			throw new NoSuchProductExistsException("product not found");
		}

	}

	@Override

	public Product getProduct(int id) {

		try {

			Optional<Product> opt = productRepo.findById(id);

			if (opt.isPresent()) {

				Product pr = opt.get();

				return pr;

			} else {

				throw new NoSuchProductExistsException("Product ID not found");

			}

		} catch (NoSuchProductExistsException ex) {

			throw ex; // Re-throw the exception for handling at a higher level

		} catch (Exception ex) {

			throw new RuntimeException("Error retrieving product", ex);

		}

	}

	@Override
	public List<Product> viewAllProducts() {

		return (List<Product>) productRepo.findAll();
	}

	@Override
	public List<Product> viewProductsByCharges(float charges) {

		if (charges != 0) {
			List<Product> ac = (List<Product>) productRepo.findAll();

			List<Product> act = new ArrayList<>();

			for (Product a : ac) {
				if (a.getCharges() == charges) {
					act.add(a);
				}
			}
			return act;
		} else {
			throw new ZeroChargeException("Enter a Non-Zero value for charges");
		}

	}

	@Override
	public List<Product> viewProductsBySize(String size) throws SizeNotFoundException {
		if (size != null) {
			List<Product> s = (List<Product>) productRepo.findAll();
			List<Product> s1 = new ArrayList<>();
			for (Product s2 : s) {
				if (s2.getSize().equals(size)) {
					s1.add(s2);
				}
			}
			return s1;
		} else {
			throw new SizeNotFoundException("product not found with givrn size");
		}

	}

}
