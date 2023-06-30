package com.prasad.booking.dto;

public class ResponseDto {

	private ProductDto product;
	private CustomerDto customer;

	public ResponseDto() {
	}

	public ResponseDto(ProductDto product, CustomerDto customer) {
		this.product = product;
		this.customer = customer;
	}
	

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}

	public CustomerDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}

}
