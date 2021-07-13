package com.dcat.ReCo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.dtos.HistoryCreateDTO;
import com.dcat.ReCo.models.History;
import com.dcat.ReCo.repositories.HistoryRepository;
import com.dcat.ReCo.repositories.RestaurantRepository;
import com.dcat.ReCo.repositories.UserRepository;

@Service
public class HistoryServiceImpl implements HistoryService {
	
	@Autowired
	private HistoryRepository historyRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private UserRepository userRepository;
		
	@Override
	public History createOne(HistoryCreateDTO dto) {
		
		History history = new History();
		history.setRestaurant(restaurantRepository.findById(dto.getRestaurantId()).orElse(null));
		history.setIp(dto.getIp());
		history.setUser(userRepository.findById(dto.getUserId()).orElse(null));
		
		return historyRepository.save(history);
	}
}
