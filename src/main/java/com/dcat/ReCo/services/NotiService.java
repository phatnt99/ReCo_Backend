package com.dcat.ReCo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dcat.ReCo.models.Notification;

public interface NotiService {

	Page<Notification> getAllByToken(Long userId, Pageable pageable);

}