package com.togarpic.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.togarpic.model.*;

import com.togarpic.repository.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ClientController {
		
	@Autowired
	private CategoryRepository cateRepo;
	
	@Autowired
	private ProductRepository prodRepo;
	
	@Autowired
	private CartItemRepository citeRepo;
	
	/* ADD TO CART */
	
	@RequestMapping(value = "/addtocart/{idpro}", method = RequestMethod.GET)
	public String addToCart(@PathVariable int idpro, CartVieww cart, HttpSession session, HttpServletRequest request) {
		ArrayList<CartVieww> lstCart = new ArrayList<>();
		cart.setPro_id(idpro);
		cart.setQuantity(1);
	
		int idcart = (int) session.getAttribute("idcart");
		cart.setCart_id(idcart);
		@SuppressWarnings("unchecked")
		ArrayList<CartVieww> cart_list = (ArrayList<CartVieww>) session.getAttribute("cartlist");
		
		if(cart_list == null) {
			lstCart.add(cart);
			session.setAttribute("cartlist", lstCart);
			return "redirect:/cart/"+idcart;
		}else {
			lstCart = cart_list;
			int count = 0;
			boolean exist = false;
			for(CartVieww i : cart_list) {
				if(i.getCart_id() == 1) {
					count = 1;
					count += i.getQuantity();
					i.setQuantity(count);
					exist = true;
					return "redirect:/cart/"+idcart;
				}
			}
			if (!exist) {
				lstCart.add(cart);
				return "redirect:/cart/"+idcart;
			}	
		}
		return null;
	}
	
	/* ADD TO CART */
	
	/* Cart View */
	
	@RequestMapping(value = "/cart/{idcart}", method = RequestMethod.GET)
	public String viewCart(HttpSession session, HttpServletRequest request, @PathVariable int idcart) {
		session = request.getSession();
		
		@SuppressWarnings("unchecked")
		ArrayList<CartVieww> cart_list = (ArrayList<CartVieww>) session.getAttribute("cartlist"); 
		Iterable<CartVieww> cartProduct = null;
		if(cart_list != null) {
			cartProduct = citeRepo.findProdOfCartByCartId(idcart);
			session.setAttribute("cartProduct", cartProduct);
			session.setAttribute("cart_list", cart_list);
		}
		return "client/cart";
	}
	
	/* Cart View */
	
	
	@RequestMapping("/")
	public String showIndex(Model model, HttpServletRequest request) {
		Iterable<Category> lstCate = cateRepo.findAll();
		model.addAttribute("lstcate",lstCate);
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("sessionUser");
		model.addAttribute("sessionUser", u);
		return "index";
	}
	
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String s() {
		
		
		return "client/product_category";
	}
	
	@RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
	public String showProductByCategory(Model model, @PathVariable int id, HttpServletRequest request) {
		Iterable<ProductView> lstpro = prodRepo.findByIdName(id);
		model.addAttribute("lstpro",lstpro);
		Iterable<Category> lstCate = cateRepo.findAll();
		model.addAttribute("lstcate",lstCate);
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("sessionUser");
		model.addAttribute("sessionUser", u);
		return "client/product_category";
	}
	
	
	@RequestMapping("/cart")
	public String showCart() {
		return "client/cart";
	}
	
	
}
