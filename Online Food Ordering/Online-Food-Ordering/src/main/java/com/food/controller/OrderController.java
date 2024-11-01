package com.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.food.model.CartItem;
import com.food.model.Order;
import com.food.model.User;
import com.food.request.AddCartItemRequest;
import com.food.request.OrderRequest;
import com.food.service.OrderService;
import com.food.service.UserService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@PutMapping("/order")
	public ResponseEntity<Order> createOrder(@RequestBody OrderRequest req,
														@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		Order order = orderService.creatOrder(req, user);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	
	@GetMapping("/order/user")
	public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user = userService.findUserByJwtToken(jwt);
		List<Order> orders = orderService.getUsersOrder(user.getId());
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
}








