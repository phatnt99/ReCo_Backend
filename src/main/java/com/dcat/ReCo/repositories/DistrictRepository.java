package com.dcat.ReCo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dcat.ReCo.dtos.dashboard.DashboardDTO;
import com.dcat.ReCo.models.District;

public interface DistrictRepository extends JpaRepository<District, Long> {
	
}
