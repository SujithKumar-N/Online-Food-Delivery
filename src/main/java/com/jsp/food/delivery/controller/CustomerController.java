package com.jsp.food.delivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsp.food.delivery.dto.Customer;
import com.jsp.food.delivery.service.CustomerService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    
    @GetMapping("/register")
    public String loadRegister(Customer customer, ModelMap map) {
        return customerService.register(customer, map);
    }

    @PostMapping("/register")
    public String register(@Valid Customer customer, BindingResult result, ModelMap map) {
        return customerService.register(customer,result, map);
    }

    @GetMapping("/otp/{id}")
    public String otp(@PathVariable("id") Integer id, ModelMap map) {
        map.put("id", id);
        return "customer-otp";
    }

    @PostMapping("/otp")
    public String otp(@RequestParam("otp") int otp, @RequestParam("id") int id) {
        return customerService.otp(id, otp);
    }

    @GetMapping("/resend-otp/{id}")
	public String resendOtp(@PathVariable("id") int id) {
		return customerService.resendOtp(id);
	}
    
}