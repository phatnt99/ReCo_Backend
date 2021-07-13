package com.dcat.ReCo.dtos.comment.projections;

import com.dcat.ReCo.dtos.restaurant.projections.RestaurantWithComment;
import com.dcat.ReCo.dtos.user.projections.UserWithComment;

public interface CommentWithDetail {
	
	String getId();

	String getContent();

	String getListPhoto();

	Integer getOverallStar();

	Integer getFoodStar();

	Integer getServiceStar();

	Integer getAimbianceStar();

	Integer getNoiseStar();

	String getPrivateNote();

	Integer getApproveStatus();
	
	UserWithComment getUser();
	
	RestaurantWithComment getRestaurant();
	
}
