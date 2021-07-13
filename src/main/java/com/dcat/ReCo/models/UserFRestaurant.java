package com.dcat.ReCo.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "user_like")
@Where(clause = "likeable_type = 'RESTAURANT'")
public class UserFRestaurant extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

	@ManyToOne
	@JoinColumn(name = "likeable_id")
	private Restaurant restaurant;

	private String likeableType;

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

	public String getLikeableType() {
		return likeableType;
	}

	public void setLikeableType(String likeableType) {
		this.likeableType = likeableType;
	}
	
	@PrePersist
	public void setRestaurantType() {
		setLikeableType("RESTAURANT");
	}

}
