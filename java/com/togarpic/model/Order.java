package com.togarpic.model;

import java.sql.Date;



public class Order {
	private long ord_id;
	private long usr_id;
	private Date ord_date;
	private float ord_totalAmount;
	private int ord_status;
	private long usr_id1;
	private String usr_telephone;
	private String usr_firstName;
	private String usr_lastName;
	  private String ord_date_formatted;
	  private String ord_address;
	  
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Order(long ord_id, long usr_id, Date ord_date, float ord_totalAmount, int ord_status, long usr_id1,
			String usr_telephone, String usr_firstName, String usr_lastName,String ord_date_formatted,String ord_address) {
		super();
		this.ord_id = ord_id;
		this.usr_id = usr_id;
		this.ord_date = ord_date;
		this.ord_totalAmount = ord_totalAmount;
		this.ord_status = ord_status;
		this.usr_id1 = usr_id1;
		this.usr_telephone = usr_telephone;
		this.usr_firstName = usr_firstName;
		this.usr_lastName = usr_lastName;
		this.ord_date_formatted = ord_date_formatted;
		this.ord_address = ord_address;
	}



	public String getUsr_firstName() {
		return usr_firstName;
	}



	public void setUsr_firstName(String usr_firstName) {
		this.usr_firstName = usr_firstName;
	}



	public String getUsr_lastName() {
		return usr_lastName;
	}



	public void setUsr_lastName(String usr_lastName) {
		this.usr_lastName = usr_lastName;
	}



	public Order(long ord_id, long usr_id, Date ord_date, float ord_totalAmount, int ord_status) {
		super();
		this.ord_id = ord_id;
		this.usr_id = usr_id;
		this.ord_date = ord_date;
		this.ord_totalAmount = ord_totalAmount;
		this.ord_status = ord_status;
	}

	public long getOrd_id() {
		return ord_id;
	}
	public void setOrd_id(long ord_id) {
		this.ord_id = ord_id;
	}
	public long getUsr_id() {
		return usr_id;
	}
	public void setUsr_id(long usr_id) {
		this.usr_id = usr_id;
	}
	public Date getOrd_date() {
		return ord_date;
	}
	public void setOrd_date(Date ord_date) {
		this.ord_date = ord_date;
	}
	public float getOrd_totalAmount() {
		return ord_totalAmount;
	}
	public void setOrd_totalAmount(float ord_totalAmount) {
		this.ord_totalAmount = ord_totalAmount;
	}
	public int getOrd_status() {
		return ord_status;
	}
	public void setOrd_status(int ord_status) {
		this.ord_status = ord_status;
	}
	public long getUsr_id1() {
		return usr_id1;
	}
	public void setUsr_id1(long usr_id1) {
		this.usr_id1 = usr_id1;
	}
	
	public String getUsr_telephone() {
		return usr_telephone;
	}

	public void setUsr_telephone(String usr_telephone) {
		this.usr_telephone = usr_telephone;
	}

	public String getOrd_date_formatted() {
		return ord_date_formatted;
	}



	public void setOrd_date_formatted(String ord_date_formatted) {
		this.ord_date_formatted = ord_date_formatted;
	}



	public String getOrd_address() {
		return ord_address;
	}



	public void setOrd_address(String ord_address) {
		this.ord_address = ord_address;
	}



	@Override
	public String toString() {
		return "order [ord_id=" + ord_id + ", usr_id=" + usr_id + ", ord_date=" + ord_date + ", ord_totalAmount="
				+ ord_totalAmount + ", ord_status=" + ord_status + "]";
	}
	
	
	
	
	


}

