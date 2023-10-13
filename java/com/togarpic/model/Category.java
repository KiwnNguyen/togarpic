package com.togarpic.model;

public class Category {
	private int stt;
	private int id;
	private String cat_name;
	
	public Category(int stt, int id, String cat_name) {
		super();
		this.stt = stt;
		this.id = id;
		this.cat_name = cat_name;
	}

	public Category() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCat_name() {
		return cat_name;
	}

	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}
	
	
	public int getStt() {
		return stt;
	}

	public void setStt(int stt) {
		this.stt = stt;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", cat_name=" + cat_name + "]";
	}
}
