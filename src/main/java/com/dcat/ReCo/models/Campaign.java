package com.dcat.ReCo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "campaigns")
public class Campaign extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "additional")
	private String additional;

	@JsonIgnore() // Avoid recur references
	@ManyToOne()
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	public Campaign() {
		super();
	}

	public Campaign(String title, String note, String additional, Restaurant restaurant) {
		super();
		this.title = title;
		this.note = note;
		this.additional = additional;
		this.restaurant = restaurant;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAdditional() {
		return additional;
	}

	public void setAdditional(String additional) {
		this.additional = additional;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
}
