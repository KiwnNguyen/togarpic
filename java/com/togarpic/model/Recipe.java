package com.togarpic.model;

public class Recipe {
	private int id;
	private String rec_name;
	private String rec_image;
	public Recipe(int id, String rec_name, String rec_image) {
		super();
		this.id = id;
		this.rec_name = rec_name;
		this.rec_image = rec_image;
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
	

	public String getRec_image() {
		return rec_image;
	}

	public void setRec_image(String rec_image) {
		this.rec_image = rec_image;
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", rec_name=" + rec_name + ", rec_image=" + rec_image + ", getId()=" + getId()
				+ ", getRec_name()=" + getRec_name() + ", getRec_image()=" + getRec_image() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
}
