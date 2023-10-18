package com.togarpic.model;

public class CartItemView {
	private long car_id;
	private long pro_id;
	private String pro_name;
	private float cai_quantity;
	public CartItemView(long car_id, long pro_id, String pro_name, float cai_quantity) {
		super();
		this.car_id = car_id;
		this.pro_id = pro_id;
		this.pro_name = pro_name;
		this.cai_quantity = cai_quantity;
	}
	
	public CartItemView() {
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
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public float getCai_quantity() {
		return cai_quantity;
	}
	public void setCai_quantity(float cai_quantity) {
		this.cai_quantity = cai_quantity;
	}
	@Override
	public String toString() {
		return "CartItemView [car_id=" + car_id + ", pro_id=" + pro_id + ", pro_name=" + pro_name + ", cai_quantity="
				+ cai_quantity + ", getCar_id()=" + getCar_id() + ", getPro_id()=" + getPro_id() + ", getPro_name()="
				+ getPro_name() + ", getCai_quantity()=" + getCai_quantity() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
