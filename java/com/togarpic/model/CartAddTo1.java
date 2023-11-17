package com.togarpic.model;

public class CartAddTo1 extends Product{
	private long usr_id;
	

	public CartAddTo1(long usr_id, float sto_price) {
		super();
		this.usr_id = usr_id;
	
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
