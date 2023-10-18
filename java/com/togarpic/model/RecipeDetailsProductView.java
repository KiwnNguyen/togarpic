package com.togarpic.model;

import java.util.List;

public class RecipeDetailsProductView {
	private int idrec;
	private List<String> product;
	public RecipeDetailsProductView(int idrec, List<String> product) {
		super();
		this.idrec = idrec;
		this.product = product;
	}
	public RecipeDetailsProductView() {
		super();
	}
	public int getIdrec() {
		return idrec;
	}
	public void setIdrec(int idrec) {
		this.idrec = idrec;
	}
	public List<String> getProduct() {
		return product;
	}
	public void setProduct(List<String> product) {
		this.product = product;
	}
	@Override
	public String toString() {
		return "RecipeDetailsProductView [idrec=" + idrec + ", product=" + product + "]";
	}
	
	
	
	
}
