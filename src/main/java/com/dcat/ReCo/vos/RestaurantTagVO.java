package com.dcat.ReCo.vos;

public class RestaurantTagVO {
	private Long restaurantId;
	private Long tagId;

	public RestaurantTagVO() {
		super();
	}

	public RestaurantTagVO(Long restaurantId, Long tagId) {
		super();
		this.restaurantId = restaurantId;
		this.tagId        = tagId;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

}
