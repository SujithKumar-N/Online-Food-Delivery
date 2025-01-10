package com.jsp.food.delivery.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import com.jsp.food.delivery.dto.Restaurant;
import com.jsp.food.delivery.helper.AES;
import com.jsp.food.delivery.helper.MyEmailSender;
import com.jsp.food.delivery.repository.CustomerRepository;
import com.jsp.food.delivery.repository.RestaurantRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class RestaurantService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    MyEmailSender emailSender;

    public String register(Restaurant restaurant, ModelMap map) {
        map.put("restaurant", restaurant);
        return "restaurant-register";
    }

    public String register(Restaurant restaurant, BindingResult result, HttpSession session) {
        if (!restaurant.getPassword().equals(restaurant.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.confirmPassword",
                    "Password and Confirm Password must be same");
        }

        // if (customerRepository.existsByEmail(restaurant.getEmail())
        //         || restaurantRepository.existsByEmail(restaurant.getEmail())) {
        //     result.rejectValue("email", "error.email", "Email already exists");

        // }

        // if (customerRepository.existsByMobile(restaurant.getMobile())
        //         || restaurantRepository.existsByMobile(restaurant.getMobile())) {
        //     result.rejectValue("mobile", "error.mobile", "Mobile already exists");
        // }

        if (result.hasErrors()) {
            return "restaurant-register";
        } else {
            restaurant.setOtp(new Random().nextInt(1000, 9999));
            restaurant.setVerified(false);
            restaurant.setPassword(AES.encrypt(restaurant.getPassword()));
            restaurant.setRegistrationDate(LocalDateTime.now());
            restaurantRepository.save(restaurant);
            System.err.println(restaurant.getOtp());
            // emailSender.sendOtp(restaurant);
            session.setAttribute("success", "OTP has been sent to your email");
            return "redirect:/restaurant/otp/" + restaurant.getId();
        }
    }

    public String otp(int id, int otp, HttpSession session) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
        if (restaurant.getOtp() == otp) {
            restaurant.setVerified(true);
            restaurant.setRegistrationDate(LocalDateTime.now());
            restaurantRepository.save(restaurant);
            session.setAttribute("success", "Registration Successful");
            return "redirect:/";
        } else {
            session.setAttribute("error", "Invalid OTP");
            return "redirect:/restaurant/otp/" + restaurant.getId();
        }
    }

    public String resendOtp(int id, HttpSession session) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
        restaurant.setOtp(new Random().nextInt(1000, 9999));
        restaurant.setVerified(false);
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurantRepository.save(restaurant);
        System.err.println(restaurant.getOtp());
        // emailSender.sendOtp(restaurant);
        session.setAttribute("success", "OTP has been re-sent to your email");
        return "redirect:/restaurant/otp/" + restaurant.getId();
    }

    public String home(HttpSession session) {
        if(session.getAttribute("restaurant") != null) {
            return "restaurant-home";
        } else {
            session.setAttribute("error", "Please login to continue");
            return "redirect:/login";
        }
    }

}
