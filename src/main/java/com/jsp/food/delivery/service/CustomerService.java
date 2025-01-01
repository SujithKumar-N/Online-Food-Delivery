package com.jsp.food.delivery.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.jsp.food.delivery.dto.Customer;
import com.jsp.food.delivery.helper.MyEmailSender;
import com.jsp.food.delivery.repository.CustomerRepository;
import com.jsp.food.delivery.repository.RestaurantRepository;

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

    public String register(Customer customer, BindingResult result, ModelMap map) {
        
        if(!customer.getPassword().equals(customer.getConfirmPassword())){
            result.rejectValue("confirmPassword", "error.confirmPassword", "Password and Confirm Password must be same");
        }

        // if(customerRepository.existsByEmail(customer.getEmail())
        //         || restaurantRepository.existsByEmail(customer.getEmail())){
        //     result.rejectValue("email", "error.email", "Email already exists"); 

        // }

        // if(customerRepository.existsByMobile(customer.getMobile())
        //     || restaurantRepository.existsByMobile(customer.getMobile())){
        //     result.rejectValue("mobile", "error.mobile", "Mobile already exists");
        // }
        
        if(result.hasErrors()){
            return "customer-register";
        } else {
            customer.setOtp(new Random().nextInt(1000,9999));
            customer.setVerified(false);
            customer.setRegistrationDate(LocalDateTime.now());
            customerRepository.save(customer);
            System.err.println(customer.getOtp());
            // emailSender.sendOtp(customer);
            return "redirect:/customer/otp/"+customer.getId();

        }
    }

    public String otp(int id, int otp) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        if(customer.getOtp() == otp) {
            customer.setVerified(true);
            customer.setRegistrationDate(LocalDateTime.now());
            customerRepository.save(customer);
            return "redirect:/";
        } else {
            return "redirect:/customer/otp/"+customer.getId();
        }
    }

    public String resendOtp(int id) {
        Customer customer = customerRepository.findById(id).orElseThrow();
        customer.setOtp(new Random().nextInt(1000,9999));
        customer.setVerified(false);
        customer.setRegistrationDate(LocalDateTime.now());
        customerRepository.save(customer);
        System.err.println(customer.getOtp());
        // emailSender.sendOtp(customer);
        return "redirect:/customer/otp/"+customer.getId();
    }
    
}