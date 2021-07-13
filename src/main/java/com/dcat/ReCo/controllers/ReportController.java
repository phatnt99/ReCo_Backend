package com.dcat.ReCo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.dtos.PageableDTO;
import com.dcat.ReCo.dtos.ReportCreateDTO;
import com.dcat.ReCo.services.ReportService;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
@RequestMapping("/reports")
public class ReportController {
	
	@Autowired
	private ReportService reportService;

	@GetMapping
	public ResponseEntity<?> getAll(@ModelAttribute PageableDTO pageable) {
		
		return HttpResponse.sendOK(reportService.getAll(pageable.get()));
	}
	
	@GetMapping("/owner/{id}")
	public ResponseEntity<?> getAllByOwner(@PathVariable Long id,@ModelAttribute PageableDTO pageable) {
		
		return HttpResponse.sendOK(reportService.getByOwner(id, pageable.get()));
	}
	
	@PostMapping
	public ResponseEntity<?> createReport(@RequestBody ReportCreateDTO dto) {
		
		return HttpResponse.sendCreated(reportService.createOne(dto));
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> search(
			@RequestParam(required = false) String query,
			@ModelAttribute PageableDTO dto) {
		
		return HttpResponse.sendOK(reportService.search(query, dto.get()));
	}
}
