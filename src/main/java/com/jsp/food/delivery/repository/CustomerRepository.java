package com.jsp.food.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.food.delivery.dto.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	boolean existsByEmail(String email);

	boolean existsByMobile(Long long1);

}
