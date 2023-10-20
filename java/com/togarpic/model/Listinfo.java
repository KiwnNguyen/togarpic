package com.togarpic.model;

import java.sql.Date;

public class Listinfo {
	private String pro_name;
	private float ord_totalAmount;
	private int odt_quantity;
	private float odt_exportPrice;
	private String rev_content;
	private Date ord_date;
	
	
	
	public Listinfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Listinfo(String pro_name, float ord_totalAmount, int odt_quantity, float odt_exportPrice, String rev_content,
			Date ord_date) {
		super();
		this.pro_name = pro_name;
		this.ord_totalAmount = ord_totalAmount;
		this.odt_quantity = odt_quantity;
		this.odt_exportPrice = odt_exportPrice;
		this.rev_content = rev_content;
		this.ord_date = ord_date;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public float getOrd_totalAmount() {
		return ord_totalAmount;
	}
	public void setOrd_totalAmount(float ord_totalAmount) {
		this.ord_totalAmount = ord_totalAmount;
	}
	public int getOdt_quantity() {
		return odt_quantity;
	}
	public void setOdt_quantity(int odt_quantity) {
		this.odt_quantity = odt_quantity;
	}
	public float getOdt_exportPrice() {
		return odt_exportPrice;
	}
	public void setOdt_exportPrice(float odt_exportPrice) {
		this.odt_exportPrice = odt_exportPrice;
	}
	public String getRev_content() {
		return rev_content;
	}
	public void setRev_content(String rev_content) {
		this.rev_content = rev_content;
	}
	public Date getOrd_date() {
		return ord_date;
	}
	public void setOrd_date(Date ord_date) {
		this.ord_date = ord_date;
	}
	@Override
	public String toString() {
		return "Listinfo [pro_name=" + pro_name + ", ord_totalAmount=" + ord_totalAmount + ", odt_quantity="
				+ odt_quantity + ", odt_exportPrice=" + odt_exportPrice + ", rev_content=" + rev_content + ", ord_date="
				+ ord_date + "]";
	}
	
	
	
	
}
