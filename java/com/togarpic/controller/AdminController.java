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

	@Autowired
	private RecipeRepository reciRepo;

	@Autowired
	private CategoryRepository cateRepo;

	@Autowired
	private RecipeDetailsRepository rdetRepo;

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

		return "admin/table";
	}

	@RequestMapping(value = "/listcategory", method = RequestMethod.GET)
	public String showCategoryList(Model model) {
		Iterable<Category> listcate = cateRepo.findAll();
		model.addAttribute("listcate", listcate);
		return "admin/category/list";
	}

	@RequestMapping(value = "/listrecipe", method = RequestMethod.GET)
	public String showAllRecipe(Model model) {
		Iterable<Recipe> listreci = reciRepo.findAll();
		model.addAttribute("listreci", listreci);
		return "admin/recipe/list";
	}

	// VIEW ACTION

	@RequestMapping(value = "/viewmore/{id}")
	public String viewMoreRecipe(@PathVariable int id) {
		return null;
	}

	// DELETE ACTION

	@RequestMapping(value = "/delCategory/{id}", method = RequestMethod.GET)
	public String deleteCategory(Model model, @PathVariable Integer id) {

		if (id != null) {
			int parseId;
			parseId = Integer.valueOf(id);
			cateRepo.deleteById(parseId);
		}

		return "redirect:/admin/listcategory";
	}

	// INSERT ACTION

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

	// UPDATE ACTION

	@RequestMapping(value = "/updCategory/{id}", method = RequestMethod.GET)
	public String showUpdateCategory(Model model, Category category, @PathVariable(name="id") int id) {
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
	public String updateCategory(Model model, Category category, @PathVariable(name="id") int id, @RequestParam String title) {
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
}
