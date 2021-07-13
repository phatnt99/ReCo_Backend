package com.dcat.ReCo.services.review;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.constants.MapperEnum;
import com.dcat.ReCo.dtos.CreateReviewDTO;
import com.dcat.ReCo.dtos.ModelMapping;
import com.dcat.ReCo.dtos.ReviewDTO;
import com.dcat.ReCo.dtos.responses.review.ReviewDetail;
import com.dcat.ReCo.dtos.responses.review.ReviewOverview;
import com.dcat.ReCo.elastics.ReviewSearchRepository;
import com.dcat.ReCo.models.Notification;
import com.dcat.ReCo.models.Review;
import com.dcat.ReCo.repositories.NotificationRepository;
import com.dcat.ReCo.repositories.RestaurantRepository;
import com.dcat.ReCo.repositories.ReviewRepository;
import com.dcat.ReCo.services.ResourceService;
import com.dcat.ReCo.services.firebase.FirebaseService;
import com.dcat.ReCo.services.user.UserService;
import com.google.firebase.messaging.FirebaseMessagingException;

@Service
public class ReviewServiceImpl
		implements ReviewService, ModelMapping<Review, ReviewDTO> {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private ResourceService restaurantTagService;

	@Autowired
	private ReviewSearchRepository searchRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public Review createOne(CreateReviewDTO createReviewDTO) {
		Review review = reviewRepository
				.save(getModel(createReviewDTO, MapperEnum.CREATE));
		// create index on elastic search

		review.setUserName(review.getUser().getFullName());
		review.setRestaurantName(review.getRestaurant().getName());

		searchRepository.save(review);

		return review;
	}
	
	public void initElastic() {
		List<Review> dbReview = reviewRepository.findAll();
		
		for(Review r : dbReview) {
			Review review = new Review();
			review.setId(r.getId());
			review.setUserName(r.getUser().getFullName());
			review.setRestaurantName(r.getRestaurant().getName());
			
			searchRepository.save(review);
		}
	}

	@Override
	public Review getModel(ReviewDTO dto, MapperEnum type) {
		Review model = new Review();

		if (type.equals(MapperEnum.CREATE)) {
			CreateReviewDTO createDTO = (CreateReviewDTO) dto;
			model.setUser(
					userService.getById(createDTO.getUserId()).orElseGet(null));
			model.setRestaurant(restaurantRepository
					.findById(createDTO.getRestaurantId()).orElseGet(null));
			model.setTitle(createDTO.getTitle());
			model.setContent(createDTO.getContent());
			model.setPoint(createDTO.getPoint());
			model.setListPhoto(createDTO.getListPhoto());
			model.setTags(createDTO.getTags().stream()
					.map(tagId -> restaurantTagService.getOneTag(tagId))
					.collect(Collectors.toList()));
		} else {

		}

		return model;
	}

	@Override
	public Page<Review> getAll(Pageable pageable) {

		return reviewRepository.findAll(pageable);
	}

	@Override
	public Page<ReviewOverview> getAllOverview(Pageable pageable) {

		return reviewRepository.findAllOverview(pageable);
	}

	@Override
	public ReviewDetail getOneReview(Long id) {

		return reviewRepository.getOneReview(id);
	}

	@Override
	public void deleteOne(Long id) {
		Review review = reviewRepository.findById(id).orElse(null);

		reviewRepository.delete(review);

		// noti
		try {
			Notification noti = notificationRepository
					.save(Notification.fromDelete(review.getTitle(),
							review.getRestaurant().getLogo(), 2, review.getUser().getId()));
			FirebaseService.getInstance().pushNotiToOne(noti,
					review.getUser().getFcmToken());
		} catch (FirebaseMessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Page<ReviewOverview> getAllOverviewByUser(Long userId,
			Pageable pageable) {

		return reviewRepository.findByUserId(userId, pageable);
	}

	@Override
	public Page<ReviewOverview> getAllOverviewByRestaurant(Long restaurantId,
			Pageable pageable) {

		return reviewRepository.findByRestaurantId(restaurantId, pageable);
	}

	@Override
	public Page<ReviewOverview> getAllOverviewByOwner(Long ownerId,
			Pageable pageable) {

		return reviewRepository.findByRestaurantOwnerId(ownerId, pageable);
	}

}
