package com.togarpic.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.togarpic.repository.*;
import com.togarpic.model.*;

@Controller

@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserRepository usr1;

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
	private OrderDetailsRepository ord_det1;

	@Autowired
	private ReviewRepository rev1;

	@Autowired
	private OrderRepository ord1;

	/*----*/

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showDashboard(HttpServletRequest request, HttpServletResponse response) {

		String roles = (String) request.getSession().getAttribute("roles");
		if (roles != null && roles.equals("ADMIN")) {
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "0");
			return "admin/dashboard";
		} else if (roles != null && roles.equals("USER")) {
			return "403";
		}

		return "redirect:/login";
	}

	@RequestMapping(value = "/alltable", method = RequestMethod.GET)
	public String showAllTable(Model model, HttpServletRequest request) {

		String roles = (String) request.getSession().getAttribute("roles");
		if (roles != null && roles.equals("ADMIN")) {
			try {
				Iterable<User> usr = usr1.findAll();
				model.addAttribute("listUser", usr);

				Iterable<Order> ord = ord1.findAllTop();
				model.addAttribute("listOrder", ord);

				Iterable<Orderdetails> ord_details = ord_det1.findAllTop();
				model.addAttribute("listOrderDetails", ord_details);

				Iterable<Review> review = rev1.findAllTop();
				model.addAttribute("listreview", review);

				Iterable<Recipe> listreci = reciRepo.findAll();
				model.addAttribute("listreci", listreci);

				Iterable<Category> listcate = cateRepo.findAll();
				model.addAttribute("listcate", listcate);

				Iterable<ProductView> listprod = product.findAll1();
				model.addAttribute("listprod", listprod);

				Iterable<StorageView> liststo = storage.findAll1();
				model.addAttribute("liststo", liststo);

				return "admin/table";

			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("list error!!");
			}
		} else if (roles != null && roles.equals("USER")) {
			return "403";
		}
		return "redirect:/login";
	}

	/* USER TABLE */

	@RequestMapping(value = "/listUser", method = RequestMethod.GET)
	public String showUserList(Model model) {
		Iterable<User> usr = usr1.findAll();
		model.addAttribute("listUser", usr);
		return "admin/user/listUser";
	}

	@RequestMapping(value = "/insertUser", method = RequestMethod.GET)
	public String insertUser(Model model) {
		try {
			MyUploadForm myUploadForm2 = new MyUploadForm();
			model.addAttribute("myUploadForm", myUploadForm2);
			return "/admin/user/insertUser";
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error in page insert!!");
		}
	}

	@RequestMapping(value = "/insertUserSubmit", method = RequestMethod.POST)
	public String InsertUserSubmit(Model model, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("telephone") String telephone,
			@RequestParam("email") String email, @RequestParam("image") String image,
			@RequestParam("password") String password, @RequestParam("role") String role,
			@RequestParam("fileDatas") MultipartFile file1, MyUploadForm myUploadForm,
			@ModelAttribute("myUploadForm") MyUploadForm myUploadForm1, HttpServletRequest request, User user) {
		try {
			String temp = encryptPassword(password);
			String rol = "ADMIN";
			user.setUsr_lastName(lastName);
			user.setUsr_telephone(telephone);
			user.setUsr_email(email);
			user.setUsr_image(image);
			user.setUsr_password(temp);
			user.setUsr_role(rol);

			usr1.insert(user);

			Path staticPath = Paths.get("src", "main", "resources", "static", "image");
			String usr1 = staticPath.toString();
			System.out.println(" staticPath:  " + usr1 + " === ");
			File uploadRootDir1 = new File(usr1);
			if (!uploadRootDir1.exists()) {
				uploadRootDir1.mkdirs();
			}
			MultipartFile[] fileDatas = myUploadForm.getFileDatas();
			List<File> uploadedFiles = new ArrayList<File>();
			for (MultipartFile fileData : fileDatas) {
				// Lấy tên ảnh
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

				} catch (Exception ex) {

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
		model.addAttribute("listUser", usr);
		return "redirect:/admin/alltable";
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.GET)
	public String updateuser(Model model, @RequestParam("id1") long id) {
		User item = usr1.findById(id);

		model.addAttribute("usr_id", item.getUsr_id());
		model.addAttribute("firstName", item.getUsr_firstName());
		model.addAttribute("lastName", item.getUsr_lastName());
		model.addAttribute("telephone", item.getUsr_telephone());
		model.addAttribute("email", item.getUsr_email());
		model.addAttribute("image", item.getUsr_image());
		model.addAttribute("password", item.getUsr_password());
		model.addAttribute("role", item.getUsr_role());
		User template = usr1.findById(id);
		String tem = template.getUsr_image();
		model.addAttribute("image", tem);
		// xóa hình đã khi thay đổi hình khác
		File imageFile = new File("src/main/resources/static/image/" + tem);
		if (imageFile.exists()) {
			imageFile.delete();
		}
		MyUploadForm myUploadForm2 = new MyUploadForm();
		model.addAttribute("myUploadForm", myUploadForm2);
		return "admin/user/updateUser";
	}

	@RequestMapping(value = "/updateUserEdit", method = RequestMethod.POST)
	public String update_user_edit(Model model, @RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName, @RequestParam("telephone") String telephone,
			@RequestParam("email") String email, @RequestParam("image") String image,
			@RequestParam("password") String password, @RequestParam("role") String role,
			@RequestParam("fileDatas") MultipartFile file1, MyUploadForm myUploadForm,
			@ModelAttribute("myUploadForm") MyUploadForm myUploadForm1, HttpServletRequest request, User user) {
		try {
			String temp = encryptPassword(password);
			user.setUsr_firstName(firstName);
			user.setUsr_lastName(lastName);
			user.setUsr_telephone(telephone);
			user.setUsr_email(email);
			user.setUsr_image(image);
			user.setUsr_password(temp);
			user.setUsr_role(role);

			usr1.update(user);

			Path staticPath = Paths.get("src", "main", "resources", "static", "image");
			String usr1 = staticPath.toString();
			System.out.println(" staticPath:  " + usr1 + " === ");
			File uploadRootDir1 = new File(usr1);
			if (!uploadRootDir1.exists()) {
				uploadRootDir1.mkdirs();
			}
			MultipartFile[] fileDatas = myUploadForm.getFileDatas();
			List<File> uploadedFiles = new ArrayList<File>();
			for (MultipartFile fileData : fileDatas) {
				// Lấy tên ảnh
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

				} catch (Exception ex) {

				}

			}

		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error value insert!!");
		}
		MultipartFile[] fileDatas = myUploadForm.getFileDatas();

		System.out.println(" ====== file Datas" + fileDatas + "======");
		Iterable<User> usr = usr1.findAll();
		model.addAttribute("listUser", usr);
		return "redirect:/admin/alltable";
	}

	public static String encryptPassword(String password) {
		String salt = BCrypt.gensalt(12);
		return BCrypt.hashpw(password, salt);
	}

	@PostMapping("/delete")
	public String DeleteUser(Model model, @RequestParam("id1") String id1) {
		int newparint;
		newparint = Integer.parseInt(id1);
		User template = usr1.findById(newparint);
		usr1.deleteById(newparint);
		// lấy đường dẫn hình ảnh theo id được chọn
		String imagetemp = template.getUsr_image();
		File imageFile = new File("src/main/resources/static/image/" + imagetemp);
		if (imageFile.exists()) {
			imageFile.delete();
		}
		return "redirect:/admin/alltable";
	}

	@GetMapping("/status")
	public String toggleStatus(@RequestParam("action") String action, @RequestParam("id") long id,
			HttpServletRequest request) {
		if (action.equals("open")) {
			usr1.openStatus(id);
		} else if (action.equals("close")) {
			usr1.closeStatus(id);
		}
		return "redirect:/admin/alltable";
	}

	@RequestMapping(value = "/viewUserOrderDT/{id}", method = RequestMethod.GET)
	public String viewUserorder_detail(Model model, @PathVariable(name = "id") int id) {
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Error view order details!!");
		}

		long pase = (long) id;

		Iterable<Orderdetails> ordview = (Iterable<Orderdetails>) ord_det1.findview(pase);
		model.addAttribute("listUserOrderDT", ordview);
		return "admin/user/UserOrderDT";
	}

	@RequestMapping(value = "/viewUserOrder/{id}", method = RequestMethod.GET)
	public String viewUserorder(Model model, @PathVariable(name = "id") int id) {
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Error view order details!!");
		}

		long pase = (long) id;

		Iterable<Order1> ordview = (Iterable<Order1>) ord1.findByIdUser(pase);
		model.addAttribute("listUserOrder", ordview);
		return "admin/user/userOrder";
	}

	/* USER TABLE */

	/* ORDER & ORDER DETAILS TABLE */

	@GetMapping("/table_order")
	public String tableOrder(Model model,HttpServletRequest request) {
		String roles = (String) request.getSession().getAttribute("roles");
	    if (roles != null && roles.equals("ADMIN")) {
	        Iterable<Order> ord = ord1.findAll1();	 			
	 		List<Order> temp = ord1.findAll1();
			String t = temp.toString();
			for(Order usr: temp) {
				if(usr.getOrd_status()==0) {
					model.addAttribute("Yes1","Xác nhận");
					model.addAttribute("No1","Hủy");
					model.addAttribute("listOrder", ord);	
				}else if(usr.getOrd_status()>=1) {
					model.addAttribute("Yes","Xác nhận");
					model.addAttribute("No","Hủy");
					model.addAttribute("listOrder", ord);	
				}
				
			}
	 		return"admin/order/list_order";
	 		
	    }else if(roles != null && roles.equals("USER")) {
	        return "403";      	 
	    }
		return "redirect:/login";
	}

	@GetMapping("/updatestatus/{idorder}/{status}")
	public String updateStatus(@PathVariable long idorder, @PathVariable int status ,Model model) {
		try {
			long tmp_orderid = idorder;
			int tmp_status = status;
			ord1.updateStatus(tmp_status , tmp_orderid);	
			
			return"redirect:/admin/table_order";
		 }catch(Exception ex) {
			 ex.printStackTrace();
			 throw new RuntimeException("Error view update status!!");
		 }
		 
		
		
	}
	@GetMapping("/Cancelstatus/{idorder}/{status}")
	public String updateCancelStatus(@PathVariable long idorder,Model model ) {
		try {
			long tmp_orderid = idorder;
			int tmp_status = 0;
			ord1.CancelStatus(tmp_status, tmp_orderid);		
			
			 return"redirect:/admin/table_order";
		 }catch(Exception ex) {
			 ex.printStackTrace();
			 throw new RuntimeException("Error view update status!!");
		 }
		 
		
		
	}
	@RequestMapping(value="/vieworder/{id}",method = RequestMethod.GET)
	public String vieworder_detail(Model model,@PathVariable(name="id")int id,HttpServletRequest request) {
		try {

			long tmp_order = Long.parseLong(orderId);
			int tmp_status = Integer.parseInt(status);
			ord1.updateStatus(tmp_status, tmp_order);
			if (redict.equals("redict")) {
				return "redirect:/admin/table_order";
			}
			return "redirect:/admin/alltable";
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Error view update status!!");
		}

	}

	@RequestMapping(value = "/vieworder/{id}", method = RequestMethod.GET)
	public String vieworder_detail(Model model, @PathVariable(name = "id") int id) {
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Error view order details!!");
		}
		long pase = (long) id;
		request.getSession().setAttribute("idorder1", pase);
		Iterable<Orderdetails> ordview = (Iterable<Orderdetails>) ord_det1.findview(pase);
		model.addAttribute("listview", ordview);
		return "admin/order_details/Databases";
	}

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

	@RequestMapping(value = "/insert1", method = RequestMethod.GET)
	public String Insertorder(Model model, HttpServletRequest request) {
		String roles = (String) request.getSession().getAttribute("roles");
		if (roles != null && roles.equals("ADMIN")) {
			Iterable<Review> usr1 = rev1.findAllUser();
			model.addAttribute("listUsrid", usr1);

			return "admin/order/insert_order";

		} else if (roles != null && roles.equals("USER")) {
			return "403";
		}

		return "redirect:/login";

	}

	@RequestMapping(value = "/insert1submit", method = RequestMethod.POST)
	public String SubmitOrder(Model model, @RequestParam("usrid") long userid, @RequestParam("address") String address,
			Order Order, HttpServletRequest request) {
		String roles = (String) request.getSession().getAttribute("roles");
		if (roles != null && roles.equals("ADMIN")) {
			try {

				Order.setUsr_id(userid);
				Order.setOrd_address(address);
				ord1.insert(Order);
				// Show thông tin bảng order sau khi insert
				Iterable<Order> ord = ord1.findAll1();
				model.addAttribute("listOrder", ord);
				// show thông tin bảng ord_details sau khi insert
				Iterable<Orderdetails> ord_details = ord_det1.findAll1();
				model.addAttribute("listOrderDetails", ord_details);
				return "redirect:/admin/table_order";
			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error value insert!!");
			}
		} else if (roles != null && roles.equals("USER")) {
			return "403";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/update_order", method = RequestMethod.GET)
	public String updateorder(Model model, @RequestParam("id1") long id1, HttpServletRequest request) {
		String roles = (String) request.getSession().getAttribute("roles");
		if (roles != null && roles.equals("ADMIN")) {
			Order item = ord1.findById(id1);
			model.addAttribute("id", item.getOrd_id());
			model.addAttribute("usrid", item.getUsr_id());
			model.addAttribute("total", item.getOrd_totalAmount());
			model.addAttribute("date", item.getOrd_date());

			return "admin/order/update_order";

		} else if (roles != null && roles.equals("USER")) {
			return "403";
		}
		return "redirect:/login";

	}

	@RequestMapping(value = "/update_order_edit", method = RequestMethod.POST)
	public String update_order_edit(Model model, @RequestParam("id") long id1, @RequestParam("userid") long userid,
			@RequestParam("total") float total, @RequestParam("date") String date1, Order Order,
			HttpServletRequest request) {
		String roles = (String) request.getSession().getAttribute("roles");
		if (roles != null && roles.equals("ADMIN")) {
			try {

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
			return "redirect:/admin/alltable";
		} else if (roles != null && roles.equals("USER")) {
			return "403";
		}
		return "redirect:/login";

	}

	@RequestMapping(value = "/listorder", method = RequestMethod.GET)
	public String showOrderList(HttpServletRequest request) {
		String roles = (String) request.getSession().getAttribute("roles");
        if (roles != null && roles.equals("ADMIN")) {
            return "admin/order/list_order";
        }else if(roles != null && roles.equals("USER")) {
        	return "403";      	 
        }
		
		return "redirect:/login";
	}

	@RequestMapping("/table_orderdt")
	public String tableOrder_details(Model model, HttpServletRequest request) {

		String roles = (String) request.getSession().getAttribute("roles");
		if (roles != null && roles.equals("ADMIN")) {
			Iterable<Orderdetails> ord_details = ord_det1.findAll1();
			model.addAttribute("listOrderDetails", ord_details);
			return "admin/order_details/Databases";

		} else if (roles != null && roles.equals("USER")) {
			return "403";
		}
		return "redirect:/login";
	}

	@PostMapping("/deleteOrdedt")
	public String DeleteOrderDetails(Model model, @RequestParam("idorderdt") String idorderdt,
			HttpServletRequest request) {
		String roles = (String) request.getSession().getAttribute("roles");
		if (roles != null && roles.equals("ADMIN")) {
			if (idorderdt != null) {
				long newparlong1;
				newparlong1 = Long.valueOf(idorderdt);
				ord_det1.deleteById(newparlong1);
			}

			return "redirect:/admin/table";

		} else if (roles != null && roles.equals("USER")) {
			return "403";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/insertorddetail", method = RequestMethod.GET)
	public String Insertorderdetails(Model model) {
		Iterable<Order> ord = ord1.findAll1();
		model.addAttribute("listOrder", ord);

		Iterable<Storage> sto = rev1.findAllSto();
		model.addAttribute("listSto", sto);
		
		Iterable<Storage1> sto1 = ord_det1.findAllSto() ;
		model.addAttribute("listSto1", sto1);

	
		 return "admin/order_details/insert_order_details";
	}

	
		@PostMapping("/processData")
	    @ResponseBody
	    public String getPriceFormStatus(@RequestBody String data) throws JsonMappingException, JsonProcessingException {
	        // Xử lý dữ liệu nhận được từ Ajax
			ObjectMapper objectMapper = new ObjectMapper();
			  Map<String, Object> requestData = objectMapper.readValue(data, Map.class);
			   String selectedOption = (String) requestData.get("selectedOption");
		        // Xử lý dữ liệu nhận được từ Ajax
		        long tmp_id1 = Long.parseLong(selectedOption);
			Storage1 price = ord_det1.StorageFind(tmp_id1);
			String result =	String.valueOf(price.getPro_price());
			String result1= String.valueOf(price.getSto_price());
			String result2 = result + "===" +result1;
			return result2;
	   
	    }
	@RequestMapping(value = "/insert4submit", method = RequestMethod.POST)
	public String SubmitOrderDetails(Model model, @RequestParam("stoid") long stoid,
		@RequestParam("quantity") int quantity, @RequestParam("importprice") float importprice,
		@RequestParam("exportprice") float exportprice, Orderdetails Order_dt,HttpServletRequest request) {
///////////////////////////////
		try {
			long ordid1 = (long) request.getSession().getAttribute("idorder1");
			String tmklid = String.valueOf(ordid1); 
			Order_dt.setOrd_id(ordid1);
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

		return "redirect:/admin/table_order";

	}

	@RequestMapping(value = "/update_order_details", method = RequestMethod.GET)
	public String updateorder_details(Model model, @RequestParam("id1") long id1, HttpServletRequest request) {

		String roles = (String) request.getSession().getAttribute("roles");
		if (roles != null && roles.equals("ADMIN")) {
			Orderdetails item = ord_det1.findById(id1);

			model.addAttribute("ordid", item.getOrd_id());
			model.addAttribute("stoid", item.getSto_id());
			model.addAttribute("odtid", item.getOdt_id());
			model.addAttribute("quantity", item.getOdt_quantity());
			model.addAttribute("imp", item.getOdt_importPrice());
			model.addAttribute("exp", item.getOdt_exportPrice());

			return "admin/order_details/update_order_details";
		} else if (roles != null && roles.equals("USER")) {
			return "403";
		}

		return "redirect:/login";
	}

	@RequestMapping(value = "/update_order_details_edit", method = RequestMethod.POST)
	public String update_order_details_edit(Model model, @RequestParam("id") long id, @RequestParam("ordid") long ordid,
			@RequestParam("stoid") long stoid, @RequestParam("quantity") int quantity, @RequestParam("imp") float imp,
			@RequestParam("exp") float exp, Orderdetails Orderdetails, HttpServletRequest request) {
		String roles = (String) request.getSession().getAttribute("roles");
		if (roles != null && roles.equals("ADMIN")) {
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

			return "redirect:/admin/Databases";
		} else if (roles != null && roles.equals("USER")) {
			return "403";
		}
		return "redirect:/login";

	}

	/* ORDER & ORDER DETAILS TABLE */

	/* REVIEW TABLE */

	@RequestMapping("/table_review")
	public String tableReview(Model model, HttpServletRequest request) {
		String roles = (String) request.getSession().getAttribute("roles");
		if (roles != null && roles.equals("ADMIN")) {
			Iterable<Review> review = rev1.findAll1();
			model.addAttribute("listreview", review);
			return "admin/review/Databases";
		} else if (roles != null && roles.equals("USER")) {
			return "403";
		}
		return "redirect:/login";
	}

	@PostMapping("/deleteReview")
	public String deleteReview(Model model, @RequestParam("idrev") String idrev, HttpServletRequest request) {
		String roles = (String) request.getSession().getAttribute("roles");
		if (roles != null && roles.equals("ADMIN")) {
			// ---Delete Recipe----
			if (idrev != null) {
				long newparlong1;
				newparlong1 = Long.valueOf(idrev);
				rev1.deleteById(newparlong1);
			}
			return "redirect:/admin/table";
		} else if (roles != null && roles.equals("USER")) {
			return "403";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/insert_review", method = RequestMethod.GET)
	public String InsertReview(Model model) {
		// trong day lay ten user va ten product va id cua order_detail

		Iterable<Orderdetails> ord_details = ord_det1.findAll1();
		model.addAttribute("listOrderDetails", ord_details);

		Iterable<Review> user = rev1.findAllUser();
		model.addAttribute("listUser", user);

		Iterable<Product> rev = rev1.findAllPro();
		model.addAttribute("listProduct", rev);

		return "admin/review/insert_review";

	}

	@RequestMapping(value = "/insert3submit", method = RequestMethod.POST)
	public String SubmitReview(Model model, @RequestParam("userid") long userid, @RequestParam("odtid") long odtid,
			@RequestParam("proid") long proid, @RequestParam("revcon") String revcon, Review Review,
			HttpServletRequest request) {

		String roles = (String) request.getSession().getAttribute("roles");
		if (roles != null && roles.equals("ADMIN")) {
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

				return "redirect:/admin/Databases";
			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("Error value insert!!");
			}
		} else if (roles != null && roles.equals("USER")) {
			return "403";
		}
		return "redirect:/login";

	}

	@RequestMapping(value = "/update_review", method = RequestMethod.GET)
	public String update_review(Model model, @RequestParam("idrev") long id1, HttpServletRequest request) {
		String roles = (String) request.getSession().getAttribute("roles");
		if (roles != null && roles.equals("ADMIN")) {
			Review item = rev1.findById(id1);

			model.addAttribute("id", item.getRev_id());
			model.addAttribute("usrid", item.getUsr_id());
			model.addAttribute("odtid", item.getOdt_id());
			model.addAttribute("proid", item.getPro_id());
			model.addAttribute("rev_con", item.getRev_content());
			return "admin/review/update_review";

		} else if (roles != null && roles.equals("USER")) {
			return "403";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/update_review_edit", method = RequestMethod.POST)
	public String update_review_edit(Model model, @RequestParam("id") long revid, @RequestParam("userid") long userid,
			@RequestParam("orderdt") long orderdt, @RequestParam("proid") long proid,
			@RequestParam("review") String Revcontent, Review Review, HttpServletRequest request) {
		String roles = (String) request.getSession().getAttribute("roles");
		if (roles != null && roles.equals("ADMIN")) {
			try {
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
			return "redirect:/admin/alltable";
		} else if (roles != null && roles.equals("USER")) {
			return "403";
		}
		return "redirect:/login";
	}

	/* REVIEW TABLE */

	/* PRODUCT TABLE */

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
			return "admin/product/insert_product";

		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("Error in page insert!!");
		}

	}

	@RequestMapping(value = "/insert2submit", method = RequestMethod.POST)
	public String InsertCategory(@RequestParam("pro_name") String name, Product product1, Model model,
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

	/* PRODUCT TABLE */

	/* STORAGE TABLE */

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
		StorageView item = storage.findById1(id);
		model.addAttribute("sto_id", item.getSto_id());
		model.addAttribute("pro_id", item.getPro_id());
		model.addAttribute("price", item.getSto_price());
		model.addAttribute("quantity", item.getSto_quantity());
		model.addAttribute("pro_name", item.getPro_name());
		List<ProductView> pro = product.findAll1();
		model.addAttribute("listprod", pro);
		return "admin/storage/update_storage";
	}

	@RequestMapping(value = "/update_storage_edit", method = RequestMethod.POST)
	public String update_storage_edit(Model model, @RequestParam("sto_id") int ID1, @RequestParam("product") int ID2,
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

	/* STORAGE TABLE */

	/* CATEGORY TABLE */

	@RequestMapping(value = "/listcategory", method = RequestMethod.GET)
	public String showCategoryList(Model model) {
		Iterable<Category> listcate = cateRepo.findAll();
		model.addAttribute("listcate", listcate);
		return "admin/category/list";
	}

	@RequestMapping(value = "/insCategory", method = RequestMethod.GET)
	public String showInsertCategory() {

		return "admin/category/insert";
	}

	@RequestMapping(value = "/delCategory/{id}", method = RequestMethod.GET)
	public String deleteCategory(Model model, @PathVariable Integer id) {
		if (id != null) {
			int parseId;
			parseId = Integer.valueOf(id);
			cateRepo.deleteById(parseId);
		}
		return "redirect:/admin/listcategory";
	}

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

	/* CATEGORY TABLE */

	/* RECIPE TABLE & RECIPE DETAILS TABLE */

	@RequestMapping(value = "/listrecipe", method = RequestMethod.GET)
	public String showAllRecipe(Model model) {
		Iterable<Recipe> listreci = reciRepo.findAll();
		model.addAttribute("listreci", listreci);
		return "admin/recipe/list";
	}

	@RequestMapping(value = "/viewmore/{id}", method = RequestMethod.GET)
	public String viewMoreRecipe(@PathVariable(name = "id") int id, Model model) {
		int parseId;
		parseId = Integer.valueOf(id);
		Iterable<RecipeDetailsView> listprodreci = rdetRepo.findByIdname(parseId);
		model.addAttribute("listprodreci", listprodreci);
		Recipe reci = reciRepo.findById(parseId);
		model.addAttribute("reci", reci);
		return "admin/recipe/details";
	}

	@RequestMapping(value = "/viewmore/{id}/add", method = RequestMethod.GET)
	public String addMoreRecipe(@PathVariable(name = "id") int id, Model model) {
		int parseId;
		parseId = Integer.valueOf(id);
		Recipe reci = reciRepo.findById(parseId);
		model.addAttribute("reci", reci);
		Iterable<Product> prod = product.findAll();
		model.addAttribute("listprod", prod);

		return "admin/recipe/adddetails";
	}

	@RequestMapping(value = "/viewmore/{id}/add", method = RequestMethod.POST)
	public String addMoreRecipeSubmit(HttpServletRequest request, @PathVariable(name = "id") int idreci, Model model,
			@RequestParam("product") int productid, @RequestParam("quantity") String quantity) {

		int parseId = idreci;
		int parseIdproduct = Integer.valueOf(productid);
		RecipeDetails item = new RecipeDetails();
		item.setRecipe_id(parseId);
		item.setProduct_id(parseIdproduct);
		item.setQuantity(quantity);

		rdetRepo.insert(item);

		Iterable<RecipeDetailsView> listprodreci = rdetRepo.findByIdname(parseId);
		model.addAttribute("listprodreci", listprodreci);

		Recipe reci = reciRepo.findById(parseId);
		model.addAttribute("reci", reci);

		return "redirect:/admin/viewmore/" + parseId;
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

	@RequestMapping(value = "/updRecipe/{id}", method = RequestMethod.GET)
	public String showUpdateRecipe(Model model, Recipe recipe, @PathVariable(name = "id") int id) {

		Recipe reci = reciRepo.findById(id);
		model.addAttribute("reci", reci);

		return "admin/recipe/update";
	}

	@RequestMapping(value = "/delRecipe/{id}", method = RequestMethod.GET)
	public String deleteRecipe(Model model, @PathVariable Integer id) {

		if (id != null) {
			int parseId;
			parseId = Integer.valueOf(id);
			rdetRepo.deleteRecipeRelateByIdRecipe(parseId);
		}

		return "redirect:/admin/listrecipe";
	}

	@RequestMapping(value = "/delProdOfRecipe/{idrec}/{idpro}", method = RequestMethod.GET)
	public String deleteProductOfRecipe(Model model, @PathVariable Integer idrec, @PathVariable int idpro,
			RecipeDetails r) {
		try {
			if (idrec != null) {
				int parseIdrec = Integer.valueOf(idrec);
				r.setRecipe_id(parseIdrec);
				r.setProduct_id(idpro);
				rdetRepo.deleteProductOfRecipeById(r);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error submit delete!!");
		}
		return "redirect:/admin/viewmore/" + idrec;
	}

	/* RECIPE TABLE & RECIPE DETAILS TABLE */

}
