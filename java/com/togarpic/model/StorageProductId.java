package com.togarpic.model;

public class StorageProductId {
	private long pro_id;
	private long sto_id;
	private String pro_name;
	private float pro_price;
	private float sto_price;
	private int sto_quantity;
	public long getPro_id() {
		return pro_id;
	}
	public void setPro_id(long pro_id) {
		this.pro_id = pro_id;
	}
	public long getSto_id() {
		return sto_id;
	}
	public void setSto_id(long sto_id) {
		this.sto_id = sto_id;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public float getPro_price() {
		return pro_price;
	}
	public void setPro_price(float pro_price) {
		this.pro_price = pro_price;
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
	public StorageProductId(long pro_id, long sto_id, String pro_name, float pro_price, float sto_price,
			int sto_quantity) {
		super();
		this.pro_id = pro_id;
		this.sto_id = sto_id;
		this.pro_name = pro_name;
		this.pro_price = pro_price;
		this.sto_price = sto_price;
		this.sto_quantity = sto_quantity;
	}
	public StorageProductId() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
