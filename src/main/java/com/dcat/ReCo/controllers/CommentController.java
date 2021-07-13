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

import com.dcat.ReCo.dtos.CreateCommentDTO;
import com.dcat.ReCo.dtos.PageableDTO;
import com.dcat.ReCo.exceptions.RestaurantNotFoundException;
import com.dcat.ReCo.exceptions.UserNotFoundException;
import com.dcat.ReCo.services.comment.CommentService;
import com.dcat.ReCo.utils.ValidatorUtil;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
@RequestMapping("/comments")
public class CommentController {

	@Autowired
	private ValidatorUtil validatorUtil;

	@Autowired
	private CommentService commentService;

	@GetMapping
	public ResponseEntity<?> getAllComment(
			@ModelAttribute PageableDTO pageableDTO,
			@RequestParam(name = "sortable", required = false, defaultValue = "createdAt") String sortProp,
			@RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction) {

		Pageable pageable = PageRequest.of(pageableDTO.getPage(),
				pageableDTO.getSize(),
				Sort.by(Direction.valueOf(direction), sortProp));

		return new HttpResponse(HttpStatus.OK, true,
				commentService.getAll(pageable)).send();
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<?> getAllCommentByUser(
			@PathVariable("id") Long userId,
			@ModelAttribute PageableDTO pageableDTO,
			@RequestParam(name = "sortable", required = false, defaultValue = "createdAt") String sortable,
			@RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction) {

		Pageable pageable = PageRequest.of(pageableDTO.getPage(),
				pageableDTO.getSize(),
				Sort.by(Direction.valueOf(direction), sortable));
		
		return new HttpResponse(HttpStatus.OK, true,
				commentService.getAllByUser(userId, pageable)).send();
	}
	
	@GetMapping("/restaurant/{id}/all")
	public ResponseEntity<?> getAllCommentByRestaurant(
			@PathVariable("id") Long restaurantId,
			@ModelAttribute PageableDTO pageableDTO,
			@RequestParam(name = "sortable", required = false, defaultValue = "createdAt") String sortable,
			@RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction) {

		Pageable pageable = PageRequest.of(pageableDTO.getPage(),
				pageableDTO.getSize(),
				Sort.by(Direction.valueOf(direction), sortable));
		
		return new HttpResponse(HttpStatus.OK, true,
				commentService.getAllByRestaurant(restaurantId, pageable)).send();
	}
	
	@GetMapping("/owner/{id}")
	public ResponseEntity<?> getAllCommentByOwner(
			@PathVariable("id") Long restaurantId,
			@ModelAttribute PageableDTO pageableDTO,
			@RequestParam(name = "sortable", required = false, defaultValue = "createdAt") String sortable,
			@RequestParam(name = "direction", required = false, defaultValue = "DESC") String direction) {

		Pageable pageable = PageRequest.of(pageableDTO.getPage(),
				pageableDTO.getSize(),
				Sort.by(Direction.valueOf(direction), sortable));
		
		return new HttpResponse(HttpStatus.OK, true,
				commentService.getAllByOwner(restaurantId, pageable)).send();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOneComment(@PathVariable("id") Long id) {

		return new HttpResponse(HttpStatus.OK, true, commentService.getOne(id))
				.send();
	}

	@PostMapping
	public ResponseEntity<?> createComment(
			@Valid @RequestBody CreateCommentDTO createCommentDTO)
			throws UserNotFoundException, RestaurantNotFoundException {
		validatorUtil.validateEntity(createCommentDTO.getUserId(),
				createCommentDTO.getRestaurantId());

		return new HttpResponse(HttpStatus.CREATED, true,
				commentService.createOne(createCommentDTO)).send();
	};

	@GetMapping("/restaurant/{restaurantId}")
	public ResponseEntity<?> getOneByRestaurantId(
			@PathVariable Long restaurantId,
			@ModelAttribute PageableDTO pageableDTO
			) {
		
		return HttpResponse.sendOK(commentService.getAllByRestaurant(restaurantId, pageableDTO.get()));
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> search(
			@RequestParam(required = false) String query,
			@ModelAttribute PageableDTO dto) {
		
		return HttpResponse.sendOK(commentService.search(query, dto.get()));
	}
}
