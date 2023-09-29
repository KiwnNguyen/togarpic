package com.controller.Shop;



import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import com.controller.Model.product;
import com.controller.Model.recipe;
import com.controller.Repository.ProductRepository;
import com.controller.Repository.RecipeRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;





@Controller
@RequestMapping("/admin")
public class AdminController  implements WebMvcConfigurer{
	
	//----------- rep -------------------------
	@Autowired
	private ProductRepository  pro1;

	@Autowired
	private RecipeRepository  rec1;
	
	//----------- rep -------------------------
	
	@RequestMapping("/")
	public String Admin() {
		return"decorators/admin";
		
	}
	//show data
	@RequestMapping("/table")
	public String table(Model model) {
		  try {
			  Iterable<product> pro = pro1.findAll();		
			  model.addAttribute("listProduct",pro);
			  
			  Iterable<recipe> rec = rec1.findAll1();	
			  model.addAttribute("listRecipe",rec);
			  
			  
			  
			  return"decorators/tableBasic";
			  
			  
		  }catch(Exception ec) {
			  ec.printStackTrace();
			throw new RuntimeException("list error!!");
		  }
		
		
	}
	 //.........Action Delete 
	  @PostMapping("/delete")
	  public String DeleteProduct(Model model,@RequestParam("id1") String id1){
		  //---Delete Product----
		  System.out.println("id1 = "+id1);
		
		  
		  
		  if(id1 != null ){
			  long newparlong;
			  newparlong = Long.valueOf(id1);
			  pro1.deleteById(newparlong); 
		  }
		  
			/*
			 * //---Delete Recipe---- if(id2 != null){ long newparlong1; newparlong1 =
			 * Long.valueOf(id2); rec1.deleteById(newparlong1); }
			 */
		
	 	
		  return "redirect:/admin/table";
	  }
	  @PostMapping("/delete1")
	  public String DeleteRecipe(Model model,@RequestParam("id2") String idrec){
		  //---Delete Product----
		
		  
			
			  //---Delete Recipe---- 
		  	if(idrec != null){ 
		  		long newparlong1; 
		  		newparlong1 = Long.valueOf(idrec);
		  		rec1.deleteById(newparlong1); 
		  	}
			 
		
	 	
		  return "redirect:/admin/table";
	  }
	  
	  
	//---- Action Delete -----
	
	//---- Action insert -----
	  @RequestMapping(value="/insert1", method = RequestMethod.GET)
	    public String insertpro(Model model) {
		  
		  	return"insertpro";
		  	
	      
	         
	    }  
	  @RequestMapping(value = "/insert1submit", method = RequestMethod.POST)
		 public String InsertCategory( @RequestParam("proname") String name,product product1,Model model
				 ,@RequestParam("proimage") String image, @RequestParam("proprice") float price){
			  try {
					  System.out.println("name = "+name+" ======= ");
					  System.out.println("image = "+image+"=======");
					  System.out.println("price = "+price);
						
					  
					  /*
						 * product1.setPro_name(name); product1.setPro_price(price);
						 * product1.setPro_image(image);
						 * 
						 * pro1.insert(product1);
						 */
					
			      
				  
					 Iterable<product> pro = pro1.findAll();		
					  model.addAttribute("listProduct",pro);
					 
				 
				 return "redirect:/admin/table";
			  }catch(Exception ec) {
				  ec.printStackTrace();
				  throw new RuntimeException("Error value insert!!");
			  }
			  
			
			
		
		 }
	  
	  
	  //----------------------------------------------------------
	  @RequestMapping(value="/insert2", method = RequestMethod.GET)
	    public String insertrec(Model model) {
		  
		  	return"insertrec";
		  	
	      
	         
	    } 
	//---- Action insert ----
	@RequestMapping("/database")
	public String database() {
		return"decorators/Databases";
		
	}
	@RequestMapping("/login")
	public String Login() {
		return"decorators/login";
		
	}
	@RequestMapping("/register")
	public String Register() {
		return"decorators/register";
		
	}
	@RequestMapping("/flowchart")
	public String Flowchart() {
		return"decorators/flowchart";
		
	}
	@RequestMapping("/map")
	public String Map() {
		return"decorators/map";
		
	}
	@RequestMapping("/mailbox")
	public String Mailbox() {
		return"decorators/mailbox";
		
	}
	@RequestMapping("/mailCompose")
	public String mailCompose() {
		return"decorators/mail_compose";
		
	}
	@RequestMapping("/invoice")
	public String invoice() {
		return"decorators/invoice";
		
	}
	@RequestMapping("/profile")
	public String profile() {
		return"decorators/profile";
		
	}
	
	//------------ error 404 -----------
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error404").setViewName("error404");
    }
	//------------ error 404 -----------
	
	
	
	 
	
	
}	
