package com.togarpic.model;

public class CartItem {
	private long car_id;
	private long pro_id;
	private float cai_quantity;
	
	
	
	public CartItem(long car_id, long pro_id, float cai_quantity) {
		super();
		this.car_id = car_id;
		this.pro_id = pro_id;
		this.cai_quantity = cai_quantity;
	}
	
	
	public CartItem() {
		// TODO Auto-generated constructor stub
	}


	public long getCar_id() {
		return car_id;
	}
	public void setCar_id(long car_id) {
		this.car_id = car_id;
	}
	public long getPro_id() {
		return pro_id;
	}
	public void setPro_id(long pro_id) {
		this.pro_id = pro_id;
	}
	public float getCai_quantity() {
		return cai_quantity;
	}
	public void setCai_quantity(float cai_quantity) {
		this.cai_quantity = cai_quantity;
	}


	@Override
	public String toString() {
		return "CartItem [car_id=" + car_id + ", pro_id=" + pro_id + ", cai_quantity=" + cai_quantity + "]";
	}
	
	
	
}
