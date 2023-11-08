package com.togarpic.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Spliterator;

import org.mindrot.jbcrypt.BCrypt;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	private RecipeRepository rec1;
	
	@Autowired
	private CartItemRepository citeRepo;

	@Autowired
	private ReviewRepository rev1;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private OrderRepository ord1;
	
	@Autowired
	private OrderDetailsRepository ord_dt1;
	
	@Autowired
	private UserRepository usr1;


	@GetMapping("/")
	public String showIndex(HttpServletRequest request,Model model,HttpServletResponse response) {
		String email = (String) request.getSession().getAttribute("email");
		String roles = (String) request.getSession().getAttribute("roles");

		HttpSession session = request.getSession();
		ArrayList<CartVieww> cartlist1 = (ArrayList<CartVieww>) session.getAttribute("cartlist");

		model.addAttribute("account",email);
		Iterable<CartAddTo1> listpro = ord1.findAllPro1();
		model.addAttribute("listPro",listpro );
		if(email!=null) {
			  model.addAttribute("account", email);
			model.addAttribute("logout","logout");	
			
		}else {
			
			model.addAttribute("login","login");
			model.addAttribute("account", null);
		}
		
		if(cartlist1 !=null) {
			
			model.addAttribute("cartShow",cartlist1.size());
		}

		if(roles != null &&roles.equals("ADMIN")) {

			 model.addAttribute("showadmin", "Admin");
		}else {
			 model.addAttribute("showadmin", null);
		}
		
		
//	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//	    response.setHeader("Pragma", "no-cache");
//	    response.setHeader("Expires", "0");
	    

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
		String email = (String) request.getSession().getAttribute("email");
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
	

	@RequestMapping("/blog")
	public String showBlog(HttpServletRequest request,Model model){
		String email = (String) request.getSession().getAttribute("email");
		model.addAttribute("account",email);
		Integer showcart = (Integer) request.getSession().getAttribute("cartShow");
		 if(showcart !=null) {
				Integer showcart1 = (Integer) request.getSession().getAttribute("cartShow");
				model.addAttribute("cartShow",showcart1);
			}
		if(email!=null) {
			model.addAttribute("logout","logout");	
		}
		else if(email == null) {
			model.addAttribute("login","login");
		}
		return "client/blog";
	}
	@RequestMapping("/recipe_details/beefsteak")
	public String beefsteak() {
		return"client//recipe_details/beefsteak";
		
	}
	@RequestMapping("/recipe_details/banhchung")
	public String banhchung() {
		return"client//recipe_details/banhchung";
		
	}
	@RequestMapping("/recipe_details/donut")
	public String donut() {
		return"client//recipe_details/donut";
		
	}
	@RequestMapping("/recipe_details/curryrice")
	public String curryrice() {
		return"client//recipe_details/curryrice";
		
	}
	@RequestMapping("/recipe_details/ramen")
	public String ramen() {
		return"client//recipe_details/ramen";
		
	}
	@RequestMapping("/recipe_details/tofu")
	public String tofu() {
		return"client//recipe_details/tofu";
		
	}
	@RequestMapping("/recipe_details/bokho")
	public String bokho() {
		return"client//recipe_details/bokho";
		
	}


	@RequestMapping(value = "/recipe", method = RequestMethod.GET)
	public String showRecipeList(Model model) {	  
		Iterable<Recipe> rec = rec1.findAll();
		model.addAttribute("listRecipe", rec);
		return "client/recipe";
	}
	@GetMapping(value = "/recipe_details/{rec_id}")
	 public String showRecipeDetails(@PathVariable("rec_id") Long recId, Model model) {
        // Xác định đường dẫn trả về dựa trên recId
        String redirectPath;
        if (recId == 1) {
            redirectPath = "redirect:/recipe_details/pizza";
        } else if (recId == 2) {
            redirectPath = "redirect:/recipe_details/beefsteak";
        }else if (recId == 3) {
            redirectPath = "redirect:/recipe_details/banhchung";
        }else if (recId == 4) {
            redirectPath = "redirect:/recipe_details/donut";
        }else if (recId == 5) {
            redirectPath = "redirect:/recipe_details/curryrice";
        }else if (recId == 6) {
            redirectPath = "redirect:/recipe_details/ramen";
        }else if (recId == 9) {
            redirectPath = "redirect:/recipe_details/tofu";
        }else if (recId == 10) {
            redirectPath = "redirect:/recipe_details/bokho";
        } else {
            // Nếu recId không phù hợp, chuyển hướng về trang chủ hoặc hiển thị thông báo lỗi
            return "redirect:/recipe";
        }

        // Trả về đường dẫn chuyển hướng
        return redirectPath;
    }
	@RequestMapping("/recipe_details/pizza")
	public String pizza() {
		return"client//recipe_details/pizza";
	}

	@RequestMapping("/contact")
	public String showcontact(HttpServletRequest request,Model model){
		String email = (String) request.getSession().getAttribute("email");
		HttpSession session = request.getSession();
		ArrayList<CartVieww> cartlist1 = (ArrayList<CartVieww>) session.getAttribute("cartlist");
		if(email!=null) {
			  model.addAttribute("account", email);
			model.addAttribute("logout","logout");	
			
		}else {
			
			model.addAttribute("login","login");
			model.addAttribute("account", null);
		}
		if(cartlist1 !=null) {
			 model.addAttribute("cartShow",cartlist1.size());
		}
		model.addAttribute("account",email);
		if(email!=null) {
			model.addAttribute("logout","logout");	
		}
		model.addAttribute("login","login");
		return "client/contact";
	}
	@RequestMapping("/cartAdd/{idpro}")
	public String shoppingCart(HttpServletResponse response,HttpServletRequest request,Model model,@PathVariable String idpro) throws IOException {
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
     		//đã lấy được id của product đã chọn
     		
     		int tmp = Integer.parseInt(idpro);
     		ArrayList<CartVieww> cartList = new ArrayList<>();
     		CartVieww cartview = new CartVieww(); 
     		cartview.setPro_id(tmp);
     		cartview.setQuantity(1);
//     		Integer carid = (Integer) request.getSession().getAttribute("cartid");
//     		cartview.setCart_id(carid);
     		System.out.println(cartview.getPro_id());
     		System.out.println(cartview.getQuantity());
     		HttpSession session = request.getSession();
     	
     		ArrayList<CartVieww> cart_list 
     		= (ArrayList<CartVieww>) session.getAttribute("cartlist");
     		
     		
     		if(cart_list ==null){
     			 cart_list = new ArrayList<>();
     			cart_list.add(cartview);
     			session.setAttribute("cartShow", cart_list.size());
     			session.setAttribute("cartlist", cart_list);   		
//     			model.addAttribute("cartShow",cart_list.size());
     			return "redirect:/cart1";
     		}else {
     			cartList = cart_list;
     			int count1 = 0;
     			boolean exist = false;
     			for(CartVieww c:cart_list) {
     				if(c.getPro_id()==tmp){
     					count1=1;
     					count1 += c.getQuantity();
     					c.setQuantity(count1);
     					exist = true;
     					return "redirect:/cart1";
     				}
     				
     			}
     			if(!exist){
     				cartList.add(cartview);
     				return "redirect:/cart1";
     			}
     			
     		}
         }
		return "redirect:/login";
	}
	@GetMapping("/cart1")
	public String Cart1(HttpServletRequest request,Model model) {
		 String roles = (String) request.getSession().getAttribute("roles");
         if (roles != null) {
        	 String email = (String) request.getSession().getAttribute("email");
			model.addAttribute("account",email);
			if(email!=null) {
			model.addAttribute("logout","logout");	
			HttpSession session = request.getSession();
			ArrayList<CartVieww> cart_list = (ArrayList<CartVieww>) session.getAttribute("cartlist");
			
			List<CartVieww> cartProduct= null;
			if(cart_list!=null) {
				Object[] t = cart_list.toArray();
				OrderRepository productTemp = new OrderRepository();
				cartProduct = productTemp.getCartProduct(cart_list);
				Object[] cartlist = cart_list.toArray();
				Object[] cartpro = cartProduct.toArray();
				model.addAttribute("listCart", cartProduct);
//				session.setAttribute("cart_list", cart_list);
//				session.setAttribute("cartProduct", cartProduct);
				cart_list.size();
				model.addAttribute("cartShow",cart_list.size());
				int tmp123 = cart_list.size();
				request.getSession().setAttribute("cartShow", tmp123);
			}
				return"client/cart";
			}
			else if(email == null) {
			model.addAttribute("login","login");
			}
		}	
		return "redirect:/login";
		
	}
	//@PostMapping("/deleteCart")
	@RequestMapping("/deletecart/{idcart}")
	public String deleteCheckout(HttpServletRequest request,@PathVariable int idcart,HttpServletResponse response,Model model ) {
//		List<CartVieww> cartlist = (List<CartVieww>) request.getSession().getAttribute("cartlist");
		HttpSession session = request.getSession();
		ArrayList<CartVieww> cartlist = (ArrayList<CartVieww>) session.getAttribute("cartlist");
		
		Object[] bientap= cartlist.toArray();
		int size1 =  cartlist.size();
		// Vị trí phần tử cần xóa
		int idca = idcart;
		List<CartVieww> cartProduct= null;
		if (idca >= 0 && idca < cartlist.size()) {
	        cartlist.remove(idcart); // Xóa phần tử tại vị trí idcart
	        String emp = cartlist.toString();
	    	request.getSession().setAttribute("cartlist", cartlist);
	    	  
		}
		 
		OrderRepository productTemp = new OrderRepository();
		cartProduct = productTemp.getCartProduct(cartlist);
		model.addAttribute("listCart", cartProduct);
		model.addAttribute("cartShow",cartlist.size());
		return"client/cart";
	}
	@PostMapping("/checkout")
	public String Checkout(HttpServletRequest request,Model model,@RequestParam("totalTong")String tongtotal,@RequestParam("productName[]") String[] productNames,@RequestParam("productPrice[]") String[] productPrices,@RequestParam("quantity[]") String[] quantity1) {
		
	 String roles = (String) request.getSession().getAttribute("roles");
	 Integer showcart = (Integer) request.getSession().getAttribute("cartShow");
	 if(showcart !=null) {
			Integer showcart1 = (Integer) request.getSession().getAttribute("cartShow");
			model.addAttribute("cartShow",showcart1);
		}
      if (roles != null) {	
    	  //Hiện tổng total
    	  String tmp_tongtotal = tongtotal;
    	  model.addAttribute("totalAll",tmp_tongtotal);	
    	  //Show products đã mua
    	  List<String> tmp_proname = Arrays.asList(productNames);
          model.addAttribute("tmp_pronames", tmp_proname);
          //Hiện các giá product
          List<String> tmp_proprice = Arrays.asList(productPrices);
          model.addAttribute("tmp_proprices", tmp_proprice);
          //Hiện quantity
          List<String> tmp_quantity = Arrays.asList(quantity1);
          model.addAttribute("tmp_quantity", tmp_quantity);
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
      return "redirect:/login";
	}
	// Đặt isFirstClick là một trường dữ liệu của lớp
	private boolean isFirstClick = true;
	@PostMapping("/submitcheckout")
	public String Checkoutsubmit(@RequestParam("firstname")String firstname,@RequestParam("lastname")String lastname,@RequestParam("phone")String phone,@RequestParam("email")String email,@RequestParam("address")String address,HttpServletRequest request,Model model
								,@RequestParam("productName1[]") String[] productNames1,@RequestParam("productPrice[]") String[] productPrices1,@RequestParam("productQuantity[]") String[] productQuantity1,OrderCheck orderCheck) {
		List<String> tmp_proname1 = Arrays.asList(productNames1);
		List<String> tmp_proprice1 = Arrays.asList(productPrices1);
		List<String> tmp_quantity = Arrays.asList(productQuantity1);			
	    Boolean isFirstClickStored = (Boolean) request.getSession().getAttribute("isFirstClick");	    
		model.addAttribute("account",email);
		  if (isFirstClickStored != null && isFirstClickStored) {
		        isFirstClick = false;
		    }
		 boolean dung= isFirstClick;  
		if(isFirstClick) {
			String email1 = (String) request.getSession().getAttribute("email");
			User mop = ord1.findByEmail(email1,firstname,lastname);
			long iduser = mop.getUsr_id();
			Order it = new Order();
			it.setUsr_id(iduser);
			it.setOrd_address(address);
			ord1.insert1(it);
			isFirstClick = false;
			request.getSession().setAttribute("isFirstClick", false);
		}

		if (tmp_proname1.size() == tmp_quantity.size()) {
		    for (int i = 0; i < tmp_proname1.size(); i++) {
		        String productName = tmp_proname1.get(i);
		        String quantityString = tmp_quantity.get(i);

		        // Xử lý productName bỏ những ký tự,... 
		        quantityString = quantityString.replaceAll("[^0-9]", "");
		        int quantity1 = Integer.parseInt(quantityString);

		        // Xử lý quantity và .....		       
		        productName = productName.replaceAll("[^\\w\\s]", "");
		        List<StorageProductId> productList = ord_dt1.findStoProID(productName);
		        // Xử lý danh sách productList cho từng giá trị productName
		        String p1= "req";
		        for (StorageProductId product : productList) {
		            long temp_storageID = product.getSto_id();
		            float temp_proprice = product.getPro_price();
		            float temp_stoprice = product.getSto_price();
		            // Các thao tác khác với product
		            Orderdetails it1 = new Orderdetails();		            
		            it1.setSto_id(temp_storageID);
		            it1.setOdt_quantity(quantity1);
		            it1.setOdt_importPrice(temp_stoprice);
		            it1.setOdt_exportPrice(temp_proprice);
		            ord_dt1.insert1(it1);
		            
		        }
		    }
		} 
		String t1231= "";
		if(t1231=="") {
			String email1 = (String) request.getSession().getAttribute("email");
			User mop = ord1.findByEmail(email1,firstname,lastname);
			long iduser = mop.getUsr_id();
			Order it = new Order();
			it.setUsr_id(iduser);
			it.setOrd_address(address);
			ord1.insert1(it);
		}

		
		
		if(email!=null) {
			model.addAttribute("logout","logout");	
		}
		else if(email == null) {
			model.addAttribute("login","login");
		}
		 Integer showcart = (Integer) request.getSession().getAttribute("cartShow");
		 if(showcart !=null) {
				Integer showcart1 = (Integer) request.getSession().getAttribute("cartShow");
				model.addAttribute("cartShow",showcart1);
				  request.getSession().removeAttribute("cartShow");
				  request.getSession().removeAttribute("listCart");
			}
		 model.addAttribute("SuccesOrder","You have successfully completed the order");
		 model.addAttribute("BackShop","Back Shop");
		 productNames1 = null;
		 productPrices1 = null;
		 productQuantity1 = null;
		 request.getSession().removeAttribute("cartlist");
		return "client/checkout";
	}
	
	@GetMapping("/login")
	public String Login() {
		return "login";

	}
	public boolean checkPassword(String password, String hashedPassword) {
	    return BCrypt.checkpw(password, hashedPassword);
	}
	@PostMapping("/submitlogin")
	public String Submitlogin(@RequestParam("email")String email,@RequestParam("password")String password,HttpServletRequest request,Model model,CartAddTo1 cartAddTo1) {
		String tmp_email = email;
		String tmp_pass = password;
		
		List<Review> temp = rev1.findAllUser();
		for (Review usr : temp) {			
			String t= usr.getUsr_role();
			String tp= "đd";
		    if (tmp_email.equals(usr.getUsr_email()) && checkPassword(tmp_pass,usr.getUsr_password())) {
		        // Mật khẩu khớp
		    	String m="d";
		        if (usr.getUsr_role().equals("USER")) {
		            request.getSession().setAttribute("roles", "USER");
		            request.getSession().setAttribute("email", tmp_email);
		            model.addAttribute("account", tmp_email);
		            model.addAttribute("logout", "logout");
		            return "redirect:/";
		        } else if (usr.getUsr_role().equals("ADMIN")) {
		            request.getSession().setAttribute("roles", "ADMIN");
		            model.addAttribute("logout", "logout");
		            request.getSession().setAttribute("email", tmp_email);
		            model.addAttribute("account", tmp_email);
		            model.addAttribute("showadmin", "Admin");
		            return "redirect:/admin";
		        }
		    }
		}
		return "redirect:/login";
	}
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		 request.getSession().invalidate();
		  request.getSession().removeAttribute("email");
		    request.getSession().removeAttribute("roles");
		return"redirect:/login?logout";
	}
	
	@GetMapping("/register")
	public String Register() {
		return "register";

	}
	public static String encryptPassword(String password) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(password, salt);
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
		String tmp_pass2 = encryptPassword(tmp_pass);
		String tmp_cfpass = cfpass;
		
		if (!tmp_pass.equals(tmp_cfpass)) {
		       model.addAttribute("errorMessage", "Mật khẩu không khớp !");
		       return "register";
		}
		review.setUsr_firstName(tmp_firstname);
		review.setUsr_lastName(tmp_lastname);
		review.setUsr_telephone(tmp_phone);
		review.setUsr_email(tmp_email);
		review.setUsr_password(tmp_pass2);
		
		String roles = "USER";
		review.setUsr_role(roles);
		
		rev1.insertUser(review);
		
		String siteURL =Utility.getSiteURL(request);
		rev1.sendVerificationEmail(review, siteURL);
	
		 model.addAttribute("successMessage", "Đăng ký thành công !");
		return "redirect:/login";

	}
}
