package com.dcat.ReCo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.dtos.PageableDTO;
import com.dcat.ReCo.services.ResourceService;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
@RequestMapping("/resources")
public class TagController {

	@Autowired
	ResourceService restaurantTagService;

	@GetMapping
	public ResponseEntity<?> getFilterResource() {
		return new HttpResponse(HttpStatus.OK, true, restaurantTagService.getAll()).send();
	}

	@GetMapping("/tags")
	public ResponseEntity<?> getAllTags() {

		return new HttpResponse(HttpStatus.OK, true, restaurantTagService.getAllTagWithAnalys()).send();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOneTag(@PathVariable Long id) {

		return HttpResponse.sendOK(restaurantTagService.getOneTag(id));
	}

	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam(name = "query", required = false) String query,
			@ModelAttribute PageableDTO pageableDto) {

		return HttpResponse.sendOK(restaurantTagService.search(query, pageableDto.get()));
	}
}
