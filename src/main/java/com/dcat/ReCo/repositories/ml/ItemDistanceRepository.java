package com.dcat.ReCo.repositories.ml;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dcat.ReCo.models.Restaurant;
import com.dcat.ReCo.models.ml.ItemDistance;

public interface ItemDistanceRepository
		extends JpaRepository<ItemDistance, Long> {
	boolean existsByRestaurantBaseId(Long id);
	
	@Query("select ml.restaurantChild from ItemDistance ml join ml.restaurantBase rb where rb.id=:id order by ml.distance asc")
	public List<Restaurant> getTop10RelatedRestaurant(@Param("id") Long id, Pageable pageable);
}
