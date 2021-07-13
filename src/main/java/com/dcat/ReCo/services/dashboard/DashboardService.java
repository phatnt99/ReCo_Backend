package com.dcat.ReCo.services.dashboard;

import com.dcat.ReCo.models.Dashboard;

public interface DashboardService {

	Dashboard getDashboard(String year);
	
	Dashboard getOnwerDashboard(String year);

}