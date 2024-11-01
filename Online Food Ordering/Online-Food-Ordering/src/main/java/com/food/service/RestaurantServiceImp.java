package com.food.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.model.Address;
import com.food.model.Restaurant;
import com.food.model.User;
import com.food.model.dto.RestaurantDto;
import com.food.repository.AddressRepository;
import com.food.repository.RestaurantRepository;
import com.food.repository.UserRepository;
import com.food.request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImp implements RestaurantService{

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
		
		Address address = addressRepository.save(req.getAddress());
		
		Restaurant restaurant = new Restaurant();
		
		restaurant.setAddress(req.getAddress());
		restaurant.setContactInformation(req.getContactInformation());
		restaurant.setCuisineType(req.getCuisineType());
		restaurant.setDescription(req.getDescription());
		restaurant.setImages(req.getImages());
		restaurant.setName(req.getName());
		restaurant.setOpeningHours(req.getOpeningHours());
		restaurant.setRegisterationDate(LocalDateTime.now());
		restaurant.setOwner(user);
		
		return restaurantRepository.save(restaurant);
		
	}

	@Override
	public Restaurant updateRestaurant(Long restaurtantId, CreateRestaurantRequest updateRestaurant)
			throws Exception {
		
		Restaurant restaurant = findRestaurantById(restaurtantId);
		if(restaurant.getCuisineType() != null) {
			restaurant.setCuisineType(updateRestaurant.getCuisineType());
		}
		
		if(restaurant.getDescription() != null) {
			restaurant.setDescription(updateRestaurant.getDescription());
		}
		
		if(restaurant.getName() != null) {
			restaurant.setName(updateRestaurant.getName());
		}
		
		return restaurantRepository.save(restaurant);
	}

	@Override
	public void deleteRestaurant(long RestaurantId) throws Exception {
		Restaurant restaurant = findRestaurantById(RestaurantId);
		
		restaurantRepository.delete(restaurant);
		
	}

	@Override
	public List<Restaurant> getAllRestaurant() {
		return restaurantRepository.findAll();
	}
	
	@Override
	public List<Restaurant> searchRestaurant(String keyword) {
		return restaurantRepository.findBySearchQuery(keyword);
	}

	@Override
	public Restaurant findRestaurantById(Long id) throws Exception {
		Optional<Restaurant> opt = restaurantRepository.findById(id);
		
		if(opt.isEmpty()) {
			throw new Exception("Restaurant not found with Id" + id);
		}
		return opt.get();
	}

	@Override
	public Restaurant getRestaurantByUserId(Long userId) throws Exception {
		Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
		
		if(restaurant == null) {
			throw new Exception("Restaurant not found with owner Id " + userId);
		}
		return restaurant;
	}

	@Override
	public RestaurantDto addToFavourites(Long restaurantId, User user) throws Exception {
		
		Restaurant restaurant = findRestaurantById(restaurantId);
		
		RestaurantDto dto = new RestaurantDto();
		dto.setDescription(restaurant.getDescription());
		dto.setImages(restaurant.getImages());
		dto.setTitle(restaurant.getName());
		dto.setId(restaurantId);
		
		Boolean isFavorited = false;
		List<RestaurantDto> favorites = user.getFavourites();
		for(RestaurantDto favourite : favorites) {
			if(favourite.getId().equals(restaurantId)) {
				isFavorited = true;
				break;
			}
		}
		
		if(isFavorited) {
			favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
		}else {
			favorites.add(dto);
		}
		
		userRepository.save(user);
		
		return dto;
	}

	@Override
	public Restaurant updateRestaurantStatus(Long id) throws Exception {
		Restaurant restaurant = findRestaurantById(id);
		restaurant.setOpen(!restaurant.isOpen());
	
		return restaurantRepository.save(restaurant);
	}

	

}











