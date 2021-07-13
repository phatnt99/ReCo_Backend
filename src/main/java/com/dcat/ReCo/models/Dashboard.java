package com.dcat.ReCo.models;

import java.util.List;

import com.dcat.ReCo.dtos.DistrictCountDTO;
import com.dcat.ReCo.dtos.MonthCount;
import com.dcat.ReCo.dtos.RestaurantTagDTO;
import com.dcat.ReCo.dtos.comment.projections.CommentOverview;

public class Dashboard {
	Long restaurantCount;
	Long userCount;
	Long reservationCount;
	Long reviewCount;

	List<RestaurantTagDTO> tagAndRestaurants;
	List<DistrictCountDTO> districtAndRestaurants;
	List<MonthCount>       monthAndReservations;
	List<MonthCount>       monthAndReviews;
	List<CommentOverview>  comments;

	public Long getRestaurantCount() {
		return restaurantCount;
	}

	public void setRestaurantCount(Long restaurantCount) {
		this.restaurantCount = restaurantCount;
	}

	public Long getUserCount() {
		return userCount;
	}

	public void setUserCount(Long userCount) {
		this.userCount = userCount;
	}

	public Long getReservationCount() {
		return reservationCount;
	}

	public void setReservationCount(Long reservationCount) {
		this.reservationCount = reservationCount;
	}

	public Long getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Long reviewCount) {
		this.reviewCount = reviewCount;
	}

	public List<RestaurantTagDTO> getTagAndRestaurants() {
		return tagAndRestaurants;
	}

	public void setTagAndRestaurants(List<RestaurantTagDTO> tagAndRestaurants) {
		this.tagAndRestaurants = tagAndRestaurants;
	}

	public List<DistrictCountDTO> getDistrictAndRestaurants() {
		return districtAndRestaurants;
	}

	public void setDistrictAndRestaurants(
			List<DistrictCountDTO> districtAndRestaurants) {
		this.districtAndRestaurants = districtAndRestaurants;
	}

	public List<MonthCount> getMonthAndReservations() {
		return monthAndReservations;
	}

	public void setMonthAndReservations(List<MonthCount> monthAndReservations) {
		this.monthAndReservations = monthAndReservations;
	}

	public List<MonthCount> getMonthAndReviews() {
		return monthAndReviews;
	}

	public void setMonthAndReviews(List<MonthCount> monthAndReviews) {
		this.monthAndReviews = monthAndReviews;
	}

	public List<CommentOverview> getComments() {
		return comments;
	}

	public void setComments(List<CommentOverview> comments) {
		this.comments = comments;
	}

}
