package com.dcat.ReCo.dtos.responses;

import java.io.Serializable;

// Projections
public class RestaurantCheckIn implements Serializable {

	private static final long serialVersionUID = 1L;
	Long id;
	String name;
	String logo;
	String detail;

	public RestaurantCheckIn() {
		super();
	}

	public RestaurantCheckIn(Long id, String name, String logo, String detail) {
		super();
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.detail = detail;
	}

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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
