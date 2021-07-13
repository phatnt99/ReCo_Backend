package com.dcat.ReCo.dtos.user.projections;

import java.util.Date;

public class DinnerOverviewDTO {

	Long   id;
	String fullName;
	Long   reservationCount;
	Date   lastReservationTime;
	Long   reviewCount;
	Long   likeCount;
	
	Date   createdAt;

	public DinnerOverviewDTO(Long id, String fullName, Long reservationCount,
			Long reviewCount, Date createAt) {
		super();
		this.id               = id;
		this.fullName         = fullName;
		this.reservationCount = reservationCount;
		this.reviewCount      = reviewCount;
		this.createdAt         = createAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getReservationCount() {
		return reservationCount;
	}

	public void setReservationCount(Long reservationCount) {
		this.reservationCount = reservationCount;
	}

	public Date getLastReservationTime() {
		return lastReservationTime;
	}

	public void setLastReservationTime(Date lastReservationTime) {
		this.lastReservationTime = lastReservationTime;
	}

	public Long getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Long reviewCount) {
		this.reviewCount = reviewCount;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createAt) {
		this.createdAt = createAt;
	}

}
