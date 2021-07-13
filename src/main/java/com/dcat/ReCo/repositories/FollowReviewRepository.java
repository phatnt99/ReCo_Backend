package com.dcat.ReCo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcat.ReCo.models.FollowReview;
import com.dcat.ReCo.models.UserFReview;

public interface FollowReviewRepository extends JpaRepository<UserFReview, Long> {

	Optional<UserFReview> findByUserIdAndReviewId(Long userId, Long reviewId);
}
