package com.dcat.ReCo.dtos.responses.restaurant;

import java.io.Serializable;

public class RestaurantOverview implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String logo;
	private String address;
	private String owner;
	private Integer approveStatus;

	

	public RestaurantOverview(Long id, String name, String logo, String address, String owner, Integer approveStatus) {
		super();
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.address = address;
		this.owner = owner;
		this.approveStatus = approveStatus;
	}

	public RestaurantOverview() {
		super();
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Integer getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(Integer approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

}
