package com.togarpic.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.togarpic.repository.*;
import com.togarpic.model.*;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController implements WebMvcConfigurer {
	
	@Autowired
	private OrderRepository ord1;

	@Autowired
	private OrderDetailsRepository ord_det1;

	@Autowired
	private ReviewRepository rev1;
	
	@Autowired
	private StorageRepository sto1;

	@Autowired
	private ListInfoRepository listif1;
	
	@Autowired
	private ProductRepository product;

	@Autowired
	private StorageRepository storage;

	@Autowired
	private RecipeRepository reciRepo;

	@Autowired
	private CategoryRepository cateRepo;

	@Autowired
	private RecipeDetailsRepository rdetRepo;

	@Autowired
	private StorageRepository stoRepo;

	@Autowired
	private ProductRepository prodRepo;

	@Autowired
	private UserRepository  usr1;

	@Autowired
	private CartRepository  car1;
	
	@Autowired
	private CartItemRepository  carItem1;

	/*----*/

	//--------------Search----------------//
	@GetMapping("/searchOrder")
	  public String filterCate(@RequestParam("id") String usr,Model model) {
		  
			System.out.println("usr = "+usr);
			if(usr == "") {
				System.out.println("xam nhap 00");
				Iterable<Order> ord = ord1.findAll1();
				model.addAttribute("listOrder", ord);
			}
			if(usr.length() >= 1) {
				long temp = Long.parseLong(usr);
				 List<Order> filteredUsers = ord1.getOrdByFilter(temp);
				  model.addAttribute("listOrder",filteredUsers);  
				  
			}
		 
		
		  return"admin/order/tableBasic";
	  }
	
	//--------------Search----------------//

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showIndex() {

		return "admin/dashboard";
	}

	@RequestMapping("/database")
	public String database(Model model) {
		Iterable<Listinfo> listif = listif1.findAll1();
		model.addAttribute("listinfo", listif);

		return "admin/Databases";
	}

	@RequestMapping("/table_order")
		public String tableOrder(Model model) {
			Iterable<Order> ord = ord1.findAll1();
			model.addAttribute("listOrder", ord);
			return"admin/order/tableBasic";
		}
		@RequestMapping("/table_orderdt")
		public String tableOrder_details(Model model) {
			Iterable<Orderdetails> ord_details = ord_det1.findAll1();
			model.addAttribute("listOrderDetails", ord_details);
			return"admin/order_details/tableBasic";
		}
		@RequestMapping("/table_review")
		public String tableReview(Model model) {
			Iterable<Review> review = rev1.findAll1();
			model.addAttribute("listreview", review);
			return"admin/review/tableBasic";
		}

	

	@RequestMapping("/database")
	public String database(Model model) {
		Iterable<Listinfo> listif = listif1.findAll1();
		model.addAttribute("listinfo", listif);

		return "admin/Databases";
	}

	@RequestMapping("/table_order")
		public String tableOrder(Model model) {
			Iterable<Order> ord = ord1.findAll1();
			model.addAttribute("listOrder", ord);
			return"admin/order/tableBasic";
		}
		@RequestMapping("/table_orderdt")
		public String tableOrder_details(Model model) {
			Iterable<Orderdetails> ord_details = ord_det1.findAll1();
			model.addAttribute("listOrderDetails", ord_details);
			return"admin/order_details/tableBasic";
		}
		@RequestMapping("/table_review")
		public String tableReview(Model model) {
			Iterable<Review> review = rev1.findAll1();
			model.addAttribute("listreview", review);
			return"admin/review/tableBasic";
		}

	@RequestMapping(value = "/listproduct", method = RequestMethod.GET)
	public String listProductView(Model model) {
		try {
			List<ProductView> pro = product.findAll1();
			model.addAttribute("listProduct", pro);
			return "admin/product/list_product";
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("list error!!");
		}
	}

	@RequestMapping(value = "/insertproduct", method = RequestMethod.GET)
	public String insertProduct(Model model) {
		try {
			List<Category> cate = cateRepo.findAll();
			model.addAttribute("listCate", cate);
			MyUploadForm myUploadForm2 = new MyUploadForm();
			model.addAttribute("myUploadForm", myUploadForm2);

			return "admin/product/insert_product";
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error in page insert!!");
		}
	}

	@RequestMapping(value = "/insert2submit", method = RequestMethod.POST)
	public String InsertCategory(@RequestParam("pro_name") String name, Product product1, Model model,
			// @RequestParam("pro_image") String image,
			@RequestParam("pro_price") float price, @RequestParam("cat_id") int id) {
		try {
			product1.setCat_id(id);
			product1.setPro_name(name);
			product1.setPro_image(null);
			product1.setPro_price(price);

			product.insert(product1);

		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error submit insert!!");
		}
		return "redirect:/admin/listproduct";
	}

	@PostMapping("/delete_product")
	public String DeleteProduct(Model model, @RequestParam("idproduct") String idproduct) {
		// ---Delete Product----
		if (idproduct != null) {
			long newparlong;
			newparlong = Long.valueOf(idproduct);
			product.deleteById(newparlong);
			Product template = product.findById(newparlong);
			String imagetemp = template.getPro_image();
			File imageFile = new File("src/main/resources/static/asset/admin/assets/img/product/" + imagetemp);
			if (imageFile.exists()) {
				imageFile.delete();
			}
		}

		return "redirect:/admin/listproduct";
	}

	@RequestMapping(value = "/update_product", method = RequestMethod.GET)
	public String updateproduct(Model model, @RequestParam("id1") int id1) {

		Product item = product.findById(id1);
		model.addAttribute("id", item.getPro_id());
		model.addAttribute("name", item.getPro_name());
		model.addAttribute("price", item.getPro_price());
		model.addAttribute("category", item.getCat_id());
		List<Category> cate = cateRepo.findAll();
		model.addAttribute("listCate", cate);
		return "admin/product/update_product";

	}

	@RequestMapping(value = "/update_product_edit", method = RequestMethod.POST)
	public String update_product_edit(Model model, @RequestParam("id") int id1, @RequestParam("name") String name,
			@RequestParam("price") float price, @RequestParam("cat_id") int cat_id, Product prod) {

		try {
			prod.setPro_id(id1);
			prod.setPro_name(name);
			prod.setPro_price(price);
			prod.setPro_image(null);
			prod.setCat_id(cat_id);

			product.update(prod);

		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error submit update!!");
		}

		return "redirect:/admin/listproduct";

	}

	@RequestMapping(value = "/liststorage", method = RequestMethod.GET)
	public String listStorage(Model model) {
		try {
			List<StorageView> sto = storage.findAll1();
			model.addAttribute("listStorage", sto);
			return "admin/storage/list_storage";
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("list error!!");
		}
	}

	@RequestMapping(value = "/insertstorage", method = RequestMethod.GET)
	public String insertStorage(Model model) {
		List<Product> pro = product.findAll();
		model.addAttribute("listProduct", pro);
		return "admin/storage/insert_storage";
	}

	@PostMapping("/delete_storage")
	public String DeleteStorage(Model model, @RequestParam("idstorage") String idstorage) {
		if (idstorage != null) {
			long newparlong;
			newparlong = Long.valueOf(idstorage);
			storage.deleteById(newparlong);
		}

		return "redirect:/admin/liststorage";
	}

	@RequestMapping(value = "/insertStosubmit", method = RequestMethod.POST)
	public String InsertSto(@RequestParam("pro_id") int id, Storage storage1, Model model,
			@RequestParam("sto_price") float price, @RequestParam("quantity") int quantity) {
		try {
			storage1.setPro_id(id);
			storage1.setSto_price(price);
			storage1.setSto_quantity(quantity);
			storage.insert(storage1);

		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error submit insert!!");
		}
		return "redirect:/admin/liststorage";
	}

	@RequestMapping(value = "/update_storage", method = RequestMethod.GET)
	public String updatestorage(Model model, @RequestParam("id2") int id) {

		Storage item = storage.findById(id);
		model.addAttribute("sto_id", item.getSto_id());
		model.addAttribute("pro_id", item.getPro_id());
		model.addAttribute("price", item.getSto_price());
		model.addAttribute("quantity", item.getSto_quantity());
		List<Product> pro = product.findAll();
		model.addAttribute("listProduct", pro);

		return "admin/storage/update_storage";

	}

	@RequestMapping(value = "/update_storage_edit", method = RequestMethod.POST)
	public String update_storage_edit(Model model, @RequestParam("sto_id") int ID1, @RequestParam("pro_id") int ID2,
			@RequestParam("price") float price, @RequestParam("quantity") int quantity, Storage sto) {

		try {
			sto.setSto_id(ID1);
			sto.setPro_id(ID2);
			sto.setSto_price(price);
			sto.setSto_quantity(quantity);
			storage.update(sto);

		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error submit update!!");
		}

		return "redirect:/admin/liststorage";

	}


	@RequestMapping(value = "/alltable", method = RequestMethod.GET)
	public String showAllTable(Model model) {
		try {
			Iterable<Order> ord = ord1.findAll1();
			model.addAttribute("listOrder", ord);

			Iterable<Orderdetails> ord_details = ord_det1.findAll1();
			model.addAttribute("listOrderDetails", ord_details);

			Iterable<Review> review = rev1.findAll1();
			model.addAttribute("listreview", review);

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
			
			Iterable<ProductView> listprod = product.findAll1();
			model.addAttribute("listprod", listprod);

			Iterable<StorageView> liststo = stoRepo.findAll1();
			model.addAttribute("liststo", liststo);
			
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

	@PostMapping("/deleteOrd")
		public String DeleteOrder(Model model, @RequestParam("idorder") String idorder) {

			// ---Delete Recipe----
			if (idorder != null) {
				long newparlong1;
				newparlong1 = Long.valueOf(idorder);
				ord1.deleteById(newparlong1);
			}

			return "redirect:/admin/table";
		}
	
	@PostMapping("/deleteOrdedt")
		public String DeleteOrderDetails(Model model, @RequestParam("idorderdt") String idorderdt) {

			// ---Delete Recipe----
			if (idorderdt != null) {
				long newparlong1;
				newparlong1 = Long.valueOf(idorderdt);
				ord_det1.deleteById(newparlong1);
			}

			return "redirect:/admin/table";
		}

	@PostMapping("/deleteReview")
		public String deleteReview(Model model, @RequestParam("idrev") String idrev) {

			System.out.println("id review controller = " + idrev);
			// ---Delete Recipe----
			if (idrev != null) {
				long newparlong1;
				newparlong1 = Long.valueOf(idrev);
				rev1.deleteById(newparlong1);
			}

			return "redirect:/admin/table";
		}

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

	@RequestMapping(value = "/insert1", method = RequestMethod.GET)
		public String insertorder(Model model) {
			Iterable<UserDB> usr = usr1.findAll1();
			model.addAttribute("listUsrid", usr);
			return "admin/order/insert_order";

		if (id != null) {
			int parseId;
			parseId = Integer.valueOf(id);
			cateRepo.deleteById(parseId);
		}
	}
	
		@RequestMapping(value = "/insert1submit", method = RequestMethod.POST)
		public String InsertOrder(Model model, @RequestParam("usrid") long userid, Order Order) {
			try {
				Order.setUsr_id(userid);
				//Order.setUsr_id1(userid);
				ord1.insert(Order);
				//Show thông tin bảng order sau khi insert
				Iterable<Order> ord = ord1.findAll1();
				model.addAttribute("listOrder", ord);
				//show thông tin bảng ord_details sau khi insert
				Iterable<Orderdetails> ord_details = ord_det1.findAll1();
				model.addAttribute("listOrderDetails", ord_details);
				return "redirect:/admin/table";
			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error value insert!!");
			}
		}

		@RequestMapping(value = "/insertorddetail", method = RequestMethod.GET)
		public String insertorderdetails(Model model) {
			Iterable<Order> ord = ord1.findAll1();
			model.addAttribute("listOrder", ord);
	
			Iterable<StorageView> sto = sto1.findAll1();
			model.addAttribute("listSto", sto);
			return "admin/order_details/insert_order_details";

		}

		// ----------------------------------------------------------
		@RequestMapping(value = "/insert2submit", method = RequestMethod.POST)
		public String insertrec(Model model, @RequestParam("ordid") long ordid, @RequestParam("stoid") long stoid,
				@RequestParam("quantity") int quantity, @RequestParam("importprice") float importprice,
				@RequestParam("exportprice") float exportprice, Orderdetails Order_dt) {

			try {
				Order_dt.setOrd_id(ordid);
				Order_dt.setSto_id(stoid);
				Order_dt.setOdt_quantity(quantity);
				Order_dt.setOdt_importPrice(importprice);
				Order_dt.setOdt_exportPrice(exportprice);

				ord_det1.insert(Order_dt);
			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error submit insert!!");
			}
			Iterable<Order> ord = ord1.findAll1();
			model.addAttribute("listOrder", ord);

			Iterable<Orderdetails> ord_details = ord_det1.findAll1();
			model.addAttribute("listOrderDetails", ord_details);

			return "redirect:/admin/table";

		}

		

		@RequestMapping(value = "/insert3submit", method = RequestMethod.POST)
		public String Insertreviewpost(Model model, @RequestParam("userid") long userid, @RequestParam("odtid") long odtid,
				@RequestParam("proid") long proid, @RequestParam("revcon") String revcon, Review Review) {
			try {

				Review.setUsr_id(userid);
				Review.setOdt_id(odtid);
				Review.setPro_id(proid);
				Review.setRev_content(revcon);

				rev1.insert(Review);

				Iterable<Order> ord = ord1.findAll1();
				model.addAttribute("listOrder", ord);

				Iterable<Orderdetails> ord_details = ord_det1.findAll1();
				model.addAttribute("listOrderDetails", ord_details);

				Iterable<Review> review = rev1.findAll1();
				model.addAttribute("listreview", review);

				return "redirect:/admin/table";
			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error value insert!!");
			}

		}

		

	//SHOW INSERT CART
	@RequestMapping(value = "/insertCart", method = RequestMethod.GET)
	public String insertcart(Model model) {
			model.addAttribute("Cart", car1.findAll1());
			return "admin/cart/insertCart";

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
			return "admin/cartItem/insertCartItem";
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

		@RequestMapping(value = "/searchInfo", method = RequestMethod.POST)
		//@PostMapping("/searchInfo")
		public String Showinfo(@RequestParam("info") String info,Model model) {
			
			if(info.equals("table order")) {
				Iterable<Order> ord = ord1.findAll1();
				model.addAttribute("listOrder", ord);
				return "admin/order/tableBasic";
			}else if(info.equals("table order details") ) {
				Iterable<Orderdetails> ord_details = ord_det1.findAll1();
				model.addAttribute("listOrderDetails", ord_details);
				return "admin/order_details/tableBasic";
			}else if(info.equals("table all")) {
				return "redirect:/admin/table";
			}else if(info.equals( "table review")){
				Iterable<Review> review = rev1.findAll1();
				model.addAttribute("listreview", review);
				return "admin/review/tableBasic";
			}
			return "admin/dashboard";
		
		}

	// SHOW INSERT FORM
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
	public String insertRecipeDetails(Model model, @PathVariable(name = "idr") int idr, @RequestParam("quantity") String quantity, RecipeDetailsView rdt) {
//		rdt.setRecipe_id(idr);
//		rdt.setQuantity(quantity);
//		rdt.setProduct_id(idr);
		
		return "admin/recipe/adddetails";
	}

	@RequestMapping(value = "/insert_review", method = RequestMethod.GET)
		public String insertreview(Model model) {

			return "admin/review/insert_review";

		}

		

	// UPDATE ACTION

	/***
		 * 
		 * @param model order
		 * @param id    order
		 * @return update table order
		 */
		@RequestMapping(value = "/update_order", method = RequestMethod.GET)
		public String updateorder(Model model, @RequestParam("id1") long id1) {

			Order item = ord1.findById(id1);

			model.addAttribute("id", item.getOrd_id());
			model.addAttribute("usrid", item.getUsr_id());
			model.addAttribute("total", item.getOrd_totalAmount());
			model.addAttribute("date", item.getOrd_date());

			return "admin/order/update_order";

		}

		@RequestMapping(value = "/update_order_edit", method = RequestMethod.POST)
		public String update_order_edit(Model model, @RequestParam("id") long id1, @RequestParam("userid") long userid,
				@RequestParam("total") float total, @RequestParam("date") String date1, Order Order) {

			try {

				Order item = ord1.findById(id1);

				SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");

				java.util.Date parsedDate1 = format1.parse(date1);

				Date sqlDate = new Date(parsedDate1.getTime());

				Order.setUsr_id(userid);
				Order.setOrd_totalAmount(total);
				Order.setOrd_date(sqlDate);
				Order.setOrd_id(id1);

				ord1.update(Order);

			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error submit update!!");
			}

			return "redirect:/admin/table";

		}

		/***
		 * 
		 * @param model order_details
		 * @param id    order_details
		 * @return update table order_details
		 */
		@RequestMapping(value = "/update_order_details", method = RequestMethod.GET)
		public String updateorder_details(Model model, @RequestParam("id1") long id1) {

			Orderdetails item = ord_det1.findById(id1);

			model.addAttribute("ordid", item.getOrd_id());
			model.addAttribute("stoid", item.getSto_id());
			model.addAttribute("odtid", item.getOdt_id());
			model.addAttribute("quantity", item.getOdt_quantity());
			model.addAttribute("imp", item.getOdt_importPrice());
			model.addAttribute("exp", item.getOdt_exportPrice());

			return "admin/order_details/update_order_details";

		}

		@RequestMapping(value = "/update_order_details_edit", method = RequestMethod.POST)
		public String update_order_details_edit(Model model, @RequestParam("id") long id,
				@RequestParam("ordid") long ordid, @RequestParam("stoid") long stoid,
				@RequestParam("quantity") int quantity, @RequestParam("imp") float imp, @RequestParam("exp") float exp,
				Orderdetails Orderdetails) {

			try {

				Orderdetails.setOrd_id(ordid);
				Orderdetails.setSto_id(stoid);
				Orderdetails.setOdt_quantity(quantity);
				Orderdetails.setOdt_importPrice(imp);
				Orderdetails.setOdt_exportPrice(exp);
				Orderdetails.setOdt_id(id);
				ord_det1.update(Orderdetails);
				 
			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error submit update!!");
			}

			return "redirect:/admin/table";
		}

		/***
		 * 
		 * @param model review
		 * @param id    review
		 * @return update table review
		 */
		@RequestMapping(value = "/update_review", method = RequestMethod.GET)
		public String update_review(Model model, @RequestParam("idrev") long id1) {

			Review item = rev1.findById(id1);
			
			model.addAttribute("id", item.getRev_id());
			model.addAttribute("usrid", item.getUsr_id());
			model.addAttribute("odtid", item.getOdt_id());
			model.addAttribute("proid", item.getPro_id());
			model.addAttribute("rev_con", item.getRev_content());
			return "admin/review/update_review";

		}

		@RequestMapping(value = "/update_review_edit", method = RequestMethod.POST)
		public String update_review_edit(Model model,
				@RequestParam("id") long revid, @RequestParam("userid") long userid,
				@RequestParam("orderdt") long orderdt, @RequestParam("proid") long proid, 
				@RequestParam("review") String Revcontent,Review Review) {
			try {
				
				System.out.println(userid +"---"+orderdt +"---"+proid+"---"+Revcontent +"---"+revid+"---");			
				Review.setUsr_id(userid);
				Review.setOdt_id(orderdt);
				Review.setPro_id(proid);
				Review.setRev_content(Revcontent);
				Review.setRev_id(revid);
				rev1.update(Review);

			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error submit update!!");
			}

			return "redirect:/admin/table";

		}

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
			return "admin/user/updateUser";

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
			return "admin/cart/updateCart";
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
			return "admin/cartItem/updateCartItem";

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
