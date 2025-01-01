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

import com.jsp.food.delivery.dto.Restaurant;
import com.jsp.food.delivery.service.RestaurantService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/restaurant")
public class RestaurantController {
   
    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/register")
    public String loadRegister(Restaurant restaurant, ModelMap map) {
        return restaurantService.register(restaurant, map);
    }

    @PostMapping("/register")
    public String register(@Valid Restaurant restaurant, BindingResult result, HttpSession session) {
        return restaurantService.register(restaurant, result, session);
    }

    @GetMapping("/otp/{id}")
    public String otp(@PathVariable("id") Integer id, ModelMap map) {
        map.put("id", id);
        return "restaurant-otp";
    }

    @PostMapping("/otp")
    public String otp(@RequestParam("otp") int otp, @RequestParam("id") int id, HttpSession session) {
        return restaurantService.otp(id, otp, session);
    }

    @GetMapping("/resend-otp/{id}")
    public String resendOtp(@PathVariable("id") int id, HttpSession session) {
        return restaurantService.resendOtp(id, session);
    }
}
