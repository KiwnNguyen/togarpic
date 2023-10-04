package com.togarpic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ClientController {
	
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	
	@RequestMapping("/cart")
	public String showCart() {
		return "client/cart";
	}
	
	@RequestMapping("/admin")
	public String showAdmin() {
		return "admin/index";
	}
}
