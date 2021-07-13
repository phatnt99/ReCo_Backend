package com.dcat.ReCo.services;

import java.util.Optional;

import com.dcat.ReCo.dtos.UserLikeDTO;
import com.dcat.ReCo.models.Restaurant;
import com.dcat.ReCo.models.User;
import com.dcat.ReCo.models.UserLike;

public interface UserLikeService {
	void updateFollowingRestaurant(UserLikeDTO dto);
	void updateFollowingReview(UserLikeDTO dto);
	UserLike createOne(UserLike model);
	Optional<UserLike> getByUserAndRestaurant(User user, Restaurant restaurant);

}