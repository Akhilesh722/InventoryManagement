package com.prasad.booking.dto;

public class ProductDto {

	private int productId;
	private String name;
	private float charges;
	private Long quantity;
	private String category;
	private String size;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
		
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getCharges() {
		return charges;
	}

	public void setCharges(float charges) {
		this.charges = charges;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

}
