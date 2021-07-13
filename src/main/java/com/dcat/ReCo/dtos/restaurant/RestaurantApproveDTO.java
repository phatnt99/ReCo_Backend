package com.dcat.ReCo.dtos.restaurant;

import com.dcat.ReCo.constants.ApproveStatusEnum;
import com.dcat.ReCo.dtos.ApproveDTO;

public class RestaurantApproveDTO extends ApproveDTO<ApproveStatusEnum> {

	@Override
	public Integer getType() {
		
		return this.type.getValue();
	}

	@Override
	public void setType(Integer type) {
		this.type = ApproveStatusEnum.getEnum(type);
	}

}
