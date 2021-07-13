package com.dcat.ReCo.dtos.review;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import com.dcat.ReCo.dtos.responses.review.ReviewOverview.ReviewUser;
import com.dcat.ReCo.dtos.restaurant.RestaurantBasic;

public interface ReviewBasic {

	Long getId();

	String getListPhoto();

	String getTitle();

	Double getPoint();

	ReviewUser getUser();

	@Value("#{target.userLikes.size()}")
	Integer getCountUserLike();

	Date getCreatedAt();

	RestaurantBasic getRestaurant();
}
