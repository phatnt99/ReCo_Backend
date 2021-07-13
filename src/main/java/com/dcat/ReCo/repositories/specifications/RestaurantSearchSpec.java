package com.dcat.ReCo.repositories.specifications;

import java.util.Collection;

import org.springframework.data.jpa.domain.Specification;

import com.dcat.ReCo.models.Address_;
import com.dcat.ReCo.models.District_;
import com.dcat.ReCo.models.Restaurant;
import com.dcat.ReCo.models.Restaurant_;
import com.dcat.ReCo.models.Tag_;

public final class RestaurantSearchSpec {

	public static Specification<Restaurant> hasDistrictIn(Collection<Long> districtIds) {
		if (districtIds == null || districtIds.size() <= 0)
			return (root, query, cb) -> cb.conjunction();

		return (root, query, cb) -> root.join(Restaurant_.address).join(Address_.DISTRICT).get(District_.ID)
				.in(districtIds);
	}

	public static Specification<Restaurant> hasMinPrice(Long minPrice) {
		if (minPrice == null)
			return (root, query, cb) -> cb.conjunction();

		return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(Restaurant_.MIN_PRICE), minPrice);
	}

	public static Specification<Restaurant> hasMaxPrice(Long maxPrice) {
		if (maxPrice == null)
			return (root, query, cb) -> cb.conjunction();

		return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(Restaurant_.MAX_PRICE), maxPrice);
	}

	public static Specification<Restaurant> hasTypeIn(Collection<Long> typeIds) {
		if (typeIds == null || typeIds.size() <= 0)
			return (root, query, cb) -> cb.conjunction();

		return (root, query, cb) -> root.join(Restaurant_.tags).get(Tag_.id).in(typeIds);
	}

	public static Specification<Restaurant> hasNationIn(Collection<Long> nationIds) {
		if (nationIds == null || nationIds.size() <= 0)
			return (root, query, cb) -> cb.conjunction();

		return (root, query, cb) -> root.join(Restaurant_.tags).get(Tag_.id).in(nationIds);
	}
	
	public static Specification<Restaurant> hasDishesIn(Collection<Long> dishesIds) {
		if (dishesIds == null || dishesIds.size() <= 0)
			return (root, query, cb) -> cb.conjunction();

		return (root, query, cb) -> root.join(Restaurant_.tags).get(Tag_.id).in(dishesIds);
	}

}
