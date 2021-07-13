package com.dcat.ReCo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.constants.MapperEnum;
import com.dcat.ReCo.constants.ToggleUpdateEnum;
import com.dcat.ReCo.dtos.CreateUserLikeDTO;
import com.dcat.ReCo.dtos.ModelMapping;
import com.dcat.ReCo.dtos.UserLikeDTO;
import com.dcat.ReCo.models.Restaurant;
import com.dcat.ReCo.models.Review;
import com.dcat.ReCo.models.User;
import com.dcat.ReCo.models.UserFRestaurant;
import com.dcat.ReCo.models.UserFReview;
import com.dcat.ReCo.models.UserLike;
import com.dcat.ReCo.repositories.FollowRestaurantRepository;
import com.dcat.ReCo.repositories.FollowReviewRepository;
import com.dcat.ReCo.repositories.RestaurantRepository;
import com.dcat.ReCo.repositories.ReviewRepository;
import com.dcat.ReCo.repositories.UserLikeRepository;
import com.dcat.ReCo.repositories.UserRepository;

@Service
public class UserLikeServiceImpl
		implements UserLikeService, ModelMapping<UserLike, CreateUserLikeDTO> {

	@Autowired
	private UserLikeRepository userLikeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private FollowRestaurantRepository followRestaurantRepository;

	@Autowired
	private FollowReviewRepository followReviewRepository;

	@Override
	public void updateFollowingRestaurant(UserLikeDTO dto) {
		User       auth       = userRepository.findById(dto.getUserId())
				.orElseThrow();
		Restaurant restaurant = restaurantRepository
				.findById(dto.getFollowableId())
				.orElseThrow();
		// List<Restaurant> userFavoRestaurant = auth.getFavoriteRestaurant();
		List<UserFRestaurant> followedUsers = auth.getFollowedRestaurant();

		if (dto.getType().equals(ToggleUpdateEnum.ACTIVE)) {
			UserFRestaurant followable = new UserFRestaurant();
			followable.setUser(auth);
			followable.setRestaurant(restaurant);

			followRestaurantRepository.save(followable);
			auth.getFollowedRestaurant().addAll(followedUsers);
		}

		if (dto.getType().equals(ToggleUpdateEnum.INACTIVE)) {
			followedUsers = followedUsers.stream()
					.filter(r -> r.getRestaurant().getId() != restaurant
							.getId())
					.collect(Collectors.toList());
			auth.getFollowedRestaurant().clear();
			auth.getFollowedRestaurant().addAll(followedUsers);
		}

		userRepository.save(auth);
	}

	@Override
	public Optional<UserLike> getByUserAndRestaurant(User user,
			Restaurant restaurant) {
		List<UserLike> collection = userLikeRepository
				.findByUserIdAndRestaurantId(user.getId(), restaurant.getId());
		return collection.size() > 0 ? Optional.of(collection.get(0))
				: Optional.ofNullable(null);
	}

	@Override
	public UserLike createOne(UserLike model) {

		return userLikeRepository.save(model);
	}

	@Override
	public UserLike getModel(CreateUserLikeDTO dto, MapperEnum type) {
		// just have create
		UserLike model = new UserLike();
		model.setUser(dto.getUser());
		// model.setRestaurant(dto.getRestaurant());
		model.setLikeableType(dto.getType());
		return model;
	}

	@Override
	public void updateFollowingReview(UserLikeDTO dto) {
		User   auth   = userRepository.findById(dto.getUserId())
				.orElseThrow();
		Review review = reviewRepository
				.findById(dto.getFollowableId())
				.orElseThrow();
		// List<Restaurant> userFavoRestaurant = auth.getFavoriteRestaurant();
		List<UserFReview> followedUsers = auth.getFollowedReview();

		if (dto.getType().equals(ToggleUpdateEnum.ACTIVE)) {
			UserFReview followable = new UserFReview();
			followable.setUser(auth);
			followable.setReview(review);

			followReviewRepository.save(followable);
			auth.getFollowedReview().addAll(followedUsers);
		}

		if (dto.getType().equals(ToggleUpdateEnum.INACTIVE)) {
			followedUsers = followedUsers.stream()
					.filter(r -> r.getReview().getId() != review
							.getId())
					.collect(Collectors.toList());
			auth.getFollowedReview().clear();
			auth.getFollowedReview().addAll(followedUsers);
		}

		userRepository.save(auth);

	}

}
