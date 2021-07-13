package com.dcat.ReCo.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dcat.ReCo.models.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
	public Page<Notification> findByUserIdOrIsAllOrderByCreatedAtDesc(Long userId, Integer isAll, Pageable pageable);

}
