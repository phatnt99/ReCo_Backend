package com.dcat.ReCo.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.dcat.ReCo.models.Restaurant;
import com.dcat.ReCo.models.Restaurant_;

public class RestaurantValidSpec {
	public static Specification<Restaurant> isValid() {
		return (root, query, cb) -> cb
				.equal(root.get(Restaurant_.APPROVE_STATUS), 2);
	}
}
