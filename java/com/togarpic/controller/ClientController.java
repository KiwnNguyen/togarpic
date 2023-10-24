package com.togarpic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.togarpic.model.Product;
import com.togarpic.model.Review;
import com.togarpic.repository.ReviewRepository;

@Controller
@RequestMapping("/home")
public class ClientController {
	@Autowired
	private ReviewRepository rev1;
	
	@GetMapping("/")
	public String showIndex(Model model) {
		Iterable<Product> pro = rev1.findAllPro();
		model.addAttribute("listpro", pro);
	
		return "client/home";
	}
	
	@RequestMapping("/cart")
	public String showCart() {
		return "client/cart";
	}
	@RequestMapping("/shoppingcart")
	public String shoppingCart() {
		return "client/shopingcart";
	}
	@GetMapping("/checkout")
	public String Checkout() {
		return "client/checkout";
	}
	@PostMapping("/submitcheckout")
	public String Checkoutsubmit(@RequestParam("firstname")String firstname,@RequestParam("lastname")String lastname,@RequestParam("phone")String phone,@RequestParam("email")String email) {
		String tmp_firts = firstname;
		String tmp_last = lastname;
		String tmp_phone = phone;
		String tmp_email = email;
		
		return "client/checkout";
	}
	@GetMapping("/login")
	public String Login() {
		return "login";

	}
	@PostMapping("/submitlogin")
	public String Submitlogin(@RequestParam("email")String email,@RequestParam("password")String password) {
		String tmp_email = email;
		String tmp_pass = password;
		Review acc = new Review();
		
		
		List<Review> temp = rev1.findAllUser();
		String t = temp.toString();
		for(Review usr: temp) {
			if(email.equals(usr.getUsr_email()) && password.equals(usr.getUsr_password())){
				System.out.println("Xam nhap vao thanh cong");
				acc.setUsr_email(tmp_email);
				acc.setUsr_password(tmp_pass);

				if(usr.getUsr_role() == 1 ) {
					return"redirect:/home/";
				}else if(usr.getUsr_role()== 2) {
					return"redirect:/admin/dashboard";
				}

			}
			
		}
		return"redirect:/home/login";
	}
	
	
	
	
	
}
