package com.wipro.restapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.restapi.entity.FoodItems;

public interface FoodItemsRepository extends JpaRepository<FoodItems, Integer> {

	List<FoodItems> findByStatusLike(String likePattern);

}