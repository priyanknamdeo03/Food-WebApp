package com.food.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.food.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{
	
	@Query("Select r FROM Restaurant r where lower(r.name) LIKE lower(concat('%' , :query, '%')) "
			+ "OR lower(r.cuisineType) LIKE lower(concat('%' , :query, '%')) ")
	List<Restaurant> findBySearchQuery(String query);
	
	Restaurant findByOwnerId(Long userId);

}
