package com.dcat.ReCo.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dcat.ReCo.exceptions.RestaurantNotFoundException;
import com.dcat.ReCo.exceptions.UserNotFoundException;
import com.dcat.ReCo.models.Restaurant;
import com.dcat.ReCo.repositories.RestaurantRepository;
import com.dcat.ReCo.services.user.UserService;

@Component
public class ValidatorUtil {
	
	@Autowired
	private UserService userService;

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	public void validateEntity(Long userId, Long restaurantId) throws UserNotFoundException, RestaurantNotFoundException {
		if (!userService.getById(userId).isPresent())
			throw new UserNotFoundException();
		if (!restaurantRepository.findById(restaurantId).isPresent())
			throw new RestaurantNotFoundException();
	}
	
	public Restaurant validateEntityRestaurant(Long restaurantId) {
		Optional<Restaurant> result = restaurantRepository.findById(restaurantId);
		
		return result.orElseThrow(RestaurantNotFoundException::new);
	}

}
