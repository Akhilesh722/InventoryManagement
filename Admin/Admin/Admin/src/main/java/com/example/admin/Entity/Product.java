package com.example.admin.Entity;

public class Product {
	private int productId;
	private String name;
	private float charges;
	private Long qunatity;
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
	public Long getQunatity() {
		return qunatity;
	}
	public void setQunatity(Long qunatity) {
		this.qunatity = qunatity;
	}
}
