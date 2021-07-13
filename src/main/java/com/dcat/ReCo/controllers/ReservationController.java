package com.dcat.ReCo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.dtos.PageableDTO;
import com.dcat.ReCo.dtos.reservation.ReservationCreateDTO;
import com.dcat.ReCo.dtos.reservation.ReservationUpdateDTO;
import com.dcat.ReCo.exceptions.RestaurantNotFoundException;
import com.dcat.ReCo.exceptions.UserNotFoundException;
import com.dcat.ReCo.services.reservation.ReservationService;
import com.dcat.ReCo.utils.ValidatorUtil;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

	@Autowired
	private ValidatorUtil validatorUtil;

	@Autowired
	private ReservationService reservationService;

	@GetMapping("/type/{type}/user/{userId}")
	public ResponseEntity<?> getAllReservationByUser(
			@ModelAttribute PageableDTO pageableDto,
			@PathVariable("type") Integer typeId,
			@PathVariable("userId") Long userId,
			@RequestParam(name = "sortable", required = false, defaultValue = "createdAt") String sortable,
			@RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction) {

		Pageable pageable = PageRequest.of(pageableDto.getPage(),
				pageableDto.getSize(),
				Sort.by(Direction.valueOf(direction), sortable));

		return HttpResponse.sendOK(
				reservationService.getAllByUser(userId, typeId, pageable));
	}

	@GetMapping("/type/{type}/restaurant/{restaurantId}")
	public ResponseEntity<?> getAllReservationByRestaurant(
			@ModelAttribute PageableDTO pageableDto,
			@PathVariable("type") Integer typeId,
			@PathVariable("restaurantId") Long restaurantId,
			@RequestParam(name = "sortable", required = false, defaultValue = "createdAt") String sortable,
			@RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction) {

		Pageable pageable = PageRequest.of(pageableDto.getPage(),
				pageableDto.getSize(),
				Sort.by(Direction.valueOf(direction), sortable));

		return HttpResponse.sendOK(reservationService
				.getAllByRestaurant(restaurantId, typeId, pageable));
	}

	@GetMapping("/type-all/restaurant/{restaurantId}")
	public ResponseEntity<?> getAllReservationByRestaurantRegardlessType(
			@ModelAttribute PageableDTO pageableDto,
			@PathVariable("restaurantId") Long restaurantId,
			@RequestParam(name = "sortable", required = false, defaultValue = "createdAt") String sortable,
			@RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction) {

		Pageable pageable = PageRequest.of(pageableDto.getPage(),
				pageableDto.getSize(),
				Sort.by(Direction.valueOf(direction), sortable));

		return HttpResponse.sendOK(
				reservationService.getAllByRestaurant(restaurantId, pageable));
	}

	@GetMapping("/type/{typeId}/owner/{ownerId}")
	public ResponseEntity<?> getAllReservationByOwner(
			@ModelAttribute PageableDTO pageableDto,
			@PathVariable("typeId") Integer typeId,
			@PathVariable("ownerId") Long ownerId,
			@RequestParam(name = "sortable", required = false, defaultValue = "createdAt") String sortable,
			@RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction) {

		Pageable pageable = PageRequest.of(pageableDto.getPage(),
				pageableDto.getSize(),
				Sort.by(Direction.valueOf(direction), sortable));

		return HttpResponse
				.sendOK(reservationService.getAllByOwner(ownerId, typeId, pageable));
	}

	@GetMapping("/type/{type}")
	public ResponseEntity<?> getAllReservationByType(
			@ModelAttribute PageableDTO pageableDto,
			@PathVariable("type") Integer typeId,
			@RequestParam(name = "sortable", required = false, defaultValue = "createdAt") String sortable,
			@RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction) {

		Pageable pageable = PageRequest.of(pageableDto.getPage(),
				pageableDto.getSize(),
				Sort.by(Direction.valueOf(direction), sortable));

		return HttpResponse
				.sendOK(reservationService.getAllByType(typeId, pageable));
	}

	@PostMapping
	public ResponseEntity<?> createReservation(
			@Valid @RequestBody ReservationCreateDTO createReservationDTO)
			throws UserNotFoundException, RestaurantNotFoundException {
		validatorUtil.validateEntity(createReservationDTO.getUserId(),
				createReservationDTO.getRestaurantId());
		reservationService.createOne(createReservationDTO);
		return new HttpResponse(HttpStatus.CREATED, true,
				"OK").send();
	};

	@PostMapping("/bulk-approve")
	public ResponseEntity<?> approveReservations(
			@RequestBody ReservationUpdateDTO dto) {
		reservationService.bulkApprove(dto.getIds(), dto.getType());

		return HttpResponse.sendNoContent();
	}

	@PostMapping("/bulk-delete")
	public ResponseEntity<?> deleteReservations(
			@RequestBody ReservationUpdateDTO dto) {
		reservationService.bulkDelete(dto.getIds());

		return HttpResponse.sendNoContent();
	}
}
