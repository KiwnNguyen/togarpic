package com.togarpic.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.http.HttpServletRequest;

import com.togarpic.repository.*;
import com.togarpic.model.*;


@Controller
@RequestMapping("/admin")
public class AdminController implements WebMvcConfigurer {
	
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

	/*----*/

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String showIndex() {

		return "admin/dashboard";
	}

	@RequestMapping(value = "/alltable", method = RequestMethod.GET)
	public String showAllTable(Model model) {
		Iterable<Recipe> listreci = reciRepo.findAll();
		model.addAttribute("listreci", listreci);

		Iterable<Category> listcate = cateRepo.findAll();
		model.addAttribute("listcate", listcate);

		Iterable<RecipeDetails> listrdet = rdetRepo.findAll();
		model.addAttribute("listrdet", listrdet);

		Iterable<RecipeDetailsView> listredet = rdetRepo.findAllname();
		model.addAttribute("listrdetname", listredet);

		Iterable<ProductView> listprod = product.findAll1();
		model.addAttribute("listprod", listprod);

		Iterable<StorageView> liststo = storage.findAll1();
		model.addAttribute("liststo", liststo);
		
		return "admin/table";
	}

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
	public String viewMoreRecipe(@PathVariable(name="id") int id, Model model) {
		int parseId;
		parseId = Integer.valueOf(id);
		Iterable<RecipeDetailsView> listprodreci = rdetRepo.findByIdname(parseId);
		model.addAttribute("listprodreci", listprodreci);
	
		Recipe reci = reciRepo.findById(parseId);
		model.addAttribute("reci", reci);
		return "admin/recipe/details";
	}

	@RequestMapping(value = "/viewmore/{id}/add", method = RequestMethod.GET)
	public String addMoreRecipe(@PathVariable(name="id") int id, Model model) {
		int parseId;
		parseId = Integer.valueOf(id);
		Recipe reci = reciRepo.findById(parseId);
		model.addAttribute("reci", reci);
		Iterable<Product> prod = product.findAll();
		model.addAttribute("listprod", prod);
		
		return "admin/recipe/adddetails";
	}
	
	@RequestMapping(value = "/viewmore/{id}/add", method = RequestMethod.POST)
	public String addMoreRecipeSubmit(
			HttpServletRequest request, 
			@PathVariable(name="id") int idreci, 
			Model model, 
			@RequestParam("product") int productid, 
			@RequestParam("quantity") String quantity) {
		
		int parseId = idreci; 
		int parseIdproduct= Integer.valueOf(productid);
		RecipeDetails item = new RecipeDetails();
		item.setRecipe_id(parseId);
		item.setProduct_id(parseIdproduct);
		item.setQuantity(quantity);
		  
		rdetRepo.insert(item);
	
		Iterable<RecipeDetailsView> listprodreci = rdetRepo.findByIdname(parseId);
		model.addAttribute("listprodreci", listprodreci);
		
		Recipe reci = reciRepo.findById(parseId);
		model.addAttribute("reci", reci);
		
		return "redirect:/admin/viewmore/"+parseId;
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
	/* RECIPE TABLE & RECIPE DETAILS TABLE */
}
