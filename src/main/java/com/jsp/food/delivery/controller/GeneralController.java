package com.jsp.food.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GeneralController {
	
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
}
