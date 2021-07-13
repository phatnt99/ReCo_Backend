package com.dcat.ReCo.services.reservation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.constants.MapperEnum;
import com.dcat.ReCo.constants.ReservationType;
import com.dcat.ReCo.dtos.ModelMapping;
import com.dcat.ReCo.dtos.ReservationDTO;
import com.dcat.ReCo.dtos.reservation.ReservationCreateDTO;
import com.dcat.ReCo.dtos.reservation.ReservationOverview;
import com.dcat.ReCo.models.Notification;
import com.dcat.ReCo.models.Reservation;
import com.dcat.ReCo.repositories.NotificationRepository;
import com.dcat.ReCo.repositories.ReservationRepository;
import com.dcat.ReCo.repositories.RestaurantRepository;
import com.dcat.ReCo.repositories.VoucherRepository;
import com.dcat.ReCo.services.firebase.FirebaseService;
import com.dcat.ReCo.services.user.UserService;
import com.google.firebase.messaging.FirebaseMessagingException;

@Service
public class ReservationServiceImpl implements ReservationService,
		ModelMapping<Reservation, ReservationDTO> {

	@Autowired
	private UserService userService;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private VoucherRepository voucherRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public Reservation createOne(ReservationCreateDTO dto) {

		return reservationRepository.save(getModel(dto, MapperEnum.CREATE));
	}

	@Override
	public Reservation getModel(ReservationDTO dto, MapperEnum type) {
		Reservation model = new Reservation();

		if (type.equals(MapperEnum.CREATE)) {
			ReservationCreateDTO createDto = (ReservationCreateDTO) dto;
			model.setUser(
					userService.getById(createDto.getUserId()).orElseGet(null));
			model.setRestaurant(restaurantRepository
					.findById(createDto.getRestaurantId()).orElseGet(null));
			model.setTimeComming(createDto.getTimeComming());
			model.setPartySize(createDto.getPartySize());
			// set one voucher
			model.setVoucher(
					createDto.getVoucherId() != null ? voucherRepository
							.findById(createDto.getVoucherId()).orElseGet(null)
							: null);
			model.setType(ReservationType.WAITING.getValue());
			model.setFullName(createDto.getFullName());
			model.setPhone(createDto.getPhone());
			model.setEmail(createDto.getEmail());
			model.setNote(createDto.getNote());
		}
		return model;
	}

	@Override
	public Page<ReservationOverview> getAllByUser(Long userId, Integer type,
			Pageable pageble) {
		return reservationRepository.findByUserIdAndType(userId,
				ReservationType.fromInt(type).getValue(), pageble);
	}

	@Override
	public Page<ReservationOverview> getAllByRestaurant(Long restaurantId,
			Integer type, Pageable pageable) {

		return reservationRepository.findByRestaurantIdAndType(restaurantId,
				type, pageable);
	}

	@Override
	public Page<ReservationOverview> getAllByType(Integer type,
			Pageable pageable) {

		return reservationRepository.findByType(type, pageable);
	}

	@Override
	public void bulkApprove(List<Long> ids, Integer type) {
		List<Reservation> approveReservations = ids.stream()
				.map(id -> {
					Reservation reservation = reservationRepository.findById(id)
							.orElseThrow();
					reservation.setType(type);

					return reservation;
				}).collect(Collectors.toList());

		List<Reservation> reservations = reservationRepository
				.saveAll(approveReservations);
		for (Reservation r : reservations) {
			// noti
			try {
				Notification noti = notificationRepository
						.save(Notification.fromApprove(r));
				FirebaseService.getInstance().pushNotiToOne(noti, r.getUser().getFcmToken());
			} catch (FirebaseMessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void bulkDelete(List<Long> ids) {
		List<Reservation> deleteReservations = ids.stream()
				.map(id -> reservationRepository.getOne(id))
				.collect(Collectors.toList());

		reservationRepository.deleteAll(deleteReservations);
	}

	@Override
	public Page<ReservationOverview> getAllByRestaurant(Long restaurantId,
			Pageable pageable) {

		return reservationRepository.findByRestaurantId(restaurantId, pageable);
	}

	@Override
	public Page<ReservationOverview> getAllByOwner(Long ownerId, Integer typeId,
			Pageable pageable) {

		return reservationRepository.findByRestaurantOwnerIdAndType(ownerId,
				typeId, pageable);
	}
}
