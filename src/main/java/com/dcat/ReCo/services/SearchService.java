package com.dcat.ReCo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchService {

	Page<Object> searchReview(String query, Pageable pageable);
	
	Page<Object> searchRestaurant(final String query,
			Pageable pageable, String type, double lng, double lat);

}