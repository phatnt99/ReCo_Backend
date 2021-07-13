package com.dcat.ReCo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dcat.ReCo.dtos.reservation.ReservationOverview;
import com.dcat.ReCo.models.Reservation;

public interface ReservationRepository
		extends JpaRepository<Reservation, Long> {

	Page<ReservationOverview> findByUserIdAndType(Long userId, Integer type,
			Pageable pageable);

	Page<ReservationOverview> findByRestaurantIdAndType(Long restaurantId,
			Integer type, Pageable pageable);

	Page<ReservationOverview> findByRestaurantId(Long restaurantId,
			Pageable pageable);

	Page<ReservationOverview> findByRestaurantOwnerIdAndType(Long ownerId, Integer typeId,
			Pageable pageable);

	Page<ReservationOverview> findByType(Integer type, Pageable pageable);
	
	Reservation findByCommentId(Long id);
}
