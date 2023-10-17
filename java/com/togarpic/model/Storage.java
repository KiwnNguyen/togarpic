package com.togarpic.model;

import java.sql.Date;

public class Storage {
	private long sto_id;
	private long pro_id;
	private Date sto_date;
	private float sto_price;
	private int sto_quantity;
	private boolean sto_enable;
	
	
	
	
	public Storage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Storage(long sto_id, long pro_id, Date sto_date, float sto_price, int sto_quantity, boolean sto_enable) {
		super();
		this.sto_id = sto_id;
		this.pro_id = pro_id;
		this.sto_date = sto_date;
		this.sto_price = sto_price;
		this.sto_quantity = sto_quantity;
		this.sto_enable = sto_enable;
	}
	public long getSto_id() {
		return sto_id;
	}
	public void setSto_id(long sto_id) {
		this.sto_id = sto_id;
	}
	public long getPro_id() {
		return pro_id;
	}
	public void setPro_id(long pro_id) {
		this.pro_id = pro_id;
	}
	public Date getSto_date() {
		return sto_date;
	}
	public void setSto_date(Date sto_date) {
		this.sto_date = sto_date;
	}
	public float getSto_price() {
		return sto_price;
	}
	public void setSto_price(float sto_price) {
		this.sto_price = sto_price;
	}
	public int getSto_quantity() {
		return sto_quantity;
	}
	public void setSto_quantity(int sto_quantity) {
		this.sto_quantity = sto_quantity;
	}
	public boolean isSto_enable() {
		return sto_enable;
	}
	public void setSto_enable(boolean sto_enable) {
		this.sto_enable = sto_enable;
	}
	@Override
	public String toString() {
		return "Storage [sto_id=" + sto_id + ", pro_id=" + pro_id + ", sto_date=" + sto_date + ", sto_price="
				+ sto_price + ", sto_quantity=" + sto_quantity + ", sto_enable=" + sto_enable + "]";
	}
	
	
	
	
	
	
}
