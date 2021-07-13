package com.dcat.ReCo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "comments")
public class Comment extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "content")
	private String content;

	@Column(name = "list_photo")
	private String listPhoto;

	@Column(name = "overall_star")
	private Double overallStar;

	@Column(name = "food_star")
	private Integer foodStar;

	@Column(name = "service_star")
	private Integer serviceStar;

	@Column(name = "aimbiance_star")
	private Integer aimbianceStar;

	@Column(name = "noise_star")
	private Integer noiseStar;

	@Column(name = "private_note")
	private String privateNote;
	
	private Integer approveStatus;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	@JsonManagedReference
	private Restaurant restaurant;

	@OneToOne
	@JoinColumn(name = "reservation_id")
	private Reservation reservation;

	public Comment() {
		super();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getListPhoto() {
		return listPhoto;
	}

	public void setListPhoto(String listPhoto) {
		this.listPhoto = listPhoto;
	}

	public Double getOverallStar() {
		return overallStar;
	}

	public void setOverallStar(Double overallStar) {
		this.overallStar = overallStar;
	}

	public Integer getFoodStar() {
		return foodStar;
	}

	public void setFoodStar(Integer foodStar) {
		this.foodStar = foodStar;
	}

	public Integer getServiceStar() {
		return serviceStar;
	}

	public void setServiceStar(Integer serviceStar) {
		this.serviceStar = serviceStar;
	}

	public Integer getAimbianceStar() {
		return aimbianceStar;
	}

	public void setAimbianceStar(Integer aimbianceStar) {
		this.aimbianceStar = aimbianceStar;
	}

	public Integer getNoiseStar() {
		return noiseStar;
	}

	public void setNoiseStar(Integer noiseStar) {
		this.noiseStar = noiseStar;
	}

	public String getPrivateNote() {
		return privateNote;
	}

	public void setPrivateNote(String privateNote) {
		this.privateNote = privateNote;
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

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	
	

}
