package com.dcat.ReCo.models.ml;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dcat.ReCo.models.BaseModel;
import com.dcat.ReCo.models.Restaurant;

@Entity
@Table(name = "restaurant_distance")
public class ItemDistance extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "restaurant_base")
	private Restaurant restaurantBase;

	@ManyToOne
	@JoinColumn(name = "restaurant_child")
	private Restaurant restaurantChild;

	private Double distance;

	public Restaurant getRestaurantBase() {
		return restaurantBase;
	}

	public void setRestaurantBase(Restaurant restaurantBase) {
		this.restaurantBase = restaurantBase;
	}

	public Restaurant getRestaurantChild() {
		return restaurantChild;
	}

	public void setRestaurantChild(Restaurant restaurantChild) {
		this.restaurantChild = restaurantChild;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

}
