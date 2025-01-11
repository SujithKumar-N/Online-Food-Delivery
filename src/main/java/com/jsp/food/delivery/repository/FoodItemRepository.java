package com.jsp.food.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.food.delivery.dto.FoodItem;

public interface FoodItemRepository  extends JpaRepository<FoodItem, Integer> {
    
}
