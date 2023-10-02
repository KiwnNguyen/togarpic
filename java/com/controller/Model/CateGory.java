package com.controller.Model;

public class CateGory extends  MyUploadForm{

	private int id;
	private String name;
	private String imageCate;
	private float price;
	
	
	
	
	public CateGory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public CateGory(int id, String name, String imageCate, float price) {
		super();
		this.id = id;
		this.name = name;
		this.imageCate = imageCate;
		this.price = price;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageCate() {
		return imageCate;
	}
	public void setImageCate(String imageCate) {
		this.imageCate = imageCate;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}



	@Override
	public String toString() {
		return id + "====" + name + "====" + imageCate + "====" + price;
	}
	
	
	
	
	
	
}
