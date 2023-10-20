package com.togarpic.model;



public class RecipeDetailsView {
	private int id;
	private String recipe_name;
	private String product_name;
	private String quantity;
	public RecipeDetailsView(int id, String recipe_name, String product_name, String quantity) {
		super();
		this.id = id;
		this.recipe_name = recipe_name;
		this.product_name = product_name;
		this.quantity = quantity;
	}
	public RecipeDetailsView() {
		super();
	}
	@Override
	public String toString() {
		return "recipe_name";
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
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	
	
	
	
}
