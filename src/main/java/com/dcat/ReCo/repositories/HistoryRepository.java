package com.dcat.ReCo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcat.ReCo.models.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
	Optional<History> existsByUserId(Long userId);
}
