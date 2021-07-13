package com.dcat.ReCo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.dtos.responses.UserProfileResponse;
import com.dcat.ReCo.models.AuthUser;
import com.dcat.ReCo.models.User;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
@RequestMapping("/me")
public class MeController {
	@GetMapping
	public ResponseEntity<?> getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		
		User auth = null;
		
		try {
			auth = ((AuthUser) authentication.getPrincipal()).getUser();

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return new HttpResponse(HttpStatus.OK, true,
				new UserProfileResponse().getResponse(auth)).send();
	}
}
