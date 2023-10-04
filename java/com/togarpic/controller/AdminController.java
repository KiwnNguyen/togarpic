package com.togarpic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String showDashboard() {
			
		
		return "admin/dashboard";
	}
	
	@RequestMapping(value = "/listcategory", method = RequestMethod.GET)
	public String showCategoryList() {
			
		
		return "admin/category/list";
	}
}
