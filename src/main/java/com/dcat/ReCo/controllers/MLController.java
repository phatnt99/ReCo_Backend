package com.dcat.ReCo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.dtos.user.UserProfileUpdateDTO;
import com.dcat.ReCo.services.ml.SparkService;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
@RequestMapping("/ml")
public class MLController {

	@Autowired
	private SparkService sparkService;

	@PostMapping("/pcb")
	public ResponseEntity<?> getContentBasedProfile(
			@RequestBody UserProfileUpdateDTO dto) {

		sparkService.doContentBasedProfile(dto);

		return HttpResponse.sendNoContent();
	}

	@GetMapping("/icb")
	public ResponseEntity<?> getContentBasedItem(
			@RequestParam Long restaurantId) {

		sparkService.doContentBasedItem(restaurantId);

		return HttpResponse.sendNoContent();
	}

	@GetMapping("/ucb")
	public ResponseEntity<?> getUserBasedCollab(
			@RequestParam Long userId) {
		sparkService
		.doUserCollab2(Long.parseLong(userId.toString()));
		return HttpResponse.sendNoContent();
	}
	
	@GetMapping("/aicb")
	public ResponseEntity<?> preprocess() {
		
		sparkService.doAll();
		
		return HttpResponse.sendNoContent();
	}
}
