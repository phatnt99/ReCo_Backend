package com.dcat.ReCo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dcat.ReCo.services.dashboard.DashboardService;
import com.dcat.ReCo.utils.https.HttpResponse;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	
	@Autowired
	private DashboardService dashboardService;
	
	@GetMapping
	public ResponseEntity<?> getAdminDashboard(
			@RequestParam(required = false) String year) {
		
		return HttpResponse.sendOK(dashboardService.getDashboard(year));
	}
	
	@GetMapping("/owner")
	public ResponseEntity<?> getOwnerDashboard(
			@RequestParam(required = false) String year) {
		
		return HttpResponse.sendOK(dashboardService.getOnwerDashboard(year));
	}
	
}
