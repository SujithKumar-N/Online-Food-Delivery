package com.jsp.food.delivery.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.jsp.food.delivery.dto.Customer;
import com.jsp.food.delivery.helper.AES;
import com.jsp.food.delivery.helper.MyEmailSender;
import com.jsp.food.delivery.repository.CustomerRepository;
import com.jsp.food.delivery.repository.RestaurantRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    MyEmailSender emailSender;

    public String register(Customer customer, ModelMap map) {
        map.put("customer", customer);
        return "customer-register";
    }

    public String register(Customer customer, BindingResult result, HttpSession session) {

        if (!customer.getPassword().equals(customer.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.confirmPassword",
                    "Password and Confirm Password must be same");
        }

        if (customerRepository.existsByEmail(customer.getEmail())
                || restaurantRepository.existsByEmail(customer.getEmail())) {
            result.rejectValue("email", "error.email", "Email already exists");

        }

        if (customerRepository.existsByMobile(customer.getMobile())
                || restaurantRepository.existsByMobile(customer.getMobile())) {
            result.rejectValue("mobile", "error.mobile", "Mobile already exists");
        }

        if (result.hasErrors()) {
            return "customer-register";
        } else {
            customer.setOtp(new Random().nextInt(1000, 9999));
            customer.setVerified(false);
            customer.setPassword(AES.encrypt(customer.getPassword()));
            customer.setRegistrationDate(LocalDateTime.now());
            customerRepository.save(customer);
            System.err.println(customer.getOtp());
            emailSender.sendOtp(customer);
            session.setAttribute("success", "OTP has been sent to your email");
            return "redirect:/customer/otp/" + customer.getId();
        }
    }

    public String otp(int id, int otp, HttpSession session) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        if (customer.getOtp() == otp) {
            customer.setVerified(true);
            customer.setRegistrationDate(LocalDateTime.now());
            customerRepository.save(customer);
            session.setAttribute("success", "Registration Successful");
            return "redirect:/";
        } else {
            session.setAttribute("error", "Invalid OTP");
            return "redirect:/customer/otp/" + customer.getId();
        }
    }

    public String resendOtp(int id, HttpSession session) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        customer.setOtp(new Random().nextInt(1000, 9999));
        customer.setVerified(false);
        customer.setRegistrationDate(LocalDateTime.now());
        customerRepository.save(customer);
        System.err.println(customer.getOtp());
        emailSender.sendOtp(customer);
        session.setAttribute("success", "OTP has been re-sent to your email");
        return "redirect:/customer/otp/" + customer.getId();
    }

    public String home(HttpSession session) {
        if(session.getAttribute("customer") != null) {
            return "customer-home";
        } else {
            session.setAttribute("error", "Please login to continue");
            return "redirect:/";
        }
    }

}
