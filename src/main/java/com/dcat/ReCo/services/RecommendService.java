package com.dcat.ReCo.services;

import java.util.List;

import com.dcat.ReCo.models.Restaurant;

public interface RecommendService {
	List<Restaurant> getTop20FCB(Long userId, double lng, double lat);

	List<Restaurant> getTop10ICB(Long restaurantId, double lng, double lat);
	
	List<Restaurant> getTop20UCB(Long userId, double lng, double lat);
	
}
