package com.controller.Shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@RequestMapping("/www.vega.com")
public class HomeController implements WebMvcConfigurer {
	
	//------------ error 404 -----------
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error404").setViewName("error404");
    }
	//------------ error 404 -----------
	
	
	@RequestMapping("/home")
	public String Home() {
		return"decorators/home";
		
	}
	@RequestMapping("/shopgrid")
	public String Shopgrid() {
		return"decorators/shopgrid";
		
	}
	@RequestMapping("/shopdetails")
	public String shopdetails() {
		return"decorators/shopdetails";
		
	}
	@RequestMapping("/shopingcart")
	public String shopingcart() {
		return"decorators/shopingcart";
		
	}
	@RequestMapping("/checkout")
	public String checkout() {
		return"decorators/checkout";
		
	}
	@RequestMapping("/blogdetails")
	public String blogdetails() {
		return"decorators/blogdetails";
		
	}
	@RequestMapping("/blog")
	public String blog() {
		return"decorators/blog";
		
	}
	@RequestMapping("/contact")
	public String contact() {
		return"decorators/contact";
		
	}
	
}
