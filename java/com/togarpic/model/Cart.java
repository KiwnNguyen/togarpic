package com.togarpic.model;

public class Cart {
	private long car_id;
	private long usr_id;
	
	
	
	public Cart(long car_id, long usr_id) {
		super();
		this.car_id = car_id;
		this.usr_id = usr_id;
	}
	public long getCar_id() {
		return car_id;
	}
	public void setCar_id(long car_id) {
		this.car_id = car_id;
	}
	public long getUsr_id() {
		return usr_id;
	}
	public void setUsr_id(long usr_id) {
		this.usr_id = usr_id;
	}
	@Override
	public String toString() {
		return "Cart [car_id=" + car_id + ", usr_id=" + usr_id + "]";
	}
	
}
