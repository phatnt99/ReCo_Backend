package com.dcat.ReCo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.dtos.HistoryCreateDTO;
import com.dcat.ReCo.services.HistoryService;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
@RequestMapping("/history")
public class HistoryController {
	
	@Autowired
	private HistoryService historyService;
	
	@PostMapping
	public ResponseEntity<?> createHistory(
			@RequestBody HistoryCreateDTO dto) {		
		
		return HttpResponse.sendCreated(historyService.createOne(dto));
	}

}
