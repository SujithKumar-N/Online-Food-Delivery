package com.jsp.food.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.food.delivery.dto.FoodCategory;
import com.jsp.food.delivery.dto.FoodItem;

public interface FoodItemRepository  extends JpaRepository<FoodItem, Integer> {

    List<FoodItem> findByCategory(FoodCategory foodCategory);
    
}
