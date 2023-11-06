package com.togarpic.model;

import java.util.List;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class OrderCheck {
	  private int odt_quantity;
	  private String pro_name;
	  private String price1;
	  private String usr_firstName;
	  private String usr_lastName;
	  private String ord_Address;
	  private String usr_email;
	  private String usr_phone;
	  private float ord_totalAmount;
	  private Date Date;
	public int getOdt_quantity() {
		return odt_quantity;
	}
	public void setOdt_quantity(int odt_quantity) {
		this.odt_quantity = odt_quantity;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getPrice1() {
		return price1;
	}
	public void setPrice1(String price1) {
		this.price1 = price1;
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
	public String getOrd_Address() {
		return ord_Address;
	}
	public void setOrd_Address(String ord_Address) {
		this.ord_Address = ord_Address;
	}
	public String getUsr_email() {
		return usr_email;
	}
	public void setUsr_email(String usr_email) {
		this.usr_email = usr_email;
	}
	public String getUsr_phone() {
		return usr_phone;
	}
	public void setUsr_phone(String usr_phone) {
		this.usr_phone = usr_phone;
	}
	public float getOrd_totalAmount() {
		return ord_totalAmount;
	}
	public void setOrd_totalAmount(float ord_totalAmount) {
		this.ord_totalAmount = ord_totalAmount;
	}
	
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		Date = date;
	}
	public OrderCheck(int odt_quantity, String pro_name, String price1, String usr_firstName, String usr_lastName,
			String ord_Address, String usr_email, String usr_phone, float ord_totalAmount,Date date) {
		super();
		this.odt_quantity = odt_quantity;
		this.pro_name = pro_name;
		this.price1 = price1;
		this.usr_firstName = usr_firstName;
		this.usr_lastName = usr_lastName;
		this.ord_Address = ord_Address;
		this.usr_email = usr_email;
		this.usr_phone = usr_phone;
		this.ord_totalAmount = ord_totalAmount;
		this.Date = date; 
	}
	public OrderCheck() {
		super();
		// TODO Auto-generated constructor stub
	}
	
		  
	  
}
