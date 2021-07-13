package com.dcat.ReCo.services.reservation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dcat.ReCo.dtos.reservation.ReservationCreateDTO;
import com.dcat.ReCo.dtos.reservation.ReservationOverview;
import com.dcat.ReCo.models.Reservation;

public interface ReservationService {

	Page<ReservationOverview> getAllByUser(Long userId, Integer type,
			Pageable pageble);

	Page<ReservationOverview> getAllByRestaurant(Long restaurantId,
			Integer type, Pageable pageable);
	
	Page<ReservationOverview> getAllByRestaurant(Long restaurantId, Pageable pageable);
	
	Page<ReservationOverview> getAllByOwner(Long ownerId, Integer typeId, Pageable pageable);

	Page<ReservationOverview> getAllByType(Integer type, Pageable pageable);

	Reservation createOne(ReservationCreateDTO dto);
	
	void bulkApprove(List<Long> ids, Integer type);
	
	void bulkDelete(List<Long> ids);

}