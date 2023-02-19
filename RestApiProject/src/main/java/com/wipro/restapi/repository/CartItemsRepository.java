package com.wipro.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.restapi.entity.CartItems;

public interface CartItemsRepository extends JpaRepository<CartItems, Integer> {

}
