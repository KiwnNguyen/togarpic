package com.togarpic.model;

import java.sql.Timestamp;

public class Storage {
	private int sto_id;
	private int pro_id;
	private Timestamp sto_date;
	private float sto_price;
	private int sto_quantity;

	public int getSto_id() {
		return sto_id;
	}

	public void setSto_id(int sto_id) {
		this.sto_id = sto_id;
	}

	public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}

	public Timestamp getSto_date() {
		return sto_date;
	}

	public void setSto_date(Timestamp sto_date) {
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

}
