package com.togarpic.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.togarpic.repository.*;

import jakarta.servlet.http.HttpServletRequest;

import com.togarpic.model.Listinfo;
import com.togarpic.model.Order;
import com.togarpic.model.Orderdetails;
import com.togarpic.model.Product;
import com.togarpic.model.Review;
import com.togarpic.model.Storage;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private ListInfoRepository listif1;
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String showDashboard(HttpServletRequest request) {
			
		 String roles = (String) request.getSession().getAttribute("roles");
         if (roles != null && roles.equals("ADMIN")) {
             return "admin/dashboard";
         }else if(roles != null && roles.equals("USER")) {
        	 return "403";      	 
         }
		return "redirect:/home/login";
	}
	
	@RequestMapping(value = "/listorder", method = RequestMethod.GET)
	public String showCategoryList(HttpServletRequest request) {
		 String roles = (String) request.getSession().getAttribute("roles");
         if (roles != null && roles.equals("ADMIN")) {
             return "admin/order/list";
         }else if(roles != null && roles.equals("USER")) {
        	 return "403";      	 
         }
		
		return "redirect:/home/login";
	}
	@RequestMapping("/database")
	public String database(Model model,HttpServletRequest request) {
		 String roles = (String) request.getSession().getAttribute("roles");
         if (roles != null && roles.equals("ADMIN")) {
        	 Iterable<Listinfo> listif = listif1.findAll1();
     		model.addAttribute("listinfo", listif);
        	 return "admin/Databases";
         }else if(roles != null && roles.equals("USER")) {
        	 return "403";      	 
         }

		return "redirect:/home/login";

	}

	
	
	

	@RequestMapping("/flowchart")
	public String Flowchart(HttpServletRequest request) {
		 String roles = (String) request.getSession().getAttribute("roles");
         if (roles != null && roles.equals("ADMIN")) {
             return "admin/flowchart";
         }else if(roles != null && roles.equals("USER")) {
        	 return "403";      	 
         }
         return "redirect:/home/login";

	}

	//-----------REPOSITORY--------
	
	@Autowired
	private OrderRepository ord1;

	@Autowired
	private OrderDetailsRepository ord_det1;

	@Autowired
	private ReviewRepository rev1;
	
	
		//-----------REPOSITORY--------
	
		//--------------SEARCH----------------//
	@GetMapping("/searchOrder")
	  public String filterCate(@RequestParam("id") String usr,Model model,HttpServletRequest request) {
		
		String roles = (String) request.getSession().getAttribute("roles");
         if (roles != null && roles.equals("ADMIN")) {
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

		  return "redirect:/home/login";
	  }
	
		//--------------SEARCH----------------//
	
		//---------- ALL TABLE-----------------//
		
		@RequestMapping("/alltable")
		public String showAllTable(Model model,HttpServletRequest request) {
			 String roles = (String) request.getSession().getAttribute("roles");
	         if (roles != null && roles.equals("ADMIN")) {
	 			try {
					Iterable<Order> ord = ord1.findAllTop();
					model.addAttribute("listOrder", ord);
		
					Iterable<Orderdetails> ord_details = ord_det1.findAllTop();
					model.addAttribute("listOrderDetails", ord_details);
		
					Iterable<Review> review = rev1.findAllTop();
					model.addAttribute("listreview", review);
					return "admin/table";
		
				} catch (Exception ec) {
					ec.printStackTrace();
					throw new RuntimeException("list error!!");
				}
	         }else if(roles != null && roles.equals("USER")) {
	        	 return "403";      	 
	         }
			return "redirect:/home/login";

	
		}
		
		//---------- ALL TABLE-----------------//
	
		//----------TABLE ORDER -----------------//
	
		@RequestMapping("/table_order")
		public String tableOrder(Model model,HttpServletRequest request) {
			 String roles = (String) request.getSession().getAttribute("roles");
	         if (roles != null && roles.equals("ADMIN")) {
	        	 Iterable<Order> ord = ord1.findAll1();
	 			model.addAttribute("listOrder", ord);
	 			return"admin/order/Databases";
	 			
	         }else if(roles != null && roles.equals("USER")) {
	        	 return "403";      	 
	         }
			return "redirect:/home/login";
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
		public String Insertorder(Model model,HttpServletRequest request) {			
			 String roles = (String) request.getSession().getAttribute("roles");
	         if (roles != null && roles.equals("ADMIN")) {
	        	 Iterable<Review> usr1 = rev1.findAllUser();
	 			model.addAttribute("listUsrid", usr1);
	 			
	 			return "admin/order/insert_order";
	 			
	         }else if(roles != null && roles.equals("USER")) {
	        	 return "403";      	 
	         }
			
			 return "redirect:/home/login";

		}

		@RequestMapping(value = "/insert1submit", method = RequestMethod.POST)
		public String SubmitOrder(Model model, @RequestParam("usrid") long userid, Order Order,HttpServletRequest request) {
			String roles = (String) request.getSession().getAttribute("roles");
	         if (roles != null && roles.equals("ADMIN")) {
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
	         }else if(roles != null && roles.equals("USER")) {
	        	 return "403";      	 
	         }
			return "redirect:/home/login";		
		}
		
		@RequestMapping(value = "/update_order", method = RequestMethod.GET)
		public String updateorder(Model model, @RequestParam("id1") long id1,HttpServletRequest request) {
			 String roles = (String) request.getSession().getAttribute("roles");
	         if (roles != null && roles.equals("ADMIN")) {
	 			Order item = ord1.findById(id1);

				model.addAttribute("id", item.getOrd_id());
				model.addAttribute("usrid", item.getUsr_id());
				model.addAttribute("total", item.getOrd_totalAmount());
				model.addAttribute("date", item.getOrd_date());

				return "admin/order/update_order";
				
	         }else if(roles != null && roles.equals("USER")) {
	        	 return "403";      	 
	         }
			return "redirect:/home/login";

		}

		@RequestMapping(value = "/update_order_edit", method = RequestMethod.POST)
		public String update_order_edit(Model model, @RequestParam("id") long id1, @RequestParam("userid") long userid,
				@RequestParam("total") float total, @RequestParam("date") String date1, Order Order,HttpServletRequest request) {
			 String roles = (String) request.getSession().getAttribute("roles");
	         if (roles != null && roles.equals("ADMIN")) {
	        		try {

	    				Order item = ord1.findById(id1);
	    				System.out.println("id =" + item.getOrd_id());
	    				System.out.println("userid =" + userid);
	    				System.out.println("total =" + total);
	    				System.out.println("date =" + date1);

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
	         }else if(roles != null && roles.equals("USER")) {
	        	 return "403";      	 
	         }
			return "redirect:/home/login";

		}
		//----------TABLE ORDER -----------------//
		
		//----------TABLE ORDER DETAILS -----------------//
		

		@RequestMapping("/table_orderdt")
		public String tableOrder_details(Model model,HttpServletRequest request) {
			
			 String roles = (String) request.getSession().getAttribute("roles");
	         if (roles != null && roles.equals("ADMIN")) {
	        	 Iterable<Orderdetails> ord_details = ord_det1.findAll1();
	 			model.addAttribute("listOrderDetails", ord_details);
	 			return"admin/order_details/Databases";
	 			
	         }else if(roles != null && roles.equals("USER")) {
	        	 return "403";      	 
	         }
			return "redirect:/home/login";
		}	
		
		@PostMapping("/deleteOrdedt")
		public String DeleteOrderDetails(Model model, @RequestParam("idorderdt") String idorderdt,HttpServletRequest request) {
			 String roles = (String) request.getSession().getAttribute("roles");
	         if (roles != null && roles.equals("ADMIN")) {
	 			if (idorderdt != null) {
					long newparlong1;
					newparlong1 = Long.valueOf(idorderdt);
					ord_det1.deleteById(newparlong1);
				}

				return "redirect:/admin/table";
				
	         }else if(roles != null && roles.equals("USER")) {
	        	 return "403";      	 
	         }
			return "redirect:/home/login";
		}
		
		@RequestMapping(value = "/insertorddetail", method = RequestMethod.GET)
		public String Insertorderdetails(Model model) {
			Iterable<Order> ord = ord1.findAll1();
			model.addAttribute("listOrder", ord);
	
			Iterable<Storage> sto = rev1.findAllSto();
			model.addAttribute("listSto", sto);
			return "admin/order_details/insert_order_details";

		}

		@RequestMapping(value = "/insert2submit", method = RequestMethod.POST)
		public String SubmitOrderDetails(Model model, @RequestParam("ordid") long ordid, @RequestParam("stoid") long stoid,
				@RequestParam("quantity") int quantity, @RequestParam("importprice") float importprice,
				@RequestParam("exportprice") float exportprice, Orderdetails Order_dt) {

			System.out.println("ordid = " + ordid);
			System.out.println("stoid = " + stoid);
			System.out.println("quantity = " + quantity);
			System.out.println("import price = " + importprice);
			System.out.println("export price = " + exportprice);

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
		
		@RequestMapping(value = "/update_order_details", method = RequestMethod.GET)
		public String updateorder_details(Model model, @RequestParam("id1") long id1,HttpServletRequest request) {
			
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
	         }else if(roles != null && roles.equals("USER")) {
	        	 return "403";      	 
	         }
			
	         return "redirect:/home/login";
			

		}

		@RequestMapping(value = "/update_order_details_edit", method = RequestMethod.POST)
		public String update_order_details_edit(Model model, @RequestParam("id") long id,
				@RequestParam("ordid") long ordid, @RequestParam("stoid") long stoid,
				@RequestParam("quantity") int quantity, @RequestParam("imp") float imp, @RequestParam("exp") float exp,
				Orderdetails Orderdetails,HttpServletRequest request) {		
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
	         }else if(roles != null && roles.equals("USER")) {
	        	 return "403";      	 
	         }
			return "redirect:/home/login";

		}
		//----------TABLE ORDER DETAILS -----------------//
		
		
		//----------TABLE REVIEW -----------------//
		
		@RequestMapping("/table_review")
		public String tableReview(Model model,HttpServletRequest request) {
			 String roles = (String) request.getSession().getAttribute("roles");
	         if (roles != null && roles.equals("ADMIN")) {
	        	 Iterable<Review> review = rev1.findAll1();
	 			model.addAttribute("listreview", review);
	 			return"admin/review/Databases";
	         }else if(roles != null && roles.equals("USER")) {
	        	 return "403";      	 
	         }
			return "redirect:/home/login";
		}
		
		@PostMapping("/deleteReview")
		public String deleteReview(Model model, @RequestParam("idrev") String idrev,HttpServletRequest request) {			
			 String roles = (String) request.getSession().getAttribute("roles");
	         if (roles != null && roles.equals("ADMIN")) {
	        		System.out.println("id review controller = " + idrev);
	    			// ---Delete Recipe----
	    			if (idrev != null) {
	    				long newparlong1;
	    				newparlong1 = Long.valueOf(idrev);
	    				rev1.deleteById(newparlong1);
	    			}

	    			return "redirect:/admin/table";
	         }else if(roles != null && roles.equals("USER")) {
	        	 return "403";      	 
	         }
			return "redirect:/home/login";
		}
		
		@RequestMapping(value = "/insert_review", method = RequestMethod.GET)
		public String InsertReview(Model model) {
			//trong day lay ten user va ten product va id cua order_detail
			
			Iterable<Orderdetails> ord_details = ord_det1.findAll1();
			model.addAttribute("listOrderDetails", ord_details);
			
			Iterable<Review> user = rev1.findAllUser();
			model.addAttribute("listUser", user);
			
			Iterable<Product> rev = rev1.findAllPro();
			model.addAttribute("listProduct", rev);
			

			
			/////////////////////////////
			
			return "admin/review/insert_review";

		}

		@RequestMapping(value = "/insert3submit", method = RequestMethod.POST)
		public String SubmitReview(Model model, @RequestParam("userid") long userid, @RequestParam("odtid") long odtid,
				@RequestParam("proid") long proid, @RequestParam("revcon") String revcon, Review Review,HttpServletRequest request) {
	
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
	         }else if(roles != null && roles.equals("USER")) {
	        	 return "403";      	 
	         }
			return "redirect:/home/login";

		}
		
		@RequestMapping(value = "/update_review", method = RequestMethod.GET)
		public String update_review(Model model, @RequestParam("idrev") long id1,HttpServletRequest request) {
			String roles = (String) request.getSession().getAttribute("roles");
	         if (roles != null && roles.equals("ADMIN")) {
	        	 Review item = rev1.findById(id1);
	 			
	 			model.addAttribute("id", item.getRev_id());
	 			model.addAttribute("usrid", item.getUsr_id());
	 			model.addAttribute("odtid", item.getOdt_id());
	 			model.addAttribute("proid", item.getPro_id());
	 			model.addAttribute("rev_con", item.getRev_content());
	 			return "admin/review/update_review";
	 			
	         }else if(roles != null && roles.equals("USER")) {
	        	 return "403";      	 
	         }
	         return "redirect:/home/login";
		}

		@RequestMapping(value = "/update_review_edit", method = RequestMethod.POST)
		public String update_review_edit(Model model,
				@RequestParam("id") long revid, @RequestParam("userid") long userid,
				@RequestParam("orderdt") long orderdt, @RequestParam("proid") long proid, 
				@RequestParam("review") String Revcontent,Review Review,HttpServletRequest request) {
					String roles = (String) request.getSession().getAttribute("roles");
			         if (roles != null && roles.equals("ADMIN")) {
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
			         }else if(roles != null && roles.equals("USER")) {
			        	 return "403";      	 
			         }
					return "redirect:/home/login";
			

		}
		//----------TABLE REVIEW -----------------//

		//----------STATUS -----------------//
		
		@GetMapping("/status")
		public String toggleStatus(@RequestParam("action") String action, @RequestParam("id") long id,HttpServletRequest request) {
			 String roles = (String) request.getSession().getAttribute("roles");
	         if (roles != null && roles.equals("ADMIN")) {
	 			if (action.equals("open")) {
					ord1.openStatus(id);
				} else if (action.equals("close")) {
					ord1.closeStatus(id);
				}
				return "redirect:/admin/table_order";
	         }else if(roles != null && roles.equals("USER")) {
	        	 return "403";      	 
	         }
			return "redirect:/home/login";
		}
		//----------STATUS -----------------//
		
		//----------SEARCH PAGE -----------------//	
		@RequestMapping(value = "/searchInfo", method = RequestMethod.POST)
		//@PostMapping("/searchInfo")
		public String Showinfo(@RequestParam("info") String info,Model model,HttpServletRequest request) {
			
			 String roles = (String) request.getSession().getAttribute("roles");
	         if (roles != null && roles.equals("ADMIN")) {
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
	         }else if(roles != null && roles.equals("USER")) {
	        	 return "403";      	 
	         }
			
	         return "redirect:/home/login";
		
		}

		//----------SEARCH PAGE -----------------//
}
