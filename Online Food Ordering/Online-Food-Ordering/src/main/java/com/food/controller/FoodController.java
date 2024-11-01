package com.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.model.Food;
import com.food.model.Restaurant;
import com.food.model.User;
import com.food.request.CreateFoodRequest;
import com.food.service.FoodService;
import com.food.service.RestaurantService;
import com.food.service.UserService;

@RestController
@RequestMapping("/api/food")
public class FoodController {
	
	@Autowired
	private FoodService foodService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	
	@GetMapping("/search")
	private ResponseEntity<List<Food>> searchFood(@RequestParam String name, 
												@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		List<Food> foods = foodService.searchFood(name);
		
		return new ResponseEntity<>(foods, HttpStatus.CREATED);
		
	}
	
	@GetMapping("/restaurant/{restaurantId}")
	private ResponseEntity<List<Food>> getRestaurantFood(
						@RequestParam boolean vegetarian, 
						@RequestParam boolean seasonal, 
						@RequestParam boolean nonVeg, 
						@RequestParam(required = false) String food_category, 
						@PathVariable Long restaurantId,
						@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		List<Food> foods = foodService.getRestaurantsFood(restaurantId, vegetarian, nonVeg, seasonal, food_category);
		
		return new ResponseEntity<>(foods, HttpStatus.OK);
		
	}


}









