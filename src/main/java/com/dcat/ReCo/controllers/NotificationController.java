package com.dcat.ReCo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.dtos.PageableDTO;
import com.dcat.ReCo.services.NotiService;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
public class NotificationController {
	
	@Autowired
	private NotiService notiService;

	@GetMapping("noti")
	public ResponseEntity<?> getNotis(@ModelAttribute PageableDTO pageable,
			@RequestParam Long userId) {

		return HttpResponse.sendOK(notiService.getAllByToken(userId, pageable.get()));
	}
}
