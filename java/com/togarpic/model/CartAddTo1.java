package com.togarpic.model;

public class CartAddTo1 extends Product{
	private long usr_id;
	private float sto_price;

	public CartAddTo1(long usr_id, float sto_price) {
		super();
		this.usr_id = usr_id;
		this.sto_price = sto_price;
	}

	public float getSto_price() {
		return sto_price;
	}

	public void setSto_price(float sto_price) {
		this.sto_price = sto_price;
	}

	public long getUsr_id() {
		return usr_id;
	}

	public void setUsr_id(long usr_id) {
		this.usr_id = usr_id;
	}

	public CartAddTo1() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartAddTo1(long usr_id) {
		super();
		this.usr_id = usr_id;
	}
	
}
