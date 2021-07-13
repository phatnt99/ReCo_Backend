package com.dcat.ReCo.dtos.restaurant;

import com.dcat.ReCo.constants.ApproveStatusEnum;

public class ApproveRestaurantDTO {

	Long restaurantId;
	ApproveStatusEnum status;

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public ApproveStatusEnum getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = ApproveStatusEnum.getEnum(status);
	}

}
