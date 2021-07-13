package com.dcat.ReCo.models;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.WhereJoinTable;

import com.dcat.ReCo.constants.UserStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users")
public class User extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String username;

	private String email;

	private String password;

	@Column(name = "full_name")
	private String fullName;

	private Integer gender;

	private LocalDate dob;

	private String phone;

	private String avatar;

	private Integer status;

	private String activeAreaIds;

	private String fcmToken;

	@OneToOne
	private Address address;

	@OneToOne
	private Role role;

	@OneToMany(mappedBy = "owner")
	List<Restaurant> restaurants;

	@OneToMany(mappedBy = "user")
	List<Review> reviews;

	@OneToMany(targetEntity = UserLike.class, mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	private List<UserLike> userLikeRestaurant;

	@OneToMany(mappedBy = "user")
	private List<Reservation> reservations;

	@ManyToMany
	@JoinTable(name = "user_like", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "likeable_id"))
	@WhereJoinTable(clause = "likeable_type = 'RESTAURANT'")
	@JsonIgnore
	private List<Restaurant> favoriteRestaurant;

	@OneToMany(mappedBy = "user", cascade = {
			CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH
	}, orphanRemoval = true)
	private List<UserFRestaurant> followedRestaurant;

	@OneToMany(mappedBy = "user", cascade = {
			CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH
	}, orphanRemoval = true)
	private List<UserFReview> followedReview;

	@ManyToMany
	@JoinTable(name = "user_like", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "likeable_id"))
	@WhereJoinTable(clause = "likeable_type = 'REVIEW'")
	@JsonIgnore
	private List<Review> favoriteReview;

	@ManyToMany
	@JoinTable(name = "user_tag", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	@JsonIgnore
	private List<Tag> favoriteTags;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<UserLike> getUserLikeRestaurant() {
		return userLikeRestaurant;
	}

	public void setUserLikeRestaurant(List<UserLike> userLikeRestaurant) {
		this.userLikeRestaurant = userLikeRestaurant;
	}

	public List<UserFRestaurant> getFollowedRestaurant() {
		return followedRestaurant;
	}

	public void setFollowedRestaurant(
			List<UserFRestaurant> followedRestaurant) {
		this.followedRestaurant = followedRestaurant;
	}

	public List<UserFReview> getFollowedReview() {
		return followedReview;
	}

	public void setFollowedReview(List<UserFReview> followedReview) {
		this.followedReview = followedReview;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Restaurant> getFavoriteRestaurant() {
		return favoriteRestaurant;
	}

	public void setFavoriteRestaurant(List<Restaurant> favoriteRestaurant) {
		this.favoriteRestaurant = favoriteRestaurant;
	}

	public List<Review> getFavoriteReview() {
		return favoriteReview;
	}

	public void setFavoriteReview(List<Review> favoriteReview) {
		this.favoriteReview = favoriteReview;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public List<Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(List<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public List<Tag> getFavoriteTags() {
		return favoriteTags;
	}

	public void setFavoriteTags(List<Tag> favoriteTags) {
		this.favoriteTags = favoriteTags;
	}

	public String getActiveAreaIds() {
		return activeAreaIds;
	}

	public void setActiveAreaIds(String activeAreaIds) {
		this.activeAreaIds = activeAreaIds;
	}

	@PrePersist
	public void setDefaultStatus() {
		this.status = UserStatusEnum.WAITING.getValue();
	}

}
