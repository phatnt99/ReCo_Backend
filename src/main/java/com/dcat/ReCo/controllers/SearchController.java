package com.dcat.ReCo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.dtos.PageableDTO;
import com.dcat.ReCo.services.SearchService;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchService searchService;

	@GetMapping("/review")
	public ResponseEntity<?> doSearchReview(
			@RequestParam(name = "query", defaultValue = "") String content, @ModelAttribute PageableDTO pageable) {

		return HttpResponse.sendOK(searchService.searchReview(content, pageable.get(false)));
	}
	
	@GetMapping("/restaurant")
	public ResponseEntity<?> doSearchRestaurant(
			@RequestParam(name = "longtitude", required = false, defaultValue = "-1") Double longtitude,
			@RequestParam(name = "latitude", required = false, defaultValue = "-1") Double latitude,
			@RequestParam(name = "type", required = false, defaultValue = "") String type,
			@RequestParam(name = "query", defaultValue = "") String content, @ModelAttribute PageableDTO pageable) {

		return HttpResponse.sendOK(searchService.searchRestaurant(content, pageable.get(false), type, longtitude, latitude));
	}
}
