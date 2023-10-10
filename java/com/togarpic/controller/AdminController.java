package com.togarpic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.togarpic.model.Product;
import com.togarpic.model.Storage;
import com.togarpic.repository.ProductRepository;
import com.togarpic.repository.StorageRepository;

@Controller
@RequestMapping("/admin")
public class AdminController implements WebMvcConfigurer {
	@Autowired
	private ProductRepository product;
	@Autowired
	private StorageRepository storage;

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String showDashboard() {

		return "admin/dashboard";
	}

	@RequestMapping(value = "/listcategory", method = RequestMethod.GET)
	public String showCategoryList() {

		return "admin/category/list";
	}

	@RequestMapping(value = "/listproduct", method = RequestMethod.GET)
	public String listProduct(Model model) {
		try {
			List<Product> pro = product.findAll();
			model.addAttribute("listProduct", pro);
			return "admin/product/list_product";
		} catch (Exception ec) {
			ec.printStackTrace();
			throw new RuntimeException("list error!!");
		}
	}

	@RequestMapping(value = "/insertproduct", method = RequestMethod.GET)
	public String insertProduct(Model model) {
		return "admin/product/insert_product";
	}

	@RequestMapping(value = "/insert2submit", method = RequestMethod.POST)
	public String InsertCategory(@RequestParam("pro_name") String name, Product product1, Model model,
//			@RequestParam("pro_image") String image,
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
//		Iterable<Product> pro = product.findAll();
//		model.addAttribute("listProduct", pro);
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
}
