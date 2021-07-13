package com.dcat.ReCo.dtos.user;

import java.util.List;

public class UserProfileUpdateDTO {

	Long         userId;
	List<Long>   tagId;
	List<String> areas;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Long> getTagId() {
		return tagId;
	}

	public void setTagId(List<Long> tagId) {
		this.tagId = tagId;
	}

	public List<String> getAreas() {
		return areas;
	}

	public void setAreas(List<String> areas) {
		this.areas = areas;
	}

}
