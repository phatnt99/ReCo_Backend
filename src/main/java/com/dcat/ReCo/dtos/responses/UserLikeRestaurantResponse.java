package com.dcat.ReCo.dtos.responses;

import java.io.Serializable;

import com.dcat.ReCo.models.Address;
import com.dcat.ReCo.models.UserLike;

public class UserLikeRestaurantResponse implements ResponseMapping<UserLikeRestaurantResponse, UserLike>, Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Restaurant restaurant;
	private Integer value;

	public UserLikeRestaurantResponse() {
		super();
	}

	public UserLikeRestaurantResponse(Long id, Restaurant restaurant, Integer value) {
		super();
		this.id = id;
		this.restaurant = restaurant;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public UserLikeRestaurantResponse getResponse(UserLike model) {

		return new UserLikeRestaurantResponse(model.getId(), 
				new Restaurant(model.getRestaurant().getId(), model.getRestaurant().getName(), model.getRestaurant().getAddress(), model.getRestaurant().getStarAverage()),
				model.getValue());
	}
	
	public class Restaurant implements Serializable {

		private static final long serialVersionUID = 1L;
		private Long id;
		private String name;
		private Address address;
		private Double starAverage;
		
		public Restaurant(Long id, String name, Address address, Double starAverage) {
			super();
			this.id = id;
			this.name = name;
			this.address = address;
			this.starAverage = starAverage;
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
		
	}
}
