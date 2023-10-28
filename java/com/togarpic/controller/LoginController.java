package com.togarpic.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.togarpic.model.User;
import com.togarpic.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	UserRepository userRepo;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST )
	public String submitLogin(HttpServletRequest request, HttpSession session, Model model) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User u = new User();
		u = userRepo.getLogin(username,password);
		if(u == null) {
			request.setAttribute("message", "Username or password is wrong!");
			return "login";
		}else {
			session = request.getSession();
			session.setAttribute("sessionUser", u);
			User us = (User)session.getAttribute("sessionUser");
			model.addAttribute("sessionUser", us);
			session.setMaxInactiveInterval(100);
			return "redirect:/";
		}
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerSubmit(HttpServletRequest request, Model model) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		if(password.equals(repassword)) {
			if(userRepo.checkUser(username) != 0) {
				request.setAttribute("message", "Username is exist!");
				return "register";
			}else {
				User user = new User();
				user.setUsr_email(username);
				user.setUsr_password(password);
				userRepo.insertRegisterUser(user);
				//session
				HttpSession session = request.getSession();
				session.setAttribute("sessionUser", user);
				User u = (User)session.getAttribute("sessionUser");
				model.addAttribute("sessionUser", u);
				session.setMaxInactiveInterval(1800);
				return "redirect:/";
			}
		}else {
			request.setAttribute("message", "Password not match!");
			return "register";
		}	
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("sessionUser");
		return "redirect:/";
	}
}
