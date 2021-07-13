package com.dcat.ReCo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dcat.ReCo.services.firebase.FirebaseService;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
@RequestMapping("/upload-image")
public class ImageController {

	@PostMapping
	public ResponseEntity<?> index(@RequestParam("image") MultipartFile multipartFile) {
		FirebaseService fbService = FirebaseService.getInstance();

		return new HttpResponse(HttpStatus.OK, true, fbService.uploadImage(multipartFile)).send();
	}
}
