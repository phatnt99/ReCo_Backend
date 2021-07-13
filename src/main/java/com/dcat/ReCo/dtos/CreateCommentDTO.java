package com.dcat.ReCo.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateCommentDTO extends CommentDTO {
	@NotNull(message = "is required")
	private Long userId;
	
	@NotNull(message = "is required")
	private Long restaurantId;
	
	private Long reservationId;
	
	@NotNull(message = "is required")
	private String content;
	
	private String listPhoto;
	
	@Min(value = 0, message = "invalid value") @Max(value = 5,  message = "invalid value")
	private Integer foodStar;
	
	@Min(value = 0, message = "invalid value") @Max(value = 5, message = "invalid value")
	private Integer serviceStar;
	
	@Min(value = 0, message = "invalid value") @Max(value = 5, message = "invalid value")
	private Integer aimbianceStar;
	
	@Min(value = 0, message = "invalid value") @Max(value = 5, message = "invalid value")
	private Integer noiseStar;
	
	private String privateNote;

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

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

}
