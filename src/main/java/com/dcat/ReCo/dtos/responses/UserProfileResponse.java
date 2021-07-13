package com.dcat.ReCo.dtos.responses;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.dcat.ReCo.models.Address;
import com.dcat.ReCo.models.Review;
import com.dcat.ReCo.models.Role;
import com.dcat.ReCo.models.User;

public class UserProfileResponse implements ResponseMapping<UserProfileResponse, User>, Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String email;
	private String fullName;
	private Integer gender;
	private String avatar;
	private Integer status;
	private Address address;
	private Role role;
	List<Review> reviews;
	private List<UserLikeRestaurantResponse> lsFollowRestaurant;

	public UserProfileResponse() {
		super();
	}

	private UserProfileResponse(Long id, String username, String email, String fullName, Integer gender, String avatar,
			Integer status, Address address, Role role, List<Review> reviews, List<UserLikeRestaurantResponse> lsUserLikeRestaurant) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.fullName = fullName;
		this.gender = gender;
		this.avatar = avatar;
		this.status = status;
		this.address = address;
		this.role = role;
		this.reviews = reviews;
		this.lsFollowRestaurant = lsUserLikeRestaurant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<UserLikeRestaurantResponse> getLsFollowRestaurant() {
		return lsFollowRestaurant;
	}

	public void setLsFollowRestaurant(List<UserLikeRestaurantResponse> lsFollowRestaurant) {
		this.lsFollowRestaurant = lsFollowRestaurant;
	}

	@Override
	public UserProfileResponse getResponse(User model) {

		// resolve UserLikeRestaurant
		List<UserLikeRestaurantResponse> collection = model.getUserLikeRestaurant().stream()
				.map(ul -> new UserLikeRestaurantResponse().getResponse(ul)).collect(Collectors.toList());
		
		return new UserProfileResponse(model.getId(), model.getUsername(), model.getEmail(), model.getFullName(),
				model.getGender(), model.getAvatar(), model.getStatus(), model.getAddress(), model.getRole(), null,
				collection);
	}

}
