package com.dcat.ReCo.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dcat.ReCo.dtos.RestaurantTagDTO;
import com.dcat.ReCo.models.Resource;
import com.dcat.ReCo.models.Tag;

public interface ResourceService {
	public List<Resource> getAll();
	public List<RestaurantTagDTO> getAllTagWithAnalys();
	public Page<RestaurantTagDTO> getAllTagWithAnalys(Pageable pageable);
	public Tag getOneTag(Long id);
	public Page<RestaurantTagDTO> search(String query, Pageable pageable);
}
