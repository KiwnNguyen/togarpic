package com.togarpic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.togarpic.model.User;
import com.togarpic.model.Cart;
import com.togarpic.model.CartItem;
import com.togarpic.repository.UserRepository;
import com.togarpic.repository.CartRepository;
import com.togarpic.repository.CartItemRepository;



@Controller
@RequestMapping("/admin")
public class AdminController  implements WebMvcConfigurer{
	
	//----------- rep -------------------------
	@Autowired
	private UserRepository  usr1;

	@Autowired
	private CartRepository  car1;
	
	//----------- rep -------------------------
	
	@Autowired
	private CartItemRepository  carItem1;
	
	//----------- rep -------------------------
	
	@RequestMapping("/")
	public String Admin() {
		return"admin/admin";
		
	}
	//show data
	@RequestMapping("/table")
	public String table(Model model) {
		  try {
			  Iterable<User> usr = usr1.findAll();		
			  model.addAttribute("listUser",usr);
			  
			  Iterable<Cart> car = car1.findAll1();
			  model.addAttribute("listCart",car);
			  
			  Iterable<CartItem> carItem = carItem1.findAll2();
			  model.addAttribute("listCartItem",carItem);
			  
			  
			  
			  return"admin/tableBasic";
			  
			  
		  }catch(Exception ec) {
			  ec.printStackTrace();
			throw new RuntimeException("list error!!");
		  }
		
		
	}
	 //.........Action Delete 
	  @PostMapping("/delete")
	  public String DeleteUser(Model model,@RequestParam("id1") String id1){
		  //---Delete Product----
		  System.out.println("id1 = "+id1);
		
		  
		  
		  if(id1 != null ){
			  long newparlong;
			  newparlong = Long.valueOf(id1);
			  usr1.deleteById(newparlong); 
		  }
		  
			/*
			 * //---Delete Recipe---- if(id2 != null){ long newparlong1; newparlong1 =
			 * Long.valueOf(id2); rec1.deleteById(newparlong1); }
			 */
		
	 	
		  return "redirect:/admin/table";
	  }
	  @PostMapping("/delete1")
	  public String DeleteCart(Model model,@RequestParam("id2") String id2){
		  //---Delete Product----
		
		  System.out.println("id2 = "+id2);
			
			  //---Delete Recipe---- 
		  	if(id2 != null){ 
		  		long newparlong1; 
		  		newparlong1 = Long.valueOf(id2);
		  		car1.deleteById(newparlong1); 
		  	}
			 
		
	 	
		  return "redirect:/admin/table";
	  }
	  @PostMapping("/delete2")
	  public String DeleteCartItem(Model model,@RequestParam("id3") String id3){
		  
		  System.out.println("id3 = "+id3);
		  
		  	if(id3 != null){ 
		  		long newparlong2; 
		  		newparlong2 = Long.valueOf(id3);
		  		carItem1.deleteById(newparlong2); 
		  	}
			 
		
	 	
		  return "redirect:/admin/table";
	  }
	  
	  
	//---- Action Delete -----
	
	//---- Action insert -----
	  @RequestMapping(value="/insertUser", method = RequestMethod.GET)
	    public String createUserForm(Model model) {
		  
		  	return"admin/insertUser";		  	      
	         
	    }  
	//---- Action insert ----
	@RequestMapping("/database")
	public String database() {
		return"admin/Databases";
		
	}
	@RequestMapping("/login")
	public String Login() {
		return"admin/login";
		
	}
	@RequestMapping("/register")
	public String Register() {
		return"admin/register";
		
	}
	@RequestMapping("/flowchart")
	public String Flowchart() {
		return"admin/flowchart";
		
	}
	@RequestMapping("/map")
	public String Map() {
		return"admin/map";
		
	}
	@RequestMapping("/mailbox")
	public String Mailbox() {
		return"admin/mailbox";
		
	}
	@RequestMapping("/mailCompose")
	public String mailCompose() {
		return"admin/mail_compose";
		
	}
	@RequestMapping("/invoice")
	public String invoice() {
		return"admin/invoice";
		
	}
	@RequestMapping("/profile")
	public String profile() {
		return"admin/profile";
		
	}
	
	//------------ error 404 -----------
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error404").setViewName("error404");
    }
	//------------ error 404 -----------
	
	
	
	 
	
	
}	
