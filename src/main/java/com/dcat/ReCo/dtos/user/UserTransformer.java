package com.dcat.ReCo.dtos.user;

import java.time.LocalDate;
import java.util.Date;

import org.modelmapper.ModelMapper;

import com.dcat.ReCo.dtos.BaseTransformer;
import com.dcat.ReCo.dtos.JsonMapper;
import com.dcat.ReCo.dtos.address.AddressTransformer;
import com.dcat.ReCo.models.User;

public class UserTransformer extends BaseTransformer
		implements JsonMapper<UserTransformer, User> {

	private String    username;
	private String    email;
	private String    phone;
	private LocalDate dob;
	private Integer   gender;
	private String    fullName;
	private String    avatar;
	private Integer   status;
	// inner value
	private Long reservationCount;
	private Long restaurantCount;

	//
	AddressTransformer address;

	public UserTransformer() {
		super();
	}

	public UserTransformer(Long id, String fullName, String avatar,
			Date createdAt, Integer status,
			Long restaurantCount,
			Long reservationCount) {
		super();
		this.id               = id;
		this.fullName         = fullName;
		this.avatar           = avatar;
		this.status           = status;
		this.createdAt        = createdAt;
		this.reservationCount = reservationCount;
		this.restaurantCount  = restaurantCount;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public UserTransformer getJson(User model) {

		ModelMapper mapper = new ModelMapper();

		return mapper.map(model, UserTransformer.class);
	}

	public Long getReservationCount() {
		return reservationCount;
	}

	public void setReservationCount(Long reservationCount) {
		this.reservationCount = reservationCount;
	}

	public Long getRestaurantCount() {
		return restaurantCount;
	}

	public void setRestaurantCount(Long restaurantCount) {
		this.restaurantCount = restaurantCount;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public AddressTransformer getAddress() {
		return address;
	}

	public void setAddress(AddressTransformer address) {
		this.address = address;
	}

}
