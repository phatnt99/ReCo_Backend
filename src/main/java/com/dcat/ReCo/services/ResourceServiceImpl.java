package com.dcat.ReCo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.dtos.RestaurantTagDTO;
import com.dcat.ReCo.models.Resource;
import com.dcat.ReCo.models.Tag;
import com.dcat.ReCo.repositories.ResourceRepository;

@Service
public class ResourceServiceImpl implements ResourceService {
	
	@Autowired
	private ResourceRepository restaurantTagRepository;
	
	public List<Resource> getAll() {
		return restaurantTagRepository.findAll();
	}

	@Override
	public Page<RestaurantTagDTO> getAllTagWithAnalys(Pageable pagealbe) {
		
		return restaurantTagRepository.findAllTagWithAnalys(pagealbe);
	}

	@Override
	public Tag getOneTag(Long id) {
		return restaurantTagRepository.findOneTag(id);
	}

	@Override
	public List<RestaurantTagDTO> getAllTagWithAnalys() {
		
		return restaurantTagRepository.findAllTagWithAnalys();
	}

	@Override
	public Page<RestaurantTagDTO> search(String query, Pageable pageable) {
		
		return restaurantTagRepository.search(query, pageable);
	}
	
}
