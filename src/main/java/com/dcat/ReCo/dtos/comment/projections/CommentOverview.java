package com.dcat.ReCo.dtos.comment.projections;

import java.util.Date;

import com.dcat.ReCo.dtos.restaurant.projections.RestaurantWithComment;
import com.dcat.ReCo.dtos.user.projections.UserWithComment;

public interface CommentOverview {

	Long getId();

	Date getCreatedAt();

	String getContent();

	String getListPhoto();

	Double getOverallStar();

//	Integer getFoodStar();
//
//	Integer getServiceStar();
//
//	Integer getAimbianceStar();
//
//	Integer getNoiseStar();

	Integer getApproveStatus();
	
	UserWithComment getUser();
	
	RestaurantWithComment getRestaurant();
}
