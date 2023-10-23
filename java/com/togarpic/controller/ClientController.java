package com.togarpic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.togarpic.model.Category;
import com.togarpic.model.ProductView;
import com.togarpic.repository.CategoryRepository;
import com.togarpic.repository.ProductRepository;

@Controller
public class ClientController {
	
	@Autowired
	private CategoryRepository cateRepo;
	
	@Autowired
	private ProductRepository prodRepo;
	
	@RequestMapping("/")
	public String showIndex(Model model) {
		Iterable<Category> lstCate = cateRepo.findAll();
		model.addAttribute("lstcate",lstCate);
		
		return "index";
	}
	
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String s() {
		
		
		return "client/product_category";
	}
	
	@RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
	public String showProductByCategory(Model model, @PathVariable int id) {
		Iterable<ProductView> lstpro = prodRepo.findByIdName(id);
		model.addAttribute("lstpro",lstpro);
		
		return "client/product_category";
	}
	
	
	@RequestMapping("/cart")
	public String showCart() {
		return "client/cart";
	}
	
	
}
