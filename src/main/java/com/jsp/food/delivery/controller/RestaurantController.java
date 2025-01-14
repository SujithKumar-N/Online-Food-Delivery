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
import org.springframework.web.multipart.MultipartFile;

import com.jsp.food.delivery.dto.FoodCategory;
import com.jsp.food.delivery.dto.FoodItem;
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

    @GetMapping("/home")
    public String loadHome(HttpSession session) {
        return restaurantService.home(session);
    }
    
    @GetMapping("/add-category")
    public String addCategory(ModelMap map) {
        return "add-category";
    }

    @PostMapping("/add-category")
    public String addCategory(FoodCategory category, HttpSession session) {
        return restaurantService.addCategory(category, session);
    }

    @GetMapping("/manage-categories")
    public String manageCategory(HttpSession session, ModelMap map){
        return restaurantService.manageCategory(session, map);
    }

    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable("id") int id, HttpSession session) {
        return restaurantService.deleteCategory(id, session);
    }

    @GetMapping("/edit-category/{id}")
    public String editCategory(@PathVariable("id") int id, HttpSession session, ModelMap map) {
        return restaurantService.editCategory(id,session, map);
    }

    @PostMapping("/edit-category")
    public String editCategory(FoodCategory foodCategory, HttpSession session) {
        return restaurantService.editCategory(foodCategory, session);
    }

    @GetMapping("/add-item/{id}")
    public String addItem(@PathVariable("id") int id, HttpSession session) {
        return restaurantService.addItem(id, session);
    }

    @PostMapping("/add-item")
    public String addItem(@RequestParam MultipartFile image, FoodItem foodItem, HttpSession session) {
        return restaurantService.addItem(image,foodItem, session);
    }

    @GetMapping("/view-items/{id}")
    public String viewItem(@PathVariable("id") int id, HttpSession session, ModelMap map) {
        return restaurantService.viewItems(id, session, map);
    }

    @GetMapping("/delete-item/{id}")
    public String deleteItem(@PathVariable("id") int id, HttpSession session) {
        return restaurantService.deleteItem(id, session);
    }
    
}
