package com.dcat.ReCo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.dtos.UserLikeDTO;
import com.dcat.ReCo.services.UserLikeService;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
public class FollowController {

	@Autowired
	private UserLikeService userLikeService;

	@RequestMapping(path = "/restaurants/follow", method = RequestMethod.POST)
	public ResponseEntity<?> followRestaurant(@RequestBody UserLikeDTO dto) {

		userLikeService.updateFollowingRestaurant(dto);

		return HttpResponse.sendNoContent();
	}

	@RequestMapping(path = "/reviews/follow", method = RequestMethod.POST)
	public ResponseEntity<?> followReview(
			@RequestBody UserLikeDTO dto) {

		userLikeService.updateFollowingReview(dto);

		return HttpResponse.sendNoContent();
	}
}
