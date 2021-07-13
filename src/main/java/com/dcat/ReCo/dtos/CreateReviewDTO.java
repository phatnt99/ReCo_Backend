package com.dcat.ReCo.dtos;

import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateReviewDTO extends ReviewDTO {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "is required")
	private Long userId;

	@NotNull(message = "is required")
	private Long restaurantId;

	private String title;

	@NotBlank(message = "cannot empty")
	private String content;

	@NotNull(message = "is required")
	@Digits(fraction = 1, integer = 2)
	private Double point;

	private String listPhoto;
	
	private List<Long> tags;

	public CreateReviewDTO() {
		super();
	}

	public CreateReviewDTO(@NotNull(message = "is required") Long userId,
			@NotNull(message = "is required") Long restaurantId, String title,
			@NotBlank(message = "cannot empty") String content,
			@NotNull(message = "is required") @Digits(fraction = 1, integer = 2) Double point, String listPhoto,
			List<Long> tags) {
		super();
		this.userId = userId;
		this.restaurantId = restaurantId;
		this.title = title;
		this.content = content;
		this.point = point;
		this.listPhoto = listPhoto;
		this.tags = tags;
	}

	public List<Long> getTags() {
		return tags;
	}

	public void setTags(List<Long> tags) {
		this.tags = tags;
	}

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
}
