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
	@RequestMapping("/menu")
	public String Menu() {
		return"decorators/menu";
		
	}
	@RequestMapping("/about")
	public String About() {
		return"decorators/about";
		
	}
	@RequestMapping("/contact")
	public String Contact() {
		return"decorators/contact";
		
	}
	@RequestMapping("/careers")
	public String careers() {
		return"decorators/careers";
		
	}
	@RequestMapping("/help")
	public String help() {
		return"decorators/help";
		
	}
	@RequestMapping("/offers")
	public String offers() {
		return"decorators/offers";
		
	}
	@RequestMapping("/faq")
	public String faq() {
		return"decorators/faq";
		
	}
	@RequestMapping("/product")
	public String product() {
		return"decorators/product";
		
	}
}
