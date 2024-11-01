package com.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.food.model.Address;
import com.food.model.Restaurant;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
