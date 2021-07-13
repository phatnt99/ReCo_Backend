package com.dcat.ReCo.dtos.reservation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.dcat.ReCo.dtos.ReservationDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ReservationCreateDTO extends ReservationDTO {
	@NotNull(message = "is required")
	private Long userId;

	@NotNull(message = "is required")
	private Long restaurantId;

	private Integer type;

	@NotNull(message = "is required")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timeComing;

	private Integer partySize;

	private Long voucherId;

	private String fullName;

	private String phone;

	private String email;

	private String note;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public LocalDateTime getTimeComming() {
		return timeComing;
	}

	public void setTimeComming(LocalDateTime timeComming) {
		this.timeComing = timeComming;
	}

	public Integer getPartySize() {
		return partySize;
	}

	public void setPartySize(Integer partySize) {
		this.partySize = partySize;
	}

	public LocalDateTime getTimeComing() {
		return timeComing;
	}

	public void setTimeComing(String timeComing) {
		this.timeComing = LocalDateTime.parse(timeComing,
				DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
