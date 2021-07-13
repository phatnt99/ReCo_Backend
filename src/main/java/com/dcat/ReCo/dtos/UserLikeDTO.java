package com.dcat.ReCo.dtos;

import com.dcat.ReCo.constants.ToggleUpdateEnum;

public class UserLikeDTO {
	Long    userId;
	Long    followableId;
	ToggleUpdateEnum type; // 1.fo 2.un

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFollowableId() {
		return followableId;
	}

	public void setFollowableId(Long followableId) {
		this.followableId = followableId;
	}

	public ToggleUpdateEnum getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = ToggleUpdateEnum.getEnum(type);
	}

}
