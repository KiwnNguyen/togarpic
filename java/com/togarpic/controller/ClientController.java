package com.togarpic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.togarpic.model.Recipe;
import com.togarpic.repository.RecipeRepository;

@Controller
@RequestMapping("/")
public class ClientController implements WebMvcConfigurer {
	@Autowired
	private RecipeRepository rec1;
	//------------ error 404 -----------
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error404").setViewName("error404");
    }
	//------------ error 404 -----------
	
	@RequestMapping("/home")
	public String Home() {
		return"client/home";
		
	}
	@RequestMapping("/shopgrid")
	public String Shopgrid() {
		return"client/shopgrid";
		
	}
	@RequestMapping("/shopdetails")
	public String shopdetails() {
		return"client/shopdetails";
		
	}
	@RequestMapping("/shoppingcart")
	public String shopingcart() {
		return"client/shoppingcart";
		
	}
	@RequestMapping("/checkout")
	public String checkout() {
		return"client/checkout";
		
	}
	@RequestMapping("/blogdetails")
	public String blogdetails() {
		return"client/blogdetails";
		
	}
	@RequestMapping(value = "/recipe", method = RequestMethod.GET)
	public String showRecipeList(Model model) {	  
		Iterable<Recipe> rec = rec1.findAll();
		model.addAttribute("listRecipe", rec);
		return "client/recipe";
	}
	@RequestMapping(value = "/recipe_details/{idrec}", method = RequestMethod.GET)
	public String rec_details(Model model, @PathVariable int idrec) {
		
		
		return"client/recipe_details/rec_details";
		
	}
	@RequestMapping("/contact")
	public String contact() {
		return"client/contact";
		
	}
	
}