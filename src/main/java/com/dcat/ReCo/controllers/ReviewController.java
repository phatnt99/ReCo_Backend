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

import com.dcat.ReCo.dtos.CreateReviewDTO;
import com.dcat.ReCo.dtos.PageableDTO;
import com.dcat.ReCo.exceptions.RestaurantNotFoundException;
import com.dcat.ReCo.exceptions.UserNotFoundException;
import com.dcat.ReCo.services.review.ReviewService;
import com.dcat.ReCo.utils.ValidatorUtil;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
	@Autowired
	private ReviewService reivewService;

	@Autowired
	private ValidatorUtil validatorUtil;

	@GetMapping
	public ResponseEntity<?> getAll(
			@ModelAttribute PageableDTO pageableDto,
			@RequestParam(name = "sortable", required = false, defaultValue = "createdAt") String sortProp,
			@RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction) {
		Pageable pageable = PageRequest.of(pageableDto.getPage(),
				pageableDto.getSize(),
				Sort.by(Direction.valueOf(direction), sortProp));

		return new HttpResponse(HttpStatus.OK, true,
				reivewService.getAllOverview(pageable)).send();
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> getAllByUser(
			@ModelAttribute PageableDTO pageableDto,
			@PathVariable("id") Long userId,
			@RequestParam(name = "sortable", required = false, defaultValue = "createdAt") String sortable,
			@RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction) {
		
		Pageable pageable = PageRequest.of(pageableDto.getPage(),
				pageableDto.getSize(),
				Sort.by(Direction.valueOf(direction), sortable));

		return new HttpResponse(HttpStatus.OK, true,
				reivewService.getAllOverviewByUser(userId, pageable)).send();
	}
	
	@GetMapping("/restaurant/{id}")
	public ResponseEntity<?> getAllByRestaurant(
			@ModelAttribute PageableDTO pageableDto,
			@PathVariable("id") Long restaurantId,
			@RequestParam(name = "sortable", required = false, defaultValue = "createdAt") String sortable,
			@RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction) {
		
		Pageable pageable = PageRequest.of(pageableDto.getPage(),
				pageableDto.getSize(),
				Sort.by(Direction.valueOf(direction), sortable));

		return new HttpResponse(HttpStatus.OK, true,
				reivewService.getAllOverviewByRestaurant(restaurantId, pageable)).send();
	}
	
	@GetMapping("/owner/{id}")
	public ResponseEntity<?> getAllByOwner(
			@ModelAttribute PageableDTO pageableDto,
			@PathVariable("id") Long ownerId,
			@RequestParam(name = "sortable", required = false, defaultValue = "createdAt") String sortable,
			@RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction) {
		
		Pageable pageable = PageRequest.of(pageableDto.getPage(),
				pageableDto.getSize(),
				Sort.by(Direction.valueOf(direction), sortable));

		return new HttpResponse(HttpStatus.OK, true,
				reivewService.getAllOverviewByOwner(ownerId, pageable)).send();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable(name = "id") Long id) {

		return new HttpResponse(HttpStatus.OK, true,
				reivewService.getOneReview(id)).send();
	}

	@PostMapping
	public ResponseEntity<?> createOne(
			@Valid @RequestBody CreateReviewDTO createReviewDTO)
			throws UserNotFoundException, RestaurantNotFoundException {
		validatorUtil.validateEntity(createReviewDTO.getUserId(),
				createReviewDTO.getRestaurantId());

		return new HttpResponse(HttpStatus.CREATED, true,
				reivewService.createOne(createReviewDTO)).send();
	};

	@PostMapping("/reaction")
	public ResponseEntity<?> reactionOne() {

		return null;
	}
	
	@GetMapping("/els")
	public ResponseEntity<?> initElastic() {
		
		reivewService.initElastic();
		
		return HttpResponse.sendNoContent();
	}

}
