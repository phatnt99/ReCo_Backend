package com.dcat.ReCo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dcat.ReCo.models.Notification;
import com.dcat.ReCo.repositories.NotificationRepository;

@Service
public class NotiServiceImpl implements NotiService {

	@Autowired
	private NotificationRepository notiRepository;

	@Override
	public Page<Notification> getAllByToken(Long userId, Pageable pageable) {
		
		return notiRepository.findByUserIdOrIsAllOrderByCreatedAtDesc(userId, 1, pageable);
	}
}
