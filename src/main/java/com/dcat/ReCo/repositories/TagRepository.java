package com.dcat.ReCo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcat.ReCo.models.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{
	
}
