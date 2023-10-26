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

import com.togarpic.model.MyUploadForm;
import com.togarpic.model.Product;
import com.togarpic.model.Cart;
import com.togarpic.model.CartItem;
import com.togarpic.model.CartItemView;
import com.togarpic.model.CartView;
import com.togarpic.model.User;
import com.togarpic.repository.CartItemRepository;
import com.togarpic.repository.CartRepository;
import com.togarpic.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController{
	
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
		return"admin/dashboard";
		
	}
	//show data
	@RequestMapping("/table")
	public String table(Model model) {
		  try {
			  Iterable<User> usr = usr1.findAll();		
			  model.addAttribute("listUser",usr);
			  
			  Iterable<Cart> car = car1.findAll1();
			  model.addAttribute("listCart",car);
			  
			  Iterable<CartView> cart = car1.findCart();
			  model.addAttribute("listCartView",cart);
			  
			  Iterable<CartItem> carItem = carItem1.findAll2();
			  model.addAttribute("listCartItem",carItem);
			  
			  Iterable<CartItemView> cartItem = carItem1.findCartItem();
			  model.addAttribute("listCartItemView",cartItem);
			  
			  
			  
			  return"admin/tableBasic";
			  
			  
		  }catch(Exception ec) {
			  ec.printStackTrace();
			throw new RuntimeException("list error!!");
		  }
		
		
	}
	@RequestMapping(value = "/listUser", method = RequestMethod.GET)
	public String showUserList(Model model) {
		Iterable<User> usr = usr1.findAll();
		model.addAttribute("listUser", usr);
		return "admin/user/listUser";
	}
	@RequestMapping(value = "/listCart", method = RequestMethod.GET)
	public String showCartList(Model model) {
		Iterable<CartView> cart = car1.findCart();
		model.addAttribute("listCartView", cart);
		return "admin/cart/listCart";
	}
	@RequestMapping(value = "/listCartItem", method = RequestMethod.GET)
	public String showCartItemList(Model model) {
		Iterable<CartItemView> cartItem = carItem1.findCartItem();
		model.addAttribute("listCartItemView", cartItem);
		return "admin/cartitem/listCartItem";
	}

	 //.........Action Delete 
	  @PostMapping("/delete")
	  public String DeleteUser(Model model,@RequestParam("id1") String id1){
		  
		  int newparint;
		  
		  newparint = Integer.parseInt(id1);
		  
		  User template =  usr1.findById(newparint);
		  System.out.println("template = "+ template);
		  
		  
		 System.out.print("id delete ="+newparint);
		 System.out.print("======================================");
			
		 usr1.deleteById(newparint); 

			//lấy đường dẫn hình ảnh theo id được chọn
		 
		 String imagetemp = template.getUsr_image();
		 
		 System.out.println("Template String image = "+imagetemp);
		 
		 
		 File imageFile = new File("src/main/resources/static/image/" + imagetemp);
	 	 System.out.println("static + image = "+imageFile);
	 	 
	 	 if(imageFile.exists()) {
	 		imageFile.delete();
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
	

//		 }
	  @RequestMapping(value = "/insertUser", method = RequestMethod.GET)
		public String insertUser(Model model) {
			try {

				  MyUploadForm myUploadForm2 = new MyUploadForm();
			      model.addAttribute("myUploadForm", myUploadForm2);
			      
			      return "/admin/user/insertUser";
			      
		  	}catch(Exception ec) {
		  		ec.printStackTrace();
				throw new RuntimeException("Error in page insert!!");
		  	}
		  	
//			return "/admin/insertUser";

		}

		@RequestMapping(value = "/insertUserSubmit", method = RequestMethod.POST)
		public String InsertUserSubmit(Model model,@RequestParam("firstName") String firstName,
				@RequestParam("lastName")String lastName,@RequestParam("telephone") String telephone,
				@RequestParam("email")String email,@RequestParam("image") String image,
				@RequestParam("password")String password,
				@RequestParam("role") String role,@RequestParam("fileDatas") MultipartFile file1
				 ,MyUploadForm myUploadForm,
				 @ModelAttribute("myUploadForm") MyUploadForm myUploadForm1,
				 HttpServletRequest request, User user) {
			try {

				
				System.out.println("firstName = " + firstName);
				System.out.println("lastName = " + lastName);
				System.out.println("telephone = " + telephone);
				System.out.println("email = " + email);
				System.out.println("image = " + image);
				System.out.println("password = " + password);
				System.out.println("role = " + role);
				
				user.setUsr_firstName(firstName);
				user.setUsr_lastName(lastName);
				user.setUsr_telephone(telephone);
				user.setUsr_email(email);
				user.setUsr_image(image);
				user.setUsr_password(password);
				user.setUsr_role(role);
				
				usr1.insert(user);

				Path staticPath = Paths.get("src", "main", "resources", "static","image");
				  String usr1 = staticPath.toString();
				 	 System.out.println(" staticPath:  "+usr1 +" === ");
				 	 File uploadRootDir1 = new File(usr1);
				 	if (!uploadRootDir1.exists()) {
			 	         uploadRootDir1.mkdirs();
			 	   }
				 	MultipartFile[] fileDatas = myUploadForm.getFileDatas();
				 	List<File> uploadedFiles = new ArrayList<File>();
				 	 for (MultipartFile fileData : fileDatas) {
				 		 //Lấy tên ảnh
				 		String originalFilename = fileData.getOriginalFilename();
				 		 try {
				 			 // Đường dẫn static + tên đường dẫn ảnh
				 			File serverFile = new File(uploadRootDir1.getAbsolutePath() + File.separator + originalFilename);
				 			System.out.println("static + image" + serverFile);
				 			
				 			 BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
							 
							  stream.write(fileData.getBytes());
				              stream.close();
						 
				              uploadedFiles.add(serverFile);
				              System.out.println("Write file: " + serverFile);
				 			
				 			
				 		 }catch(Exception ex) {
				 			 
				 		 }
				 		 
				 	 }
				 	 
				 	 
				 	   
				 	   
				 	   
				System.out.println("hi" + staticPath);
				
			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error value insert!!");
			}
			MultipartFile[] fileDatas = myUploadForm.getFileDatas();
			

		 	
			
		 	  System.out.println(" ====== file Datas" + fileDatas + "======");
			Iterable<User> usr = usr1.findAll();
			model.addAttribute("listOrder", usr);
			return "redirect:/admin/table";
		}
		
		@RequestMapping(value = "/insertCart", method = RequestMethod.GET)
		public String insertcart(Model model) {
			model.addAttribute("Cart", car1.findAll1());
			return "admin/cart/insertCart";

		}
//
//		// ----------------------------------------------------------
		@RequestMapping(value = "/insertCartSubmit", method = RequestMethod.POST)
		public String insertcart(Model model, 
			@RequestParam("usr_id") long usr_id, Cart cart) {

			System.out.println("usr_id = " + usr_id);

			try {
				cart.setUsr_id(usr_id);
				car1.insert(cart);
			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error submit cart!!");
			}
			Iterable<Cart> car = car1.findAll1();
			model.addAttribute("listCart", car);
			return "redirect:/admin/table";

		}
		
		
		
		@RequestMapping(value = "/insertCartItem", method = RequestMethod.GET)
		public String insertcartitem(Model model) {
			Iterable<CartItem> carItem = carItem1.findAll2();
			model.addAttribute("listCartItem", carItem);
			model.addAttribute("Cart", car1.findAll1());
			Iterable<Product> carItem2 = carItem1.findProduct();
			model.addAttribute("listProduct", carItem2);

			
			
			
			
			return "admin/cartitem/insertCartItem";

		}

		// ----------------------------------------------------------
		@RequestMapping(value = "/insertCartItemSubmit", method = RequestMethod.POST)
		public String insertcartitemsubmit(Model model, @RequestParam("car_id") long car_id
				,@RequestParam("pro_id") long pro_id,
				@RequestParam("cai_quantity") float cai_quantity, CartItem cartitem) {

			System.out.println("car_id = " + car_id);
			System.out.println("pro_id = " + pro_id);
			System.out.println("cai_quantity = " + cai_quantity);

			try {
				cartitem.setCar_id(car_id);
				cartitem.setPro_id(pro_id);
				cartitem.setCai_quantity(cai_quantity);
				carItem1.insert(cartitem);
			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error submit cartitem!!");
			}
			Iterable<CartItem> carItem = carItem1.findAll2();
			model.addAttribute("listCartItem", carItem);
			return "redirect:/admin/table";

		}
		@RequestMapping(value = "/updateUser", method = RequestMethod.GET)
		public String updateuser(Model model, @RequestParam ("id1") long id) {
			

			User item = usr1.findById(id);

			model.addAttribute("usr_id", item.getUsr_id());
			model.addAttribute("firstName", item.getUsr_firstName());
			model.addAttribute("lastName", item.getUsr_lastName());
			model.addAttribute("telephone", item.getUsr_telephone());
			model.addAttribute("email", item.getUsr_email());
			model.addAttribute("image", item.getUsr_image());
			model.addAttribute("password", item.getUsr_password());
			model.addAttribute("role", item.getUsr_role());
			System.out.println("yes = "+id);
			 User template =  usr1.findById(id);
			 String tem = template.getUsr_image();
			System.out.println("Getimage = "+tem);
			model.addAttribute("image", tem);
			
			 //xóa hình đã khi thay đổi hình khác
			
			  
			  
			  File imageFile = new File("src/main/resources/static/image/" + tem);
			  System.out.println("static + image = "+imageFile);
			 	 
			  if(imageFile.exists()) {
			 		imageFile.delete();
			  }
			
			
			
			MyUploadForm myUploadForm2 = new MyUploadForm();
		    model.addAttribute("myUploadForm", myUploadForm2);
		      
			return "admin/user/updateUser";

		}

		@RequestMapping(value = "/updateUserEdit", method = RequestMethod.POST)
		public String update_user_edit(Model model,@RequestParam("firstName") String firstName,
				@RequestParam("lastName")String lastName,@RequestParam("telephone") String telephone,
				@RequestParam("email")String email,@RequestParam("image") String image,
				@RequestParam("password")String password,
				@RequestParam("role") String role,@RequestParam("fileDatas") MultipartFile file1
				 ,MyUploadForm myUploadForm,
				 @ModelAttribute("myUploadForm") MyUploadForm myUploadForm1,
				 HttpServletRequest request, User user) {
			try {

				
				System.out.println("firstName = " + firstName);
				System.out.println("lastName = " + lastName);
				System.out.println("telephone = " + telephone);
				System.out.println("email = " + email);
				System.out.println("image = " + image);
				System.out.println("password = " + password);
				System.out.println("role = " + role);
				
				user.setUsr_firstName(firstName);
				user.setUsr_lastName(lastName);
				user.setUsr_telephone(telephone);
				user.setUsr_email(email);
				user.setUsr_image(image);
				user.setUsr_password(password);
				user.setUsr_role(role);
				
				usr1.update(user);

				Path staticPath = Paths.get("src", "main", "resources", "static","image");
				  String usr1 = staticPath.toString();
				 	 System.out.println(" staticPath:  "+usr1 +" === ");
				 	 File uploadRootDir1 = new File(usr1);
				 	if (!uploadRootDir1.exists()) {
			 	         uploadRootDir1.mkdirs();
			 	   }
				 	MultipartFile[] fileDatas = myUploadForm.getFileDatas();
				 	List<File> uploadedFiles = new ArrayList<File>();
				 	 for (MultipartFile fileData : fileDatas) {
				 		 //Lấy tên ảnh
				 		String originalFilename = fileData.getOriginalFilename();
				 		 try {
				 			 // Đường dẫn static + tên đường dẫn ảnh
				 			File serverFile = new File(uploadRootDir1.getAbsolutePath() + File.separator + originalFilename);
				 			System.out.println("static + image" + serverFile);
				 			
				 			 BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
							 
							  stream.write(fileData.getBytes());
				              stream.close();
						 
				              uploadedFiles.add(serverFile);
				              System.out.println("Write file: " + serverFile);
				 			
				 			
				 		 }catch(Exception ex) {
				 			 
				 		 }
				 		 
				 	 }
				 	 
				 	 
				 	   
				 	   
				 	   
				System.out.println("hi" + staticPath);
				
			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error value insert!!");
			}
			MultipartFile[] fileDatas = myUploadForm.getFileDatas();
			

		 	
			
		 	  System.out.println(" ====== file Datas" + fileDatas + "======");
			Iterable<User> usr = usr1.findAll();
			model.addAttribute("listOrder", usr);
			return "redirect:/admin/table";
		}
		
		/***
		 * 
		 * @param model order_details
		 * @param id    order_details
		 * @return update table order_details
		 */
		@RequestMapping(value = "/updateCart", method = RequestMethod.GET)
		public String updatecart(Model model, @RequestParam("id2")long id) {
//
			Cart item = car1.findById(id);
//
			model.addAttribute("UpCart", car1.findAll1());
			model.addAttribute("usr_id", item.getUsr_id());
			model.addAttribute("car_id", item.getCar_id());
			System.out.println(id);
			return "admin/cart/updateCart";

		}

		@RequestMapping(value = "/updateCartEdit", method = RequestMethod.POST)
		public String update_cart_edit(Model model, 
				@RequestParam("usr_id") long usr_id, Cart cart) {

				System.out.println("usr_id = " + usr_id);

				try {

					cart.setUsr_id(usr_id);
					car1.update(cart);
				} catch (Exception ec) {
					ec.printStackTrace();
					throw new RuntimeException("Error submit cart!!");
				}
				Iterable<Cart> car = car1.findAll1();
				model.addAttribute("listCart", car);
				return "redirect:/admin/table";

			}
			
		
		/***
		 * 
		 * @param model review
		 * @param id    review
		 * @return update table review
		 */
		@RequestMapping(value = "/updateCartItem", method = RequestMethod.GET)
		public String update_cartitem(Model model, @RequestParam("id3")long id) {

			CartItem item = carItem1.findById(id);
			Iterable<Product> carItem2 = carItem1.findProduct();
			model.addAttribute("listProduct", carItem2);

			model.addAttribute("car_id", item.getCar_id());
			model.addAttribute("pro_id", item.getPro_id());
			model.addAttribute("cai_quantity", item.getCai_quantity());
			System.out.println(id);
			return "admin/cartitem/updateCartItem";

		}

		@RequestMapping(value = "/updateCartItemEdit", method = RequestMethod.POST)
		public String update_cartitem_edit(Model model, @RequestParam("car_id") long car_id, @RequestParam("pro_id") long pro_id,
				@RequestParam("cai_quantity") float cai_quantity, CartItem cartitem) {

			System.out.println("car_id = " + car_id);
			System.out.println("pro_id = " + pro_id);
			System.out.println("cai_quantity = " + cai_quantity);

			try {
				cartitem.setCar_id(car_id);
				cartitem.setPro_id(pro_id);
				cartitem.setCai_quantity(cai_quantity);
				carItem1.update(cartitem);
			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error submit cartitem!!");
			}
			Iterable<CartItem> carItem = carItem1.findAll2();
			model.addAttribute("listCartItem", carItem);
			return "redirect:/admin/table";

		}
		// ---- Action update ----
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
	public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error404").setViewName("error404");
    }
	//------------ error 404 -----------
	
	
	
	 
	
	
}	



