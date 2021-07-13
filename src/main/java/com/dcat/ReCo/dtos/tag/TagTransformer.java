package com.dcat.ReCo.dtos.tag;

import com.dcat.ReCo.dtos.BaseTransformer;

public class TagTransformer extends BaseTransformer {
	Long   id;
	String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
