package com.togarpic.model.recipedetails;

import java.util.HashMap;

import com.togarpic.model.RecipeDetailsProductView;

public class RecipeDetailsView {
	private int stt;
	private int id;
	private String recipe_name;
	private HashMap<String, RecipeDetailsProductView> productlist;
	public RecipeDetailsView(int stt, int id, String recipe_name, HashMap<String, RecipeDetailsProductView> productlist) {
		super();
		this.stt = stt;
		this.id = id;
		this.recipe_name = recipe_name;
		this.productlist = productlist;
	}
	public RecipeDetailsView() {
		super();
	}
	public int getStt() {
		return stt;
	}
	public void setStt(int stt) {
		this.stt = stt;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRecipe_name() {
		return recipe_name;
	}
	public void setRecipe_name(String recipe_name) {
		this.recipe_name = recipe_name;
	}
	public HashMap<String, RecipeDetailsProductView> getProductlist() {
		return productlist;
	}
	public void setProductlist(HashMap<String, RecipeDetailsProductView> productlist) {
		this.productlist = productlist;
	}
	@Override
	public String toString() {
		return "RecipeDetailsView [stt=" + stt + ", id=" + id + ", recipe_name=" + recipe_name + ", productlist="
				+ productlist + "]";
	}

	
	
	
}
