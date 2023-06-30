package com.example.admin.modal;

public class ResponseDto {

	private ProductDto productDto;
	private CustomerDto customerDto;

	public ResponseDto(ProductDto productDto, CustomerDto customerDto) {
		super();
		this.productDto = productDto;
		this.customerDto = customerDto;
	}
	
	public ResponseDto() {
		
	}

	public ProductDto getProductDto() {
		return productDto;
	}

	public void setProductDto(ProductDto productDto) {
		this.productDto = productDto;
	}

	public CustomerDto getCustomerDto() {
		return customerDto;
	}

	public void setCustomerDto(CustomerDto customerDto) {
		this.customerDto = customerDto;
	}

}
