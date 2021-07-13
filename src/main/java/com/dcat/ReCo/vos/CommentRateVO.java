package com.dcat.ReCo.vos;

import java.io.Serializable;

public class CommentRateVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long   userId;
	private Long   restaurantId;
	private Double rating;

	public CommentRateVO(Long userId, Long restaurantId, Double rating) {
		super();
		this.userId       = userId;
		this.restaurantId = restaurantId;
		this.rating       = rating;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

}
