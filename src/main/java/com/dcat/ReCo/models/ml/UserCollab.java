package com.dcat.ReCo.models.ml;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dcat.ReCo.models.BaseModel;
import com.dcat.ReCo.models.Restaurant;
import com.dcat.ReCo.models.User;

@Entity
@Table(name = "user_collab")
public class UserCollab extends BaseModel {

	private static final long serialVersionUID = 1L;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User       user;
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;
	private Double     predict;

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

	public Double getPredict() {
		return predict;
	}

	public void setPredict(Double predict) {
		this.predict = predict;
	}

}
