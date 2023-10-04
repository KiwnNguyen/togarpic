package com.togarpic.model;

public class RecipeDetails {
	private int id;
	private int recipe_id;
	private int product_id;
	private int quantity;
	public RecipeDetails(int id, int recipe_id, int product_id, int quantity) {
		super();
		this.id = id;
		this.recipe_id = recipe_id;
		this.product_id = product_id;
		this.quantity = quantity;
	}
	public RecipeDetails() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "RecipeDetails [id=" + id + ", recipe_id=" + recipe_id + ", product_id=" + product_id + ", quantity="
				+ quantity + "]";
	}
	
	
}
