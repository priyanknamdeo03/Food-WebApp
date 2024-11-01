package com.food.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.model.IngredientCategory;
import com.food.model.IngredientsItem;
import com.food.model.Restaurant;
import com.food.repository.IngredientCategoryRepository;
import com.food.repository.IngredientItemRepository;

@Service
public class IngredientServiceImp implements IngredientsService{
	
	@Autowired
	private IngredientItemRepository ingredientItemRepository;
	
	@Autowired
	private IngredientCategoryRepository ingredientCategoryRepository;
	
	@Autowired
	private RestaurantService restaurantService;

	@Override
	public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
		Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
		
		IngredientCategory category = new IngredientCategory();
		category.setRestaurant(restaurant);
		category.setName(name);
		
		return ingredientCategoryRepository.save(category);
	}

	@Override
	public IngredientCategory findIngredientCategoryById(Long Id) throws Exception {
		Optional<IngredientCategory> opt = ingredientCategoryRepository.findById(Id);
		
		if(opt.isEmpty()) {
			throw new Exception("Ingredient Category Not Found");
		}
		return opt.get();
	}

	@Override
	public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long Id) throws Exception {
		restaurantService.findRestaurantById(Id);
		return ingredientCategoryRepository.findByRestaurantId(Id);
	}

	@Override
	public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId)
			throws Exception {
		
		Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
		IngredientCategory category = findIngredientCategoryById(categoryId);
		
		IngredientsItem item = new IngredientsItem();
		item.setName(ingredientName);
		item.setRestaurant(restaurant);
		item.setCategory(category);
		
		IngredientsItem ingredient = ingredientItemRepository.save(item);
		category.getIngredients().add(ingredient);
		
		return ingredient;
	}

	@Override
	public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) throws Exception {
		return ingredientItemRepository.findByRestaurantId(restaurantId);
	}

	@Override
	public IngredientsItem updateStock(Long Id) throws Exception {
		Optional<IngredientsItem> optionalIngredientsItem = ingredientItemRepository.findById(Id);
		if(optionalIngredientsItem.isEmpty()) {
			throw new Exception("Ingredient Not Found");
		}
		
		IngredientsItem ingredientsItem = optionalIngredientsItem.get();
		ingredientsItem.setInStock(!ingredientsItem.isInStock());

		return ingredientItemRepository.save(ingredientsItem);
	}

	
}









