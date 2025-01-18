package com.jsp.food.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.food.delivery.dto.FoodCategory;
import com.jsp.food.delivery.dto.FoodItem;
import com.jsp.food.delivery.dto.Restaurant;

public interface FoodItemRepository  extends JpaRepository<FoodItem, Integer> {

    List<FoodItem> findByCategory(FoodCategory foodCategory);

    List<FoodItem> findByRestaurant(Restaurant restaurant);
    
}
