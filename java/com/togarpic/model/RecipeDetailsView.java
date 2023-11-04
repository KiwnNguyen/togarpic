package com.togarpic.model;



public class RecipeDetailsView {
	private int id;
	private int recipe_id;
	private String recipe_name;
	private int product_id;
	private String product_name;
	private String quantity;

	public RecipeDetailsView(int id, int recipe_id, String recipe_name, int product_id, String product_name, String quantity) {
		super();
		this.id = id;
		this.recipe_id = recipe_id;
		this.product_id = product_id;
		this.recipe_name = recipe_name;
		this.product_name = product_name;
		this.quantity = quantity;
	}

	
	public RecipeDetailsView() {
		super();
	}
	
	public int getId() {
		return id;
	}

	public int getRecipe_id() {
		return recipe_id;
	}

	public void setRecipe_id(int recipe_id) {
		this.recipe_id = recipe_id;
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
	
	public int getProduct_id() {
		return product_id;
	}


	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}


	@Override
	public String toString() {
		return "recipe_name";
	}
	
	
	
	
	
}
