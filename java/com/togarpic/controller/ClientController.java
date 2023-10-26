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

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/home")
public class ClientController {
	@Autowired
	private ReviewRepository rev1;
	
	@GetMapping("/")
	public String showIndex(HttpServletRequest request,Model model) {
		String email = (String) request.getSession().getAttribute("email");
		String roles = (String) request.getSession().getAttribute("roles");
		model.addAttribute("account",email);
		if(email!=null) {
			model.addAttribute("logout","logout");	
		}else if(email == null) {
			model.addAttribute("login","login");
		}
		
		if(roles != null &&roles.equals("ADMIN")) {
			 model.addAttribute("showadmin", "Admin");
		}
		
	
		return "client/home";
	}
	@RequestMapping("/shopdetails")
	public String showshopdetails(HttpServletRequest request,Model model) {
		String email = (String) request.getSession().getAttribute("email");
		model.addAttribute("account",email);
		if(email!=null) {
			model.addAttribute("logout","logout");	
		}
		else if(email == null) {
			model.addAttribute("login","login");
		}
		return "client/shopdetails";
	}
	@RequestMapping("/shopgrid")
	public String showshopgrid(HttpServletRequest request,Model model) {
		String email = (String) request.getSession().getAttribute("email");
		model.addAttribute("account",email);
		if(email!=null) {
			model.addAttribute("logout","logout");	
		}
		model.addAttribute("login","login");
		return "client/shopgrid";
	}
	@RequestMapping("/blogdetails")
	public String showblogdetails(HttpServletRequest request,Model model) {
		String email = (String) request.getSession().getAttribute("email");
		model.addAttribute("account",email);
		if(email!=null) {
			model.addAttribute("logout","logout");	
		}
		else if(email == null) {
			model.addAttribute("login","login");
		}
		return "client/blogdetails";
	}
	
	@RequestMapping("/cart")
	public String showCart(HttpServletRequest request,Model model){
		String email = (String) request.getSession().getAttribute("email");
		model.addAttribute("account",email);
		if(email!=null) {
			model.addAttribute("logout","logout");	
		}
		else if(email == null) {
			model.addAttribute("login","login");
		}
		return "client/cart";
	}
	@RequestMapping("/blog")
	public String showBlog(HttpServletRequest request,Model model){
		String email = (String) request.getSession().getAttribute("email");
		model.addAttribute("account",email);
		if(email!=null) {
			model.addAttribute("logout","logout");	
		}
		else if(email == null) {
			model.addAttribute("login","login");
		}
		return "client/blog";
	}
	@RequestMapping("/contact")
	public String showcontact(HttpServletRequest request,Model model){
		String email = (String) request.getSession().getAttribute("email");
		model.addAttribute("account",email);
		if(email!=null) {
			model.addAttribute("logout","logout");	
		}
		model.addAttribute("login","login");
		return "client/contact";
	}
	@RequestMapping("/shoppingcart")
	public String shoppingCart(HttpServletRequest request,Model model) {
		//USER
		 String roles = (String) request.getSession().getAttribute("roles");
         if (roles != null) {
        	 String email = (String) request.getSession().getAttribute("email");
     		model.addAttribute("account",email);
     		if(email!=null) {
    			model.addAttribute("logout","logout");	
    		}
     		else if(email == null) {
    			model.addAttribute("login","login");
    		}
             return "client/shopingcart";
         }
		return "redirect:/home/login";
	}
	@GetMapping("/checkout")
	public String Checkout(HttpServletRequest request,Model model) {
		String email = (String) request.getSession().getAttribute("email");
		model.addAttribute("account",email);
		if(email!=null) {
			model.addAttribute("logout","logout");	
		}
		else if(email == null) {
			model.addAttribute("login","login");
		}
		return "client/checkout";
	}
	@PostMapping("/submitcheckout")
	public String Checkoutsubmit(@RequestParam("firstname")String firstname,@RequestParam("lastname")String lastname,@RequestParam("phone")String phone,@RequestParam("email")String email,HttpServletRequest request,Model model) {
		String tmp_firts = firstname;
		String tmp_last = lastname;
		String tmp_phone = phone;
		String tmp_email = email;
		
		String email1 = (String) request.getSession().getAttribute("email");
		model.addAttribute("account",email1);
		if(email!=null) {
			model.addAttribute("logout","logout");	
		}
		else if(email == null) {
			model.addAttribute("login","login");
		}
		return "client/checkout";
	}
	@GetMapping("/login")
	public String Login() {
		return "login";

	}
	@PostMapping("/submitlogin")
	public String Submitlogin(@RequestParam("email")String email,@RequestParam("password")String password,HttpServletRequest request,Model model) {
		String tmp_email = email;
		String tmp_pass = password;
	
		List<Review> temp = rev1.findAllUser();
		String t = temp.toString();
		for(Review usr: temp) {
			if(email.equals(usr.getUsr_email()) && password.equals(usr.getUsr_password())){
				System.out.println("Xam nhap vao thanh cong");
//				request.getSession().setAttribute("user", usr);
				if(usr.getUsr_role().equals("USER") ) {
					System.out.println("Xam nhap user");
					request.getSession().setAttribute("roles", "USER");
					
					request.getSession().setAttribute("email", tmp_email);
					model.addAttribute("account",tmp_email);
					model.addAttribute("logout","logout");
					
					return"client/home";
				}else if(usr.getUsr_role().equals("ADMIN")) {
					System.out.println("Xam nhap admin");
					request.getSession().setAttribute("roles", "ADMIN");
					model.addAttribute("logout","logout");
					request.getSession().setAttribute("email", tmp_email);
					model.addAttribute("account",tmp_email);
					 model.addAttribute("showadmin", "Admin");
					return"client/home";
				}

			}
			
		}
		return"redirect:/home/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		 request.getSession().invalidate();
		return"redirect:/home/login?";
	}
	
	@GetMapping("/register")
	public String Register() {
		return "register";

	}
	@PostMapping("/registersubmit")
	public String RegisterSubmit(@RequestParam("firstname")String firstname,
								@RequestParam("lastname")String lastname,
								@RequestParam("phone") String phone,
								@RequestParam("email")String email,
								@RequestParam("password")String password,
								@RequestParam("cfpass")String cfpass,Model model,Review review) {
		String tmp_firstname = firstname;
		String tmp_lastname = lastname;
		String tmp_phone = phone;
		String tmp_email = email;
		String tmp_pass = password;
		String tmp_cfpass = cfpass;
		
		if (!tmp_pass.equals(tmp_cfpass)) {
		       model.addAttribute("errorMessage", "Mật khẩu không khớp !");
		       return "register";
		}
		review.setUsr_firstName(tmp_firstname);
		review.setUsr_lastName(tmp_lastname);
		review.setUsr_telephone(tmp_phone);
		review.setUsr_email(tmp_email);
		review.setUsr_password(tmp_pass);
		
		String roles = "USER";
		review.setUsr_role(roles);
		
		rev1.insertUser(review);
		
		 model.addAttribute("successMessage", "Đăng ký thành công !");
		return "register";

	}
	
	
}
