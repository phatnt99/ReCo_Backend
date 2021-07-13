package com.dcat.ReCo.dtos;

import com.dcat.ReCo.constants.ToggleUpdateEnum;

public class FollowableDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;
	
	private Long followableId;
	
	private ToggleUpdateEnum type;

	public FollowableDTO() {
		super();
	}
	
	public FollowableDTO(Long followableId, ToggleUpdateEnum type) {
		super();
		this.followableId = followableId;
		this.type = type;
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
