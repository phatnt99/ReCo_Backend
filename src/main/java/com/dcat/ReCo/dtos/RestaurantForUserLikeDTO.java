package com.dcat.ReCo.dtos;

import com.dcat.ReCo.models.Address;
import com.dcat.ReCo.models.Restaurant;

public class RestaurantForUserLikeDTO extends BaseDTO implements DTOMapping<RestaurantForUserLikeDTO, Restaurant> {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Address address;
	private Double starAverage;
	
	

	public RestaurantForUserLikeDTO(Long id, String name, Address address,
			Double starAverage) {
		super();
		this.id          = id;
		this.name        = name;
		this.address     = address;
		this.starAverage = starAverage;
	}

	public RestaurantForUserLikeDTO() {
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Double getStarAverage() {
		return starAverage;
	}

	public void setStarAverage(Double starAverage) {
		this.starAverage = starAverage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public RestaurantForUserLikeDTO getDTO(Restaurant model) {

		return new RestaurantForUserLikeDTO(model.getId(), model.getName(), model.getAddress(), model.getStarAverage());
	}

}
