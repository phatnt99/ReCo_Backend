package com.dcat.ReCo.dtos;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.dcat.ReCo.models.Tag;

public class RestaurantCB {
	private Long       id;
	private Long       district;
	private List<Long> tags;

	public RestaurantCB(Long id, Long district, Collection<Tag> tags) {
		super();
		this.id       = id;
		this.district = district;
		this.tags = tags.stream().map(tag -> tag.getId()).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public Long getDistrict() {
		return district;
	}

	public List<Long> getTags() {
		return tags;
	}

}
