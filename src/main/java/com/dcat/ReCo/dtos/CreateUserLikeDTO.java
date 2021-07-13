package com.dcat.ReCo.dtos;

import com.dcat.ReCo.models.Restaurant;
import com.dcat.ReCo.models.User;

public class CreateUserLikeDTO {
	private User user;
	private Restaurant restaurant;
	private String type;
	
	public CreateUserLikeDTO(User user, Restaurant restaurant, String type) {
		super();
		this.user = user;
		this.restaurant = restaurant;
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	
}
