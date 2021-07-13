package com.dcat.ReCo.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.dtos.PageableDTO;
import com.dcat.ReCo.services.RestaurantService;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
@RequestMapping("/admin/restaurants")
public class AdminRestaurantController {

	@Autowired
	private RestaurantService restaurantService;

	@GetMapping
	public ResponseEntity<?> getAll(@ModelAttribute PageableDTO pageableDto) {

		Pageable pageable = PageRequest.of(pageableDto.getPage(), pageableDto.getSize());

		return new HttpResponse(HttpStatus.OK, true, restaurantService.getAll(pageable)).sendWithPaginateHeaders();
	}
	
}
