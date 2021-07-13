package com.dcat.ReCo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.constants.ToggleUpdateEnum;
import com.dcat.ReCo.dtos.UserLikeDTO;
import com.dcat.ReCo.models.Review;
import com.dcat.ReCo.models.User;
import com.dcat.ReCo.repositories.ReviewRepository;
import com.dcat.ReCo.repositories.UserRepository;

@Service
public class FollowReviewServiceImpl implements FollowReviewService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public void updateFollowingReview(UserLikeDTO dto) {
		User         auth            = userRepository.findById(dto.getUserId())
				.orElseThrow();
		Review       review          = reviewRepository
				.findById(dto.getFollowableId())
				.orElseThrow();
		List<Review> userFavoReviews = auth.getFavoriteReview();

		if (dto.getType().equals(ToggleUpdateEnum.ACTIVE)) {
			userFavoReviews.add(review);
		}

		if (dto.getType().equals(ToggleUpdateEnum.INACTIVE)) {
			userFavoReviews = auth.getFavoriteReview();
			userFavoReviews = userFavoReviews.stream()
					.filter(r -> r.getId() != review.getId())
					.collect(Collectors.toList());

		}

		auth.setFavoriteReview(userFavoReviews);
		userRepository.save(auth);

	}
}
