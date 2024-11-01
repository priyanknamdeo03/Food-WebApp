package com.food.service;

import java.util.List;

import com.food.model.Restaurant;
import com.food.model.User;
import com.food.model.dto.RestaurantDto;
import com.food.request.CreateRestaurantRequest;

public interface RestaurantService {
	
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

	public Restaurant updateRestaurant(Long restaurtantId, CreateRestaurantRequest updateRestaurant) throws Exception;
	
	public void deleteRestaurant(long RestaurantId) throws Exception;
	
	public List<Restaurant> getAllRestaurant();
	
	public List<Restaurant> searchRestaurant(String keyword);
	
	public Restaurant findRestaurantById(Long id) throws Exception;
	
	public Restaurant getRestaurantByUserId(Long userId) throws Exception;
	
	public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception;
	
	public Restaurant updateRestaurantStatus(Long id) throws Exception;

	
	
}
