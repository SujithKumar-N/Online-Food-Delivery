package com.jsp.food.delivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.food.delivery.dto.FoodCategory;
import com.jsp.food.delivery.dto.Restaurant;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Integer> {

    List<FoodCategory> findByRestaurant(Restaurant restaurant);

    List<FoodCategory> findAllByRestaurantId(Integer id);
    
}
