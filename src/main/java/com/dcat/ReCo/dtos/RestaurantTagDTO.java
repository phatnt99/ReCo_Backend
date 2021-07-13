package com.dcat.ReCo.dtos;

import java.io.Serializable;

import com.dcat.ReCo.models.Tag;

public class RestaurantTagDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Tag tag;
	
	private Long countTag;
	
	public RestaurantTagDTO() {
		super();
	}

	public RestaurantTagDTO(Tag tag, Long countTag) {
		super();
		this.tag = tag;
		this.countTag = countTag;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public Long getCountTag() {
		return countTag;
	}

	public void setCountTag(Long countTag) {
		this.countTag = countTag;
	}
	
	
}
