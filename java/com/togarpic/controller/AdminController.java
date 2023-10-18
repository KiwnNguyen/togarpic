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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import com.togarpic.repository.*;

import com.togarpic.model.*;
import com.togarpic.model.recipedetails.*;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private RecipeRepository reciRepo;

	@Autowired
	private CategoryRepository cateRepo;

	@Autowired
	private RecipeDetailsRepository rdetRepo;

	@Autowired
	private ProductRepository prodRepo;

	@Autowired
	private UserRepository  usr1;

	@Autowired
	private CartRepository  car1;
	
	@Autowired
	private CartItemRepository  carItem1;

	/*----*/

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showIndex() {

		return "admin/dashboard";
	}

	@RequestMapping(value = "/alltable", method = RequestMethod.GET)
	public String showAllTable(Model model) {
		try {
			Iterable<User> usr = usr1.findAll();		
			model.addAttribute("listUser",usr);
			
			Iterable<Cart> car = car1.findAll1();
			model.addAttribute("listCart",car);
			
			Iterable<CartItem> carItem = carItem1.findAll2();
			model.addAttribute("listCartItem",carItem);
			
			Iterable<Recipe> listreci = reciRepo.findAll();
			model.addAttribute("listreci", listreci);

			Iterable<Category> listcate = cateRepo.findAll();
			model.addAttribute("listcate", listcate);

			Iterable<RecipeDetailsView> listrdet = rdetRepo.findAllname();
			model.addAttribute("listrdet", listrdet);
			
			return"admin/table";
			
			
		}catch(Exception ec) {
			ec.printStackTrace();
		  throw new RuntimeException("list error!!");
		}
	}


	@RequestMapping(value = "/listcategory", method = RequestMethod.GET)
	public String showCategoryList(Model model) {
		Iterable<Category> listcate = cateRepo.findAll();
		model.addAttribute("listcate", listcate);
		return "admin/category/list";
	}

	// Recipe
	@RequestMapping(value = "/listrecipe", method = RequestMethod.GET)
	public String showAllRecipe(Model model) {
		Iterable<Recipe> listreci = reciRepo.findAll();
		model.addAttribute("listreci", listreci);
		return "admin/recipe/list";
	}

	@RequestMapping(value = "/viewmore/{id}", method = RequestMethod.GET)
	public String viewMoreRecipe(@PathVariable int id, Model model) {
		int parseId;
		parseId = Integer.valueOf(id);
		Iterable<RecipeDetailsView> listprodreci = rdetRepo.findByIdname(parseId);
		model.addAttribute("listprodreci", listprodreci);
		return "admin/recipe/details";
	}


	// DELETE ACTION

	// DELETE USER TABLE

	@PostMapping("/delete")
	public String DeleteUser(Model model,@RequestParam("id1") String id1){
		  
		int newparint;
		  
		newparint = Integer.parseInt(id1);
		  
		User template =  usr1.findById(newparint);
			
		usr1.deleteById(newparint); 

			//lấy đường dẫn hình ảnh theo id được chọn
		 
		String imagetemp = template.getUsr_image();
		 		 
		File imageFile = new File("src/main/resources/static/image/" + imagetemp);
	 	 
	 	if(imageFile.exists()) {
	 		imageFile.delete();
	 	}
	 		 	
		return "redirect:/admin/alltable";
	  }

	// DELETE CART TABLE
	@PostMapping("/delete1")
	public String DeleteCart(Model model,@RequestParam("id2") String id2){
			
		  //---Delete Recipe---- 
		  	if(id2 != null){ 
		  		long newparlong1; 
		  		newparlong1 = Long.valueOf(id2);
		  		car1.deleteById(newparlong1); 
		  	}
			
		  return "redirect:/admin/alltable";
	  }

	// DELETE CART ITEM TABLE
	@PostMapping("/delete2")
	public String DeleteCartItem(Model model,@RequestParam("id3") String id3){
		  		  
		  	if(id3 != null){ 
		  		long newparlong2; 
		  		newparlong2 = Long.valueOf(id3);
		  		carItem1.deleteById(newparlong2); 
		  	}

		  	return "redirect:/admin/alltable";
	  }

	// Category
	@RequestMapping(value = "/delCategory/{id}", method = RequestMethod.GET)
	public String deleteCategory(Model model, @PathVariable Integer id) {

		if (id != null) {
			int parseId;
			parseId = Integer.valueOf(id);
			cateRepo.deleteById(parseId);
		}

		return "redirect:/admin/listcategory";
	}

	// Recipe
	@RequestMapping(value = "/delRecipe/{id}", method = RequestMethod.GET)
	public String deleteRecipe(Model model, @PathVariable Integer id) {

		if (id != null) {
			int parseId;
			parseId = Integer.valueOf(id);
			reciRepo.deleteById(parseId);
		}

		return "redirect:/admin/listrecipe";
	}
	// INSERT ACTION

	//SHOW INSERT CART
	@RequestMapping(value = "/insertCart", method = RequestMethod.GET)
	public String insertcart(Model model) {
			model.addAttribute("Cart", car1.findAll1());
			return "admin/cart/insert";

		}

	@RequestMapping(value = "/insertCartSubmit", method = RequestMethod.POST)
	public String insertcart(Model model, 
			@RequestParam("usr_id") long usr_id, Cart cart) {
			try {
				cart.setUsr_id(usr_id);
				car1.insert(cart);
			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error submit cart!!");
			}
			Iterable<Cart> car = car1.findAll1();
			model.addAttribute("listCart", car);
			return "redirect:/admin/alltable";

		}
	
	//SHOW INSERT CART ITEM
	@RequestMapping(value = "/insertCartItem", method = RequestMethod.GET)
	public String insertcartitem(Model model) {
			return "admin/cartItem/insert";
		}

	@RequestMapping(value = "/insertCartItemSubmit", method = RequestMethod.POST)
	public String insertcartitemsubmit(Model model, @RequestParam("car_id") long car_id, @RequestParam("pro_id") long pro_id,
				@RequestParam("cai_quantity") float cai_quantity, CartItem cartitem) {

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

	// SHOW INSERT FORM
	@RequestMapping(value = "/insertUser", method = RequestMethod.GET)
	public String insertUser(Model model) {
		try {

			MyUploadForm myUploadForm2 = new MyUploadForm();
			model.addAttribute("myUploadForm", myUploadForm2);
			return "/admin/user/insert";
			      
		}catch(Exception ec) {
		  	ec.printStackTrace();
			throw new RuntimeException("Error in page insert!!");
		}
	}

	@RequestMapping(value = "/insertUserSubmit", method = RequestMethod.POST)
	public String InsertUserSubmit(Model model,@RequestParam("firstName") String firstName,
		@RequestParam("lastName")String lastName,@RequestParam("telephone") String telephone,
		@RequestParam("email")String email,@RequestParam("image") String image,
		@RequestParam("password")String password,
		@RequestParam("role") int role,@RequestParam("fileDatas") MultipartFile file1
		,MyUploadForm myUploadForm,
		@ModelAttribute("myUploadForm") MyUploadForm myUploadForm1,
		HttpServletRequest request, User user) {
		try {
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
			 			
				 	BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
							 
					stream.write(fileData.getBytes());
				    stream.close();
						 
				    uploadedFiles.add(serverFile);
				 			
				}catch(Exception ex) {
				 			 
				}
				 		 
			}
				
			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error value insert!!");
			}
			MultipartFile[] fileDatas = myUploadForm.getFileDatas();
			Iterable<User> usr = usr1.findAll();
			model.addAttribute("listOrder", usr);
			return "redirect:/admin/alltable";
		}

	// Category
	@RequestMapping(value = "/insCategory", method = RequestMethod.GET)
	public String showInsertCategory() {

		return "admin/category/insert";
	}

	@RequestMapping(value = "/insCategory", method = RequestMethod.POST)
	public String insertCategory(Model model, Category category, @RequestParam("title") String title) {
		try {
			category.setCat_name(title);

			cateRepo.insert(category);
			Iterable<Category> cat = cateRepo.findAll();
			model.addAttribute("listcate", cat);
			return "redirect:/admin/listcategory";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error value insert!!");
		}
	}

	// RECIPE
	@RequestMapping(value = "/insRecipe", method = RequestMethod.GET)
	public String showInsertRecipe() {

		return "admin/recipe/insert";
	}

	@RequestMapping(value = "/insRecipe", method = RequestMethod.POST)
	public String insertRecipe(Model model, Recipe recipe, @RequestParam("title") String title) {
		try {
			recipe.setRec_name(title);

			reciRepo.insert(recipe);
			Iterable<Recipe> reci = reciRepo.findAll();
			model.addAttribute("listreci", reci);
			return "redirect:/admin/listrecipe";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error value insert!!");
		}
	}

	// RECIPE DETAILS
	@RequestMapping(value = "/insProRecipe", method = RequestMethod.GET)
	public String showInsertRecipeDetails(Model model) {

		Iterable<Product> prod = prodRepo.findAll();
		model.addAttribute("listprod", prod);
		
		return "admin/recipe/adddetails";
	}

	@RequestMapping(value = "/insProRecipe/{idr}", method = RequestMethod.POST)
	public String insertRecipeDetails(Model model, @PathVariable(name = "idr") int idr, @RequestParam("quantity") String quantity, RecipeDetails rdt) {
		rdt.setRecipe_id(idr);
		rdt.setQuantity(quantity);
//		rdt.setProduct_id(idr);
		
		return "admin/recipe/adddetails";
	}

	// UPDATE ACTION

	// UPDATE USER TABLE
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
			User template =  usr1.findById(id);
			String tem = template.getUsr_image();
			model.addAttribute("image", tem);
			
			//xóa hình đã khi thay đổi hình khác
			 
			File imageFile = new File("src/main/resources/static/image/" + tem);
			 	 
			if(imageFile.exists()) {
			 	imageFile.delete();
			}
			
			MyUploadForm myUploadForm2 = new MyUploadForm();
		    model.addAttribute("myUploadForm", myUploadForm2);
		      
			return "admin/user/update";
		}

	@RequestMapping(value = "/updateUserEdit", method = RequestMethod.POST)
	public String update_user_edit(Model model,@RequestParam("firstName") String firstName,
				@RequestParam("lastName")String lastName,@RequestParam("telephone") String telephone,
				@RequestParam("email")String email,@RequestParam("image") String image,
				@RequestParam("password")String password,
				@RequestParam("role") int role,@RequestParam("fileDatas") MultipartFile file1
				 ,MyUploadForm myUploadForm,
				 @ModelAttribute("myUploadForm") MyUploadForm myUploadForm1,
				 HttpServletRequest request, User user) {
			try {
	
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
				
			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error value insert!!");
			}
			MultipartFile[] fileDatas = myUploadForm.getFileDatas();
			Iterable<User> usr = usr1.findAll();
			model.addAttribute("listOrder", usr);
			return "redirect:/admin/alltable";
		}

	/***
		 * 
		 * @param model order_details
		 * @param id    order_details
		 * @return update table order_details
		 */
	@RequestMapping(value = "/updateCart", method = RequestMethod.GET)
		public String updatecart(Model model, @RequestParam("id2")long id) {
			Cart item = car1.findById(id);
			model.addAttribute("usr_id", item.getUsr_id());
			model.addAttribute("car_id", item.getCar_id());
			System.out.println(id);
			return "admin/cart/update";
		}

	@RequestMapping(value = "/updateCartEdit", method = RequestMethod.POST)
	public String update_cart_edit(Model model, 
				@RequestParam("usr_id") long usr_id, Cart cart) {
				try {
					cart.setUsr_id(usr_id);
					car1.update(cart);
				} catch (Exception ec) {
					ec.printStackTrace();
					throw new RuntimeException("Error submit cart!!");
				}
				Iterable<Cart> car = car1.findAll1();
				model.addAttribute("listCart", car);
				return "redirect:/admin/alltable";
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
			model.addAttribute("car_id", item.getCar_id());
			model.addAttribute("pro_id", item.getPro_id());
			model.addAttribute("cai_quantity", item.getCai_quantity());
			return "admin/cartItem/update";

		}

	@RequestMapping(value = "/updateCartItemEdit", method = RequestMethod.POST)
	public String update_cartitem_edit(Model model, @RequestParam("car_id") long car_id, @RequestParam("pro_id") long pro_id,
				@RequestParam("cai_quantity") float cai_quantity, CartItem cartitem) {
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
			return "redirect:/admin/alltable";
		}


	// Category
	@RequestMapping(value = "/updCategory/{id}", method = RequestMethod.GET)
	public String showUpdateCategory(Model model, Category category, @PathVariable(name = "id") int id) {
		try {

			Category cat = cateRepo.findById(id);
			model.addAttribute("cate", cat);

			return "admin/category/update";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error value insert!!");
		}
	}

	@RequestMapping(value = "/updCategory/{id}", method = RequestMethod.POST)
	public String updateCategory(Model model, Category category, @PathVariable(name = "id") int id,
			@RequestParam String title) {
		try {

			category.setCat_name(title);
			category.setId(id);
			cateRepo.update(category);

			return "redirect:/admin/listcategory";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error value insert!!");
		}
	}

	// Recipe
	@RequestMapping(value = "/updRecipe/{id}", method = RequestMethod.GET)
	public String showUpdateRecipe(Model model, Recipe recipe, @PathVariable(name = "id") int id) {

		Recipe reci = reciRepo.findById(id);
		model.addAttribute("reci", reci);

		return "admin/recipe/update";
	}

	@RequestMapping(value = "/updRecipe/{id}", method = RequestMethod.POST)
	public String updateRecipe(Model model, Recipe recipe, @PathVariable(name = "id") int id,
			@RequestParam String title) {
		try {

			recipe.setRec_name(title);
			recipe.setId(id);
			reciRepo.update(recipe);

			return "redirect:/admin/listrecipe";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error value insert!!");
		}
	}

	//------------ error 404 -----------
	public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/error404").setViewName("error404");
    }
	//------------ error 404 -----------

}
