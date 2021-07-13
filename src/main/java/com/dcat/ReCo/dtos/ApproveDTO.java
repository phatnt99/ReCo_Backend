package com.dcat.ReCo.dtos;

import java.util.List;

public abstract class ApproveDTO<T> {
	protected List<Long> ids;
	protected T          type;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public abstract Integer getType();

	public abstract void setType(Integer type);

}
