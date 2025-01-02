package com.jsp.food.delivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.food.delivery.dto.Customer;
import com.jsp.food.delivery.dto.Restaurant;
import com.jsp.food.delivery.helper.AES;
import com.jsp.food.delivery.repository.CustomerRepository;
import com.jsp.food.delivery.repository.RestaurantRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class GeneralService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    public String login(String emph, String password, HttpSession session) {
        
        Long mobile = null;
        String email = null;

        try {
            mobile = Long.parseLong(emph);
            Customer customer = customerRepository.findByMobile(mobile);
            Restaurant restaurant = restaurantRepository.findByMobile(mobile);

            if(customer == null && restaurant == null) {
                session.setAttribute("error", "Invalid Mobile Number");
                return "redirect:/login";
            } else {
                if(restaurant != null) {
                    if(AES.decrypt(restaurant.getPassword()).equals(password)) {
                        session.setAttribute("success", "Login Successful as Restaurant");
                        session.setAttribute("restaurant", restaurant);
                        return "redirect:/restaurant/home";
                    } else {
                        session.setAttribute("error", "Invalid Password");
                        return "redirect:/login";
                    }
                } else {
                    if(AES.decrypt(customer.getPassword()).equals(password)) {
                        session.setAttribute("success", "Login Successful as Customer");
                        session.setAttribute("customer", customer);
                        return "redirect:/customer/home";
                    } else {
                        session.setAttribute("error", "Invalid Password");
                        return "redirect:/login";
                    }
                }
            }

        } catch(NumberFormatException e) {
            email = emph;
            Customer customer = customerRepository.findByEmail(email);
            Restaurant restaurant = restaurantRepository.findByEmail(email);

            if(customer == null && restaurant == null) {
                session.setAttribute("error", "Invalid Email");
                return "redirect:/login";
            } else {
                if(restaurant != null) {
                    if(AES.decrypt(restaurant.getPassword()).equals(password)) {
                        session.setAttribute("success", "Login Successful as Restaurant");
                        session.setAttribute("restaurant", restaurant);
                        return "redirect:/restaurant/home";
                    } else {
                        session.setAttribute("error", "Invalid Password");
                        return "redirect:/login";
                    }
                } else {
                    if(AES.decrypt(customer.getPassword()).equals(password)) {
                        session.setAttribute("success", "Login Successful as Customer");
                        session.setAttribute("customer", customer);
                        return "redirect:/customer/home";
                    } else {
                        session.setAttribute("error", "Invalid Password");
                        return "redirect:/login";
                    }
                }
            }
        }
        
    }

    public String logout(HttpSession session) {
        session.removeAttribute("customer");
        session.removeAttribute("restaurant");
        session.setAttribute("success", "Logout Successful");
        return "redirect:/";
    }
    
}
