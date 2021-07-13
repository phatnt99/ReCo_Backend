package com.dcat.ReCo.vos;

import java.io.Serializable;

public class EuclidProfileVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long   userId;
	private Long   resId;
	private Double distance;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getResId() {
		return resId;
	}

	public void setResId(Long resId) {
		this.resId = resId;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

}
