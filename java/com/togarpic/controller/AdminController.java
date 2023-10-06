package com.togarpic.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import com.togarpic.model.User;
import com.togarpic.model.MyUploadForm;
import com.togarpic.model.Cart;
import com.togarpic.model.CartItem;
import com.togarpic.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

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
	    public String createUserForm(@ModelAttribute User user,Model model) {
		 	try {

				  MyUploadForm myUploadForm2 = new MyUploadForm();
			      model.addAttribute("myUploadForm", myUploadForm2);
			      
			      return "admin/insertUser";
			      
		  	}catch(Exception ec) {
		  		ec.printStackTrace();
				throw new RuntimeException("Error in page insert!!");
		  	}		  		      
//		  	return"admin/insertUser";		  	      
	         
	    }  
	  @RequestMapping(value = "/insertUserSubmit", method = RequestMethod.POST)
		 public String InsertUser( @RequestParam("name") String firstName,String lastName,String telephone,String email,String password,@RequestParam("id") int id,User user,Model model
				 ,@RequestParam("image") String image, @RequestParam("role") boolean role,
				 @RequestParam("fileDatas") MultipartFile file1
				 ,MyUploadForm myUploadForm,
				 @ModelAttribute("myUploadForm") MyUploadForm myUploadForm1,
				 HttpServletRequest request){
			  
			  try {
			 		System.out.println("MultipartFile file1 = "+file1+" ===== ");
					
//					  System.out.println("id = "+id);
					  System.out.println("======================================");
					  
					  System.out.println("firstName = "+firstName+" ======= ");
					  System.out.println("lastName"+lastName+" ======= ");
					  System.out.println("telephone = "+telephone+" ======= ");
					  System.out.println("email = "+email+" ======= ");
					  System.out.println("image = "+image+"=======");
					  
					  System.out.println("======================================");
					  System.out.println("password = "+password+" ======= ");
					  
					  System.out.println("role = "+role);
					  
					  System.out.println("======================================");
					 

				 user.setUsr_firstName(firstName); user.setUsr_lastName(lastName); user.setUsr_telephone(telephone); user.setUsr_email(email); user.setUsr_image(image); user.setUsr_password(password);

					  
				 usr1.insert(user);
				
			      
//				 this.doUpload(request, model, myUploadForm1);
				 	
				 	
				 	//------- ----------------------------
				 	  Path staticPath = Paths.get("src", "main", "resources", "static","upload");
				 	  
				 	  String temp1 = staticPath.toString();
				 	 System.out.println(" staticPath:  "+temp1 +" === ");
				 	 File uploadRootDir1 = new File(temp1);
				 	 
				 	 System.out.println("====== uploadRootDir1 :  "+uploadRootDir1 +" === ");
				 	   if (!uploadRootDir1.exists()) {
				 	         uploadRootDir1.mkdirs();
				 	   }
				 	   
				 	   
				 	  MultipartFile[] fileDatas = myUploadForm.getFileDatas();
				 	  System.out.println(" ====== file Datas" + fileDatas + "======");
				 	  
				    //----------------------------------------------------

				    //MultipartFile[] fileDatas = myUploadForm.getFileDatas();
					 List<File> uploadedFiles = new ArrayList<File>();
					 ///Đường dẫn hình ảnh
					 for (MultipartFile fileData : fileDatas) {
						 
						 
						 String originalFilename = fileData.getOriginalFilename();
						 try {
							 File serverFile = new File(uploadRootDir1.getAbsolutePath() + File.separator + originalFilename);
							 
							 System.out.println("======== Sum = "+serverFile+" =======");
							 BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
							 
							  stream.write(fileData.getBytes());
				              stream.close();
						 
				              uploadedFiles.add(serverFile);
				              System.out.println("Write file: " + serverFile);
						 }catch(Exception ex) {
							 
							 
						 }
				  
					 }
				  
				 Iterable<User> usr = usr1.findAll();
				 model.addAttribute("listUsers",usr);
					 
				 
				 
			  }catch(Exception ec) {
				  ec.printStackTrace();
				throw new RuntimeException("Error insert!!");
			  }
			  
			  return "redirect:/admin/table";
			
		
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
