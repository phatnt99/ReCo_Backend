package com.dcat.ReCo.dtos.user;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.dcat.ReCo.dtos.address.AddressBasic;
import com.dcat.ReCo.dtos.restaurant.RestaurantInUserDetail;
import com.dcat.ReCo.dtos.review.ReviewBasic;
import com.dcat.ReCo.dtos.tag.TagBasic;

public interface UserDetail extends UserBasic {
	
	String getUsername();
	
	LocalDate getDob();
	
	String getAvatar();
	
	String getEmail();
	
	String getPhone();
	
	Integer getGender();
	
	String getActiveAreaIds();
	
	AddressBasic getAddress();
	
	@Value("#{target.reviews.size()}")
	Integer getReviewCount();
	
	@Value("#{target.reservations.size()}")
	Integer getReservationCount();
	
	List<RestaurantInUserDetail> getFavoriteRestaurant();
	
	List<ReviewBasic> getFavoriteReview();
	
	List<TagBasic> getFavoriteTags();
	
}
