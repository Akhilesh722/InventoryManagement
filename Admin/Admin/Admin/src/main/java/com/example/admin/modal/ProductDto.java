package com.example.admin.modal;


public class ProductDto {
	private int productId;
	private String name;
	private float charges;
	private Long quantity;

	public ProductDto(int productId, String name, float charges, Long quantity) {
		super();
		this.productId = productId;
		this.name = name;
		this.charges = charges;
		this.quantity = quantity;
	}
	
	public ProductDto() {
		
	}

	public ProductDto(int i, String string, double d) {
		// TODO Auto-generated constructor stub
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
