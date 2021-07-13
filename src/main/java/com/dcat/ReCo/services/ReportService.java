package com.dcat.ReCo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dcat.ReCo.dtos.ReportCreateDTO;
import com.dcat.ReCo.models.Report;

public interface ReportService {

	Page<Report> getAll(Pageable pageable);

	Page<Report> getByOwner(Long ownerId, Pageable pageable);

	Report createOne(ReportCreateDTO dto);
	
	Page<Report> search(String query, Pageable pageable);

}