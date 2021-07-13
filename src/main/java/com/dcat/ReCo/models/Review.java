package com.dcat.ReCo.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.WhereJoinTable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Entity
@Table(name = "reviews")
@Document(indexName = "reviewidx")
public class Review extends BaseModel {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	@Column(name = "title")
	@Field(type = FieldType.Text, name = "title")
	private String title;

	@Column(name = "content")
	@Field(type = FieldType.Text, name = "content")
	private String content;

	@Column(name = "point")
	private Double point;

	@Column(name = "list_photo")
	private String listPhoto;

	@Column(name = "approve_status")
	private Integer approveStatus;

	@ManyToMany
	@JoinTable(name = "review_tag", joinColumns = @JoinColumn(name = "review_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<Tag> tags;

	@ManyToMany
	@JoinTable(name = "user_like", joinColumns = @JoinColumn(name = "likeable_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	@WhereJoinTable(clause = "likeable_type='REVIEW'")
	private List<User> userLikes;

	// for full text search
	@Transient
	private String restaurantName;

	@Transient
	private String userName;

	public Review() {
		super();
	}

	public Review(User user, Restaurant restaurant, String title,
			String content, Double point, String listPhoto,
			Integer approveStatus, List<Tag> tags) {
		super();
		this.user          = user;
		this.restaurant    = restaurant;
		this.title         = title;
		this.content       = content;
		this.point         = point;
		this.listPhoto     = listPhoto;
		this.approveStatus = approveStatus;
		this.tags          = tags;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Double getPoint() {
		return point;
	}

	public void setPoint(Double point) {
		this.point = point;
	}

	public String getListPhoto() {
		return listPhoto;
	}

	public void setListPhoto(String listPhoto) {
		this.listPhoto = listPhoto;
	}

	public Integer getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(Integer approveStatus) {
		this.approveStatus = approveStatus;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<User> getUserLikes() {
		return userLikes;
	}

	public void setUserLikes(List<User> userLikes) {
		this.userLikes = userLikes;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
