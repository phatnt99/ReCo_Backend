package com.dcat.ReCo.repositories.ml;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dcat.ReCo.models.Restaurant;
import com.dcat.ReCo.models.ml.ProfileDistance;

public interface ProfileDistanceRepository
		extends JpaRepository<ProfileDistance, Long> {
	public boolean existsByUserId(Long id);

	public Optional<ProfileDistance> findByUserIdAndRestaurantId(Long userId,
			Long restaurantId);
	
	@Query("select ml.restaurant from ProfileDistance ml join ml.user u where u.id=:id order by ml.distance asc")
	public List<Restaurant> getTop20FCB(@Param("id") Long id, Pageable pageable);
}
