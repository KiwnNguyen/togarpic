package com.togarpic.model;

import java.sql.Date;

public class Order1 {
	private long ord_id;
	private long usr_id;
	private Date ord_date;
	private float ord_totalAmount;
	private int ord_status;
	private String ord_address;
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
	public String getOrd_address() {
		return ord_address;
	}
	public void setOrd_address(String ord_address) {
		this.ord_address = ord_address;
	}
	
}