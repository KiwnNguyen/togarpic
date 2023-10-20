package com.togarpic.model;

public class CartView {
	private long car_id;
	private long usr_id;
	private String usr_name;
	
	
	public CartView(long car_id, long usr_id, String usr_name) {
		super();
		this.car_id = car_id;
		this.usr_id = usr_id;
		this.usr_name = usr_name;
	}
	
	public CartView() {
		// TODO Auto-generated constructor stub
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
	public String getUsr_name() {
		return usr_name;
	}
	public void setUsr_name(String usr_name) {
		this.usr_name = usr_name;
	}
	@Override
	public String toString() {
		return "CartView [car_id=" + car_id + ", usr_id=" + usr_id + ", usr_name=" + usr_name + ", getCar_id()="
				+ getCar_id() + ", getUsr_id()=" + getUsr_id() + ", getUsr_name()=" + getUsr_name() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
