package com.dcat.ReCo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dcat.ReCo.dtos.responses.review.ReviewDetail;
import com.dcat.ReCo.dtos.responses.review.ReviewOverview;
import com.dcat.ReCo.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	@Query("SELECT r FROM Review r")
	public Page<ReviewOverview> findAllOverview(Pageable pageable);
	
	public Page<ReviewOverview> findByUserId(Long userId, Pageable pageable);
	
	public Page<ReviewOverview> findByRestaurantId(Long restaurantId, Pageable pageable);
	
	public Page<ReviewOverview> findByRestaurantOwnerId(Long ownerId, Pageable pageable);
	
	@Query("SELECT r FROM Review r WHERE r.id = :id")
	public ReviewDetail getOneReview(@Param(value = "id") Long id);
	
	<T> T findById(Long id, Class<T> type);
}
