package com.dcat.ReCo.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_like")
@Where(clause = "likeable_type = 'REVIEW'")
public class UserFReview extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

	@ManyToOne
	@JoinColumn(name = "likeable_id")
	@JsonIgnore
	private Review review;

	private String likeableType;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public String getLikeableType() {
		return likeableType;
	}

	public void setLikeableType(String likeableType) {
		this.likeableType = likeableType;
	}
	
	@PrePersist
	public void setRestaurantType() {
		setLikeableType("REVIEW");
	}

}
