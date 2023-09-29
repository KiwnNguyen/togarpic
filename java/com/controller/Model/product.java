package com.controller.Model;

public class product {
	private long pro_id;
	private String pro_name;
	private float pro_price;
	private String pro_image;
	private boolean pro_enable;
	private long cat_id;
	public product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public product(long pro_id, String pro_name, float pro_price, String pro_image, boolean pro_enable, long cat_id) {
		super();
		this.pro_id = pro_id;
		this.pro_name = pro_name;
		this.pro_price = pro_price;
		this.pro_image = pro_image;
		this.pro_enable = pro_enable;
		this.cat_id = cat_id;
	}
	public long getPro_id() {
		return pro_id;
	}
	public void setPro_id(long pro_id) {
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
	public boolean isPro_enable() {
		return pro_enable;
	}
	public void setPro_enable(boolean pro_enable) {
		this.pro_enable = pro_enable;
	}
	public long getCat_id() {
		return cat_id;
	}
	public void setCat_id(long cat_id) {
		this.cat_id = cat_id;
	}
	@Override
	public String toString() {
		return pro_id + "===" + pro_name + "===" + pro_price + "==="
				+ pro_image + "===" + pro_enable + "===" + cat_id ;
	}
	
	
	
	
}
