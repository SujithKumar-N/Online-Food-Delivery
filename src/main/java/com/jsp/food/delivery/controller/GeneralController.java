package com.jsp.food.delivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsp.food.delivery.service.GeneralService;

import jakarta.servlet.http.HttpSession;

@Controller
public class GeneralController {

	@Autowired
	GeneralService generalService;
	
	@GetMapping("/")
	public String loadHome() {
		return "home";
	}
	
	@GetMapping("/about-us")
	public String loadAbout() {
		return "about-us";
	}
	
	@GetMapping("/contact-us")
	public String loadContact() {
		return "contact-us";
	}
	
	@GetMapping("/login")
	public String loadLogin() {
		return "login";
	}

	@PostMapping("/login")
	public String loadLogin(@RequestParam("emph") String emph, @RequestParam("password") String password, HttpSession session) {
		return generalService.login(emph, password, session);
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		return generalService.logout(session);
	}

}
