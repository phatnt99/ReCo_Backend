package com.dcat.ReCo.services.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dcat.ReCo.dtos.CreateReviewDTO;
import com.dcat.ReCo.dtos.responses.review.ReviewDetail;
import com.dcat.ReCo.dtos.responses.review.ReviewOverview;
import com.dcat.ReCo.models.Review;

public interface ReviewService {
	public Page<Review> getAll(Pageable pageable);

	public Page<ReviewOverview> getAllOverview(Pageable pageable);
	
	public Page<ReviewOverview> getAllOverviewByUser(Long userId, Pageable pageable);
	
	public Page<ReviewOverview> getAllOverviewByRestaurant(Long restaurantId, Pageable pageable);
	
	public Page<ReviewOverview> getAllOverviewByOwner(Long ownerId, Pageable pageable);

	public ReviewDetail getOneReview(Long id);
	
	public Review createOne(CreateReviewDTO createReviewDTO);
	
	public void deleteOne(Long id);
	
	void initElastic();
}
