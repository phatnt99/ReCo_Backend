package com.dcat.ReCo.dtos.responses.review;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.dcat.ReCo.dtos.address.AddressBasic;

public interface ReviewOverview {
	Long getId();
	
	String getListPhoto();
	
	String getTitle();
	
	Double getPoint();
	
	ReviewUser getUser();
	
	ReviewRestaurant getRestaurant();
	
	@Value("#{target.userLikes.size()}")
	Integer getCountUserLike();
	
	Date getCreatedAt();
	
	interface ReviewUser {
		Long getId();
		String getFullName();
		String getAvatar();
	}
	
	interface ReviewRestaurant {
		Long getId();
		String getName();
		AddressBasic getAddress();
	}
}
