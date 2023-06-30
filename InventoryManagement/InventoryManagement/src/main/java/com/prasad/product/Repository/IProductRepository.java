package com.prasad.product.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prasad.product.Entity.Product;

@Repository
public interface IProductRepository extends CrudRepository<Product, Integer> {

}
