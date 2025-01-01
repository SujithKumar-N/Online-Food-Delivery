package com.jsp.food.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.food.delivery.dto.Restaurant;

public interface RestaurantRepository  extends JpaRepository<Restaurant, Integer>{

    boolean existsByEmail(String email);

    boolean existsByMobile(Long mobile);
    
}
