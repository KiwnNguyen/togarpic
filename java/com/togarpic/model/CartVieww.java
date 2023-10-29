package com.togarpic.model;

public class CartVieww extends Product{
	private int cart_id;
	private int quantity;

	
	public int getCart_id() {
		return cart_id;
	}

	public void setCart_id(int cart_id) {
		this.cart_id = cart_id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public CartVieww(int quantity) {
		super();
		this.quantity = quantity;
	}

	public CartVieww() {
		super();
	}
	
	
}
