package com.togarpic.controller;

import java.util.ArrayList;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.togarpic.model.*;

import com.togarpic.repository.*;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ClientController {
		
	@Autowired
	private CategoryRepository cateRepo;
	
	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired
	private CartItemRepository citeRepo;

	@Autowired
	private ReviewRepository rev1;
	
	@Autowired
	private JavaMailSender mailSender;


	@GetMapping("/")
	public String showIndex(HttpServletRequest request,Model model,HttpServletResponse response) {
		String email = (String) request.getSession().getAttribute("email");
		String roles = (String) request.getSession().getAttribute("roles");
		model.addAttribute("account",email);
		if(email!=null) {
			  model.addAttribute("account", email);
			model.addAttribute("logout","logout");	
			
		}else {
			
			model.addAttribute("login","login");
			model.addAttribute("account", null);
		}
		
		if(roles != null &&roles.equals("ADMIN")) {

			 model.addAttribute("showadmin", "Admin");
		}else {
			 model.addAttribute("showadmin", null);
		}
	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Expires", "0");
	    

		Iterable<Category> lstCate = cateRepo.findAll();
		model.addAttribute("lstcate",lstCate);

		return "index";
	}
	
	/* ADD TO CART */
	
	@RequestMapping(value = "/addtocart/{idpro}", method = RequestMethod.GET)
	public String addToCart(@PathVariable int idpro, CartVieww cart, HttpSession session, HttpServletRequest request) {
		ArrayList<CartVieww> lstCart = new ArrayList<>();
		cart.setPro_id(idpro);
		cart.setQuantity(1);
	
		int idcart = (int) session.getAttribute("idcart");
		cart.setCart_id(idcart);
		@SuppressWarnings("unchecked")
		ArrayList<CartVieww> cart_list = (ArrayList<CartVieww>) session.getAttribute("cartlist");
		
		if(cart_list == null) {
			lstCart.add(cart);
			session.setAttribute("cartlist", lstCart);
			return "redirect:/cart/"+idcart;
		}else {
			lstCart = cart_list;
			int count = 0;
			boolean exist = false;
			for(CartVieww i : cart_list) {
				if(i.getCart_id() == 1) {
					count = 1;
					count += i.getQuantity();
					i.setQuantity(count);
					exist = true;
					return "redirect:/cart/"+idcart;
				}
			}
			if (!exist) {
				lstCart.add(cart);
				return "redirect:/cart/"+idcart;
			}	
		}
		return null;
	}
	
	/* ADD TO CART */
	
	/* Cart View */
	
	@RequestMapping(value = "/cart/{idcart}", method = RequestMethod.GET)
	public String viewCart(HttpSession session, HttpServletRequest request, @PathVariable int idcart) {
		session = request.getSession();
		
		@SuppressWarnings("unchecked")
		ArrayList<CartVieww> cart_list = (ArrayList<CartVieww>) session.getAttribute("cartlist"); 
		Iterable<CartVieww> cartProduct = null;
		if(cart_list != null) {
			cartProduct = citeRepo.findProdOfCartByCartId(idcart);
			session.setAttribute("cartProduct", cartProduct);
			session.setAttribute("cart_list", cart_list);
		}
		return "client/cart";
	}
	
	/* Cart View */
	
	
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String s() {
		
		
		return "client/product_category";
	}
	
	@RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
	public String showProductByCategory(Model model, @PathVariable int id, HttpServletRequest request) {
		Iterable<ProductView> lstpro = prodRepo.findByIdName(id);
		model.addAttribute("lstpro",lstpro);
		Iterable<Category> lstCate = cateRepo.findAll();
		model.addAttribute("lstcate",lstCate);
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("sessionUser");
		model.addAttribute("sessionUser", u);
		return "client/product_category";
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
					
					return"redirect:/home/";
				}else if(usr.getUsr_role().equals("ADMIN")) {
					System.out.println("Xam nhap admin");
					request.getSession().setAttribute("roles", "ADMIN");
					model.addAttribute("logout","logout");
					request.getSession().setAttribute("email", tmp_email);
					model.addAttribute("account",tmp_email);
					 model.addAttribute("showadmin", "Admin");
					return"redirect:/admin/dashboard";
				}

			}
			
		}
		return"redirect:/home/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		 request.getSession().invalidate();
		  request.getSession().removeAttribute("email");
		    request.getSession().removeAttribute("roles");
		return"redirect:/home/login?logout";
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
								@RequestParam("cfpass")String cfpass,Model model,Review review,HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
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
		
		String siteURL =Utility.getSiteURL(request);
		rev1.sendVerificationEmail(review, siteURL);
	
		 model.addAttribute("successMessage", "Đăng ký thành công !");
		return "redirect:/home/login";

	}

	
}
