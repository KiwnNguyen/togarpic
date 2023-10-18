package com.togarpic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.togarpic.repository.*;

import com.togarpic.model.*;
import com.togarpic.model.recipedetails.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@RequestMapping(value = "/process", method = RequestMethod.POST)
	  public String processForm(@RequestParam("searchInput") String selectedValue) {
	 
	    System.out.println(selectedValue);
	  
	    return "admin/recipe/list";
	  }
	
	@Autowired
	private RecipeRepository reciRepo;

	@Autowired
	private CategoryRepository cateRepo;

	@Autowired
	private RecipeDetailsRepository rdetRepo;

	@Autowired
	private ProductRepository prodRepo;
	/*----*/

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showIndex() {

		return "admin/dashboard";
	}

	@RequestMapping(value = "/alltable", method = RequestMethod.GET)
	public String showAllTable(Model model) {
		Iterable<Recipe> listreci = reciRepo.findAll();
		model.addAttribute("listreci", listreci);

		Iterable<Category> listcate = cateRepo.findAll();
		model.addAttribute("listcate", listcate);

		Iterable<RecipeDetails> listrdet = rdetRepo.findAll();
		model.addAttribute("listrdet", listrdet);

		Iterable<RecipeDetailsView> listredet = rdetRepo.findAllname();
		model.addAttribute("listrdetname", listredet);

		return "admin/table";
	}

	// VIEW ACTION

	// Category
	@RequestMapping(value = "/listcategory", method = RequestMethod.GET)
	public String showCategoryList(Model model) {
		Iterable<Category> listcate = cateRepo.findAll();
		model.addAttribute("listcate", listcate);
		return "admin/category/list";
	}

	// Recipe
	@RequestMapping(value = "/listrecipe", method = RequestMethod.GET)
	public String showAllRecipe(Model model) {
		Iterable<Recipe> listreci = reciRepo.findAll();
		model.addAttribute("listreci", listreci);
		return "admin/recipe/list";
	}

	@RequestMapping(value = "/viewmore/{id}", method = RequestMethod.GET)
	public String viewMoreRecipe(@PathVariable int id, Model model) {
		int parseId;
		parseId = Integer.valueOf(id);
		Iterable<RecipeDetailsView> listprodreci = rdetRepo.findByIdname(parseId);
		model.addAttribute("listprodreci", listprodreci);
		return "admin/recipe/details";
	}

	// Recipe Details

	// DELETE ACTION

	// Category
	@RequestMapping(value = "/delCategory/{id}", method = RequestMethod.GET)
	public String deleteCategory(Model model, @PathVariable Integer id) {

		if (id != null) {
			int parseId;
			parseId = Integer.valueOf(id);
			cateRepo.deleteById(parseId);
		}

		return "redirect:/admin/listcategory";
	}

	// Recipe
	@RequestMapping(value = "/delRecipe/{id}", method = RequestMethod.GET)
	public String deleteRecipe(Model model, @PathVariable Integer id) {

		if (id != null) {
			int parseId;
			parseId = Integer.valueOf(id);
			reciRepo.deleteById(parseId);
		}

		return "redirect:/admin/listrecipe";
	}
	// INSERT ACTION

	// Category
	@RequestMapping(value = "/insCategory", method = RequestMethod.GET)
	public String showInsertCategory() {

		return "admin/category/insert";
	}

	@RequestMapping(value = "/insCategory", method = RequestMethod.POST)
	public String insertCategory(Model model, Category category, @RequestParam("title") String title) {
		try {
			category.setCat_name(title);

			cateRepo.insert(category);
			Iterable<Category> cat = cateRepo.findAll();
			model.addAttribute("listcate", cat);
			return "redirect:/admin/listcategory";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error value insert!!");
		}
	}

	// RECIPE
	@RequestMapping(value = "/insRecipe", method = RequestMethod.GET)
	public String showInsertRecipe() {

		return "admin/recipe/insert";
	}

	@RequestMapping(value = "/insRecipe", method = RequestMethod.POST)
	public String insertRecipe(Model model, Recipe recipe, @RequestParam("title") String title) {
		try {
			recipe.setRec_name(title);

			reciRepo.insert(recipe);
			Iterable<Recipe> reci = reciRepo.findAll();
			model.addAttribute("listreci", reci);
			return "redirect:/admin/listrecipe";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error value insert!!");
		}
	}

	// RECIPE DETAILS
	@RequestMapping(value = "/insProRecipe", method = RequestMethod.GET)
	public String showInsertRecipeDetails(Model model) {

		Iterable<Product> prod = prodRepo.findAll();
		model.addAttribute("listprod", prod);
		
		return "admin/recipe/adddetails";
	}

	@RequestMapping(value = "/insProRecipe/{idr}", method = RequestMethod.POST)
	public String insertRecipeDetails(Model model, @PathVariable(name = "idr") int idr, @RequestParam("quantity") String quantity, RecipeDetails rdt) {
		rdt.setRecipe_id(idr);
		rdt.setQuantity(quantity);
//		rdt.setProduct_id(idr);
		
		return "admin/recipe/adddetails";
	}

	// UPDATE ACTION

	// Category
	@RequestMapping(value = "/updCategory/{id}", method = RequestMethod.GET)
	public String showUpdateCategory(Model model, Category category, @PathVariable(name = "id") int id) {
		try {

			Category cat = cateRepo.findById(id);
			model.addAttribute("cate", cat);

			return "admin/category/update";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error value insert!!");
		}
	}

	@RequestMapping(value = "/updCategory/{id}", method = RequestMethod.POST)
	public String updateCategory(Model model, Category category, @PathVariable(name = "id") int id,
			@RequestParam String title) {
		try {

			category.setCat_name(title);
			category.setId(id);
			cateRepo.update(category);

			return "redirect:/admin/listcategory";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error value insert!!");
		}
	}

	// Recipe
	@RequestMapping(value = "/updRecipe/{id}", method = RequestMethod.GET)
	public String showUpdateRecipe(Model model, Recipe recipe, @PathVariable(name = "id") int id) {

		Recipe reci = reciRepo.findById(id);
		model.addAttribute("reci", reci);

		return "admin/recipe/update";
	}

	@RequestMapping(value = "/updRecipe/{id}", method = RequestMethod.POST)
	public String updateRecipe(Model model, Recipe recipe, @PathVariable(name = "id") int id,
			@RequestParam String title) {
		try {

			recipe.setRec_name(title);
			recipe.setId(id);
			reciRepo.update(recipe);

			return "redirect:/admin/listrecipe";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error value insert!!");
		}
	}

}
