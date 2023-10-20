package com.togarpic.controller;

<<<<<<< Updated upstream
=======
import java.io.File;
>>>>>>> Stashed changes
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
<<<<<<< Updated upstream
=======
import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
<<<<<<< Updated upstream

import com.togarpic.model.Product;
import com.togarpic.model.Storage;
import com.togarpic.repository.ProductRepository;
import com.togarpic.repository.StorageRepository;
=======
import com.togarpic.model.*;
import com.togarpic.repository.*;
>>>>>>> Stashed changes

@Controller
@RequestMapping("/admin")
public class AdminController implements WebMvcConfigurer {
	@Autowired
	private ProductRepository product;
	@Autowired
	private StorageRepository storage;
<<<<<<< Updated upstream

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String showDashboard() {

	@Autowired
	private RecipeRepository reciRepo;

	@Autowired
	private CategoryRepository cateRepo;

	@Autowired
	private RecipeDetailsRepository rdetRepo;
=======
	@Autowired
	private RecipeRepository reciRepo;

	@Autowired
	private CategoryRepository cateRepo;

	@Autowired
	private RecipeDetailsRepository rdetRepo;

	@Autowired
	private StorageRepository stoRepo;
>>>>>>> Stashed changes

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showIndex() {

		return "admin/dashboard";
	}

	@RequestMapping(value = "/listproduct", method = RequestMethod.GET)
<<<<<<< Updated upstream
	public String listProduct(Model model) {
		try {
			List<Product> pro = product.findAll();
=======
	public String listProductView(Model model) {
		try {
			List<ProductView> pro = product.findAll1();
>>>>>>> Stashed changes
			model.addAttribute("listProduct", pro);
			return "admin/product/list_product";
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("list error!!");
		}
	}

	@RequestMapping(value = "/insertproduct", method = RequestMethod.GET)
	public String insertProduct(Model model) {
<<<<<<< Updated upstream
		return "admin/product/insert_product";
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
		// Iterable<Product> pro = product.findAll();
		// model.addAttribute("listProduct", pro);
		return "redirect:/admin/listproduct";
	}

	@PostMapping("/delete_product")
	public String DeleteProduct(Model model, @RequestParam("idproduct") String idproduct) {
		// ---Delete Product----
		System.out.println("idproduct = " + idproduct);

		if (idproduct != null) {
			long newparlong;
			newparlong = Long.valueOf(idproduct);
			product.deleteById(newparlong);
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
			List<Storage> sto = storage.findAll();
			model.addAttribute("listStorage", sto);
			return "admin/storage/list_storage";
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("list error!!");
		}
	}

	@RequestMapping(value = "/insertstorage", method = RequestMethod.GET)
	public String insertStorage(Model model) {
		return "admin/storage/insert_storage";
	}

	@PostMapping("/delete_storage")
	public String DeleteStorage(Model model, @RequestParam("idstorage") String idstorage) {
		if (idstorage != null) {
			long newparlong;
			newparlong = Long.valueOf(idstorage);
			storage.deleteById(newparlong);
		}

=======
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
		System.out.println("idproduct = " + idproduct);

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

>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
=======
		List<Product> pro = product.findAll();
		model.addAttribute("listProduct", pro);
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======
	}
>>>>>>> Stashed changes

	@RequestMapping(value = "/alltable", method = RequestMethod.GET)
	public String showAllTable(Model model) {
		Iterable<Recipe> listreci = reciRepo.findAll();
		model.addAttribute("listreci", listreci);

		Iterable<Category> listcate = cateRepo.findAll();
		model.addAttribute("listcate", listcate);

<<<<<<< Updated upstream
		Iterable<RecipeDetails> listrdet = rdetRepo.findAll();
		model.addAttribute("listrdet", listrdet);

=======
		Iterable<com.togarpic.model.recipedetails.RecipeDetails> listrdet = rdetRepo.findAll();
		model.addAttribute("listrdet", listrdet);

		Iterable<ProductView> listprod = product.findAll1();
		model.addAttribute("listprod", listprod);

		Iterable<StorageView> liststo = stoRepo.findAll1();
		model.addAttribute("liststo", liststo);
>>>>>>> Stashed changes
		return "admin/table";
	}

	@RequestMapping(value = "/listcategory", method = RequestMethod.GET)
	public String showCategoryList(Model model) {
		Iterable<Category> listcate = cateRepo.findAll();
		model.addAttribute("listcate", listcate);
		return "admin/category/list";
	}

	@RequestMapping(value = "/listrecipe", method = RequestMethod.GET)
	public String showAllRecipe(Model model) {
		Iterable<Recipe> listreci = reciRepo.findAll();
		model.addAttribute("listreci", listreci);
		return "admin/recipe/list";
	}

	// VIEW ACTION

	@RequestMapping(value = "/viewmore/{id}")
	public String viewMoreRecipe(@PathVariable int id) {
		return null;
	}

	// DELETE ACTION

	@RequestMapping(value = "/delCategory/{id}", method = RequestMethod.GET)
	public String deleteCategory(Model model, @PathVariable Integer id) {

		if (id != null) {
			int parseId;
			parseId = Integer.valueOf(id);
			cateRepo.deleteById(parseId);
		}

		return "redirect:/admin/listcategory";
	}

	// INSERT ACTION

<<<<<<< Updated upstream
=======
	@RequestMapping(value = "/insert1", method = RequestMethod.GET)
		public String insertorder(Model model) {
			Iterable<UserDB> usr = usr1.findAll1();
			model.addAttribute("listUsrid", usr);
			return "admin/order/insert_order";

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
>>>>>>> Stashed changes
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

	// UPDATE ACTION

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
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
			category.setCat_name(title);
			category.setId(id);
			cateRepo.update(category);

			return "redirect:/admin/listcategory";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error value insert!!");
		}
	}
}
