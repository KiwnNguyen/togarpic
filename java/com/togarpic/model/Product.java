package com.togarpic.model;

public class Product {
	private int pro_id;
	private String pro_name;
	private float pro_price;
	private String pro_image;
	private int pro_enable;
	private int cat_id;

	public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
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

	public String getPro_image() {
		return pro_image;
	}

	public void setPro_image(String pro_image) {
		this.pro_image = pro_image;
	}

	public int getPro_enable() {
		return pro_enable;
	}

	public void setPro_enable(int pro_enable) {
		this.pro_enable = pro_enable;
	}

	public int getCat_id() {
		return cat_id;
	}

	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
	}
}
