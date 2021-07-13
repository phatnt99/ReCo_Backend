package com.dcat.ReCo.dtos;

public class DistrictCountDTO {
	private String name;
	private Long   count;

	public DistrictCountDTO(String name, Long count) {
		super();
		this.setName(name);
		this.setCount(count);
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
