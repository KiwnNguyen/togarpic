package com.togarpic.model;

public class Recipe {
	private int id;
	private String rec_name;

	public Recipe(int id, String rec_name) {
		super();
		this.id = id;
		this.rec_name = rec_name;
	}

	public Recipe() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRec_name() {
		return rec_name;
	}

	public void setRec_name(String rec_name) {
		this.rec_name = rec_name;
	}
	
	@Override
	public String toString() {
		return "Recipe [id=" + id + ", rec_name=" + rec_name + "]";
	}
}
