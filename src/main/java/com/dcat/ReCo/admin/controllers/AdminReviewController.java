package com.dcat.ReCo.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.dtos.PageableDTO;
import com.dcat.ReCo.models.Review_;
import com.dcat.ReCo.services.review.ReviewService;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
@RequestMapping("/admin/reviews")
public class AdminReviewController {

	@Autowired
	private ReviewService reivewService;

	@GetMapping
	public ResponseEntity<?> getAll(@ModelAttribute PageableDTO pageableDto) {
		Pageable pageable = PageRequest.of(pageableDto.getPage(), pageableDto.getSize(),
				Sort.by(Review_.CREATED_AT).descending());

		return new HttpResponse(HttpStatus.OK, true, reivewService.getAllOverview(pageable)).send();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteOneReview(@PathVariable Long id) {
		
		reivewService.deleteOne(id);
		
		return HttpResponse.sendNoContent();
	}
}
