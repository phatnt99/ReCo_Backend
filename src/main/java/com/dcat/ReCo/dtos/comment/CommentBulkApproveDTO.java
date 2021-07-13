package com.dcat.ReCo.dtos.comment;

import com.dcat.ReCo.constants.ApproveStatusEnum;

public class CommentBulkApproveDTO extends CommentBulkActionDTO{
	
	ApproveStatusEnum status;

	public Integer getStatus() {
		return status.getValue();
	}

	public void setStatus(Integer status) {
		this.status = ApproveStatusEnum.getEnum(status);
	}
	
}
