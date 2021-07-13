package com.dcat.ReCo.services.comment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.constants.MapperEnum;
import com.dcat.ReCo.dtos.CommentDTO;
import com.dcat.ReCo.dtos.CreateCommentDTO;
import com.dcat.ReCo.dtos.ModelMapping;
import com.dcat.ReCo.dtos.comment.CommentBulkApproveDTO;
import com.dcat.ReCo.dtos.comment.projections.CommentOverview;
import com.dcat.ReCo.models.Comment;
import com.dcat.ReCo.models.Notification;
import com.dcat.ReCo.models.Restaurant;
import com.dcat.ReCo.repositories.CommentRepository;
import com.dcat.ReCo.repositories.NotificationRepository;
import com.dcat.ReCo.repositories.ReservationRepository;
import com.dcat.ReCo.repositories.RestaurantRepository;
import com.dcat.ReCo.services.firebase.FirebaseService;
import com.dcat.ReCo.services.user.UserService;
import com.dcat.ReCo.utils.FunctionUtil;
import com.google.firebase.messaging.FirebaseMessagingException;

@Service
public class CommentServiceImpl implements CommentService, ModelMapping<Comment, CommentDTO> {

	@Autowired
	private UserService userService;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public Comment createOne(CreateCommentDTO dto) {
		Comment comment = commentRepository.save(getModel(dto, MapperEnum.CREATE));
		// when create new comment, must update the relative restaurant star!
		Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId()).orElseThrow();

		Long commentStar = Math.round(comment.getOverallStar());
		if (commentStar == 1)
			restaurant.setStar1(restaurant.getStar1() + 1);
		if (commentStar == 2)
			restaurant.setStar2(restaurant.getStar2() + 1);
		if (commentStar == 3)
			restaurant.setStar3(restaurant.getStar3() + 1);
		if (commentStar == 4)
			restaurant.setStar4(restaurant.getStar4() + 1);
		if (commentStar == 5)
			restaurant.setStar5(restaurant.getStar5() + 1);

		restaurant.setStarAmbiance(restaurant.getStarAmbiance() == 0 ? comment.getAimbianceStar()
				: (restaurant.getStarAmbiance() + comment.getAimbianceStar()) / 2);
		restaurant.setStarFood(restaurant.getStarFood() == 0 ? comment.getFoodStar()
				: (restaurant.getStarFood() + comment.getFoodStar()) / 2);
		restaurant.setStarNoise(restaurant.getStarNoise() == 0 ? comment.getNoiseStar()
				: (restaurant.getStarNoise() + comment.getNoiseStar()) / 2);
		restaurant.setStarService(restaurant.getStarService() == 0 ? comment.getServiceStar()
				: (restaurant.getStarService() + comment.getServiceStar()) / 2);
		restaurant.setStarAverage(restaurant.getStarAverage() == 0 ? comment.getOverallStar()
				: (restaurant.getStarAverage() + comment.getOverallStar()) / 2);

		restaurantRepository.save(restaurant);

		return comment;
	}

	@Override
	public Comment getModel(CommentDTO dto, MapperEnum type) {
		Comment model = new Comment();

		if (type.equals(MapperEnum.CREATE)) {
			CreateCommentDTO createDto = (CreateCommentDTO) dto;
			model.setUser(userService.getById(createDto.getUserId()).orElseGet(null));
			model.setRestaurant(restaurantRepository.findById(createDto.getRestaurantId()).orElseGet(null));
			model.setReservation(reservationRepository.findById(createDto.getReservationId()).orElseGet(null));
			model.setContent(createDto.getContent());
			model.setListPhoto(createDto.getListPhoto());
			model.setOverallStar(FunctionUtil.avg(createDto.getAimbianceStar(), createDto.getFoodStar(),
					createDto.getServiceStar(), createDto.getNoiseStar()));
			model.setAimbianceStar(createDto.getAimbianceStar());
			model.setFoodStar(createDto.getFoodStar());
			model.setServiceStar(createDto.getServiceStar());
			model.setNoiseStar(createDto.getNoiseStar());
			model.setPrivateNote(createDto.getPrivateNote());

		} else {

		}

		return model;
	}

	@Override
	public Page<Comment> getAllByUserId(Long userId, Pageable pageable) {
		return commentRepository.findAllByUserId(userId, pageable);
	}

	@Override
	public Page<CommentOverview> getAll(Pageable pageable) {

		return commentRepository.findBy(pageable);
	}

	@Override
	public Page<CommentOverview> search(String query, Pageable pageable) {

		return commentRepository.findByUserFullNameContainsOrRestaurantNameContains(query, query, pageable);
	}

	@Override
	public CommentOverview getOne(Long id) {

		return commentRepository.findOneById(id);
	}

	@Override
	public void deleteByIds(Collection<Long> id) {

		Collection<Comment> deleteComments = commentRepository.findAllById(id);

		commentRepository.deleteAll(deleteComments);

		for (Comment c : deleteComments) {
			// noti
			try {
				Notification noti = notificationRepository.save(
						Notification.fromDelete(c.getContent(), c.getRestaurant().getLogo(), 1, c.getUser().getId()));
				FirebaseService.getInstance().pushNotiToOne(noti, c.getUser().getFcmToken());
			} catch (FirebaseMessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void approveByIds(CommentBulkApproveDTO dto) {

		Collection<Comment> aproveComments = commentRepository.findAllById(dto.getBulkIds());

		// aproveComments.stream().forEach((comment) ->
		// comment.setApproveStatus(dto.getStatus()));

		commentRepository.saveAll(aproveComments);

	}

	@Override
	public Page<CommentOverview> getAllByUser(Long userId, Pageable pageable) {

		return commentRepository.findByUserId(userId, pageable);
	}

	@Override
	public Page<CommentOverview> getAllByRestaurant(Long restaurantId, Pageable pageable) {

		return commentRepository.findByRestaurantId(restaurantId, pageable);
	}

	@Override
	public Page<CommentOverview> getAllByOwner(Long ownerId, Pageable pageable) {

		return commentRepository.findByRestaurantOwnerId(ownerId, pageable);
	}

}
