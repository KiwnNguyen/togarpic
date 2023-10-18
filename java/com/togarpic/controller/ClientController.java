package com.togarpic.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@RequestMapping("/")
public class ClientController implements WebMvcConfigurer {
	
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
	@RequestMapping("/shopingcart")
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
	@RequestMapping("/blog")
	public String blog() {
		return"client/blog";
		
	}
	@RequestMapping("/contact")
	public String contact() {
		return"client/contact";
		
	}
	
}
