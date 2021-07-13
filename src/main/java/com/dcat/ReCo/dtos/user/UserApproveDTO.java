package com.dcat.ReCo.dtos.user;

import com.dcat.ReCo.constants.UserStatusEnum;
import com.dcat.ReCo.dtos.ApproveDTO;

public class UserApproveDTO extends ApproveDTO<UserStatusEnum> {

	@Override
	public Integer getType() {
		
		return this.type.getValue();
	}

	@Override
	public void setType(Integer type) {
		
		this.type = UserStatusEnum.fromInt(type);
	}
	
}
