package com.dcat.ReCo.dtos.reservation;

import java.util.List;

import com.dcat.ReCo.constants.ReservationType;

public class ReservationUpdateDTO {
	private List<Long>      ids;
	private ReservationType type;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Integer getType() {
		return type.getValue();
	}

	public void setType(Integer type) {
		this.type = ReservationType.fromInt(type);
	}

}
