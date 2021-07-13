package com.dcat.ReCo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dcat.ReCo.models.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
	
	Page<Report> findByRestaurantOwnerIdAndTypeNot(Long id, Integer type, Pageable pagealbe);
	
	@Query("select r from Report r join r.restaurant re where re.name like %:query%")
	Page<Report> findAllForSearch(String query, Pageable pageable);
}

