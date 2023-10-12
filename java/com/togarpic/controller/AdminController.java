package com.togarpic.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.togarpic.repository.*;

import com.togarpic.model.Order;
import com.togarpic.model.Orderdetails;
import com.togarpic.model.Review;
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String showDashboard() {
			
		
		return "admin/dashboard";
	}
	
	@RequestMapping(value = "/listorder", method = RequestMethod.GET)
	public String showCategoryList() {
			
		
		return "admin/order/list";
	}
	@RequestMapping("/database")
	public String database() {
		return "admin/Databases";

	}

	@RequestMapping("/login")
	public String Login() {
		return "login";

	}

	@RequestMapping("/register")
	public String Register() {
		return "register";

	}

	@RequestMapping("/flowchart")
	public String Flowchart() {
		return "admin/flowchart";

	}

	@RequestMapping("/map")
	public String Map() {
		return "admin/map";

	}

	@RequestMapping("/mailbox")
	public String Mailbox() {
		return "admin/mailbox";

	}

	@RequestMapping("/mailCompose")
	public String mailCompose() {
		return "admin/mail_compose";

	}

	@RequestMapping("/invoice")
	public String invoice() {
		return "admin/invoice";

	}

	@RequestMapping("/profile")
	public String profile() {
		return "admin/profile";

	}
	//-------------------
	@Autowired
	private OrderRepository ord1;

	@Autowired
	private OrderDetailsRepository ord_det1;

	@Autowired
	private ReviewRepository rev1;
	
	// show data
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
		@RequestMapping("/table")
		public String table(Model model) {
			try {
				/*
				 * Iterable<product> pro = pro1.findAll(); model.addAttribute("listProduct",
				 * pro);
				 * 
				 * Iterable<recipe> rec = rec1.findAll1(); model.addAttribute("listRecipe",
				 * rec);
				 */

				Iterable<Order> ord = ord1.findAll1();
				model.addAttribute("listOrder", ord);

				Iterable<Orderdetails> ord_details = ord_det1.findAll1();
				model.addAttribute("listOrderDetails", ord_details);

				Iterable<Review> review = rev1.findAll1();
				model.addAttribute("listreview", review);
				return "admin/tableBasic";

			} catch (Exception ec) {
				ec.printStackTrace();
				throw new RuntimeException("list error!!");
			}

		}
		
		// ....Status

		@GetMapping("/status")
		public String toggleCategoryStatus(@RequestParam("action") String action, @RequestParam("id") long id) {
			if (action.equals("open")) {
				ord1.openStatus(id);
			} else if (action.equals("close")) {
				ord1.closeStatus(id);
			}

			return "redirect:/admin/table";
		}

		// ...Status
		// .........Action Delete
		/*
		 * @PostMapping("/delete") public String DeleteProduct(Model
		 * model,@RequestParam("id1") String id1){ //---Delete Product----
		 * System.out.println("id1 = "+id1);
		 * 
		 * 
		 * 
		 * if(id1 != null ){ long newparlong; newparlong = Long.valueOf(id1);
		 * pro1.deleteById(newparlong); }
		 * 
		 * 
		 * //---Delete Recipe---- if(id2 != null){ long newparlong1; newparlong1 =
		 * Long.valueOf(id2); rec1.deleteById(newparlong1); }
		 * 
		 * 
		 * 
		 * return "redirect:/admin/table"; }
		 */
		/*
		 * @PostMapping("/delete1") public String DeleteRecipe(Model
		 * model, @RequestParam("id2") String idrec) { // ---Delete Product----
		 * 
		 * // ---Delete Recipe---- if (idrec != null) { long newparlong1; newparlong1 =
		 * Long.valueOf(idrec); rec1.deleteById(newparlong1); }
		 * 
		 * return "redirect:/admin/table"; }
		 */

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

		// ---- Action Delete -----

		// ---- Action insert -----
		@RequestMapping(value = "/insert1", method = RequestMethod.GET)
		public String insertorder(Model model) {

			return "admin/order/insert_order";

		}

		@RequestMapping(value = "/insert1submit", method = RequestMethod.POST)
		public String InsertOrder(Model model, @RequestParam("userid") long userid, Order Order) {
			try {

				/*
				 * SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy"); java.util.Date
				 * parsedDate = format.parse(date); Date sqlDate = new
				 * Date(parsedDate.getTime());
				 */

				System.out.println("user id = " + userid);
				
			

				Order.setUsr_id(userid);
			

				ord1.insert(Order);

				Iterable<Order> ord = ord1.findAll1();
				model.addAttribute("listOrder", ord);

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

			return "admin/order_details/insert_order_details";

		}

		// ----------------------------------------------------------
		@RequestMapping(value = "/insert2submit", method = RequestMethod.POST)
		public String insertrec(Model model, @RequestParam("ordid") long ordid, @RequestParam("stoid") long stoid,
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

		@RequestMapping(value = "/insert_review", method = RequestMethod.GET)
		public String insertreview(Model model) {

			return "admin/review/insert_review";

		}

		@RequestMapping(value = "/insert3submit", method = RequestMethod.POST)
		public String Insertreview(Model model, @RequestParam("userid") long userid, @RequestParam("odtid") long odtid,
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

		// ---- Action insert ----
		// ---- Action update ----
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

				System.out.println(ordid+"---"+stoid+"---"+quantity+"---"+imp+"---"+exp+"---"+id+"---");

				
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
				@RequestParam("orderdt") long orderdt, @RequestParam("proid") long proid, @RequestParam("review") String Revcontent,Review Review) {

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

		// ---- Action update ----
}
