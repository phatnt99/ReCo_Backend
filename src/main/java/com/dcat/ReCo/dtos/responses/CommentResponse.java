package com.dcat.ReCo.dtos.responses;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.dcat.ReCo.models.Comment;

public class CommentResponse implements ResponseMapping<CommentResponse, Comment>, Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String content;

	private String listPhoto;

	private Integer overallStar;

	private Integer foodStar;

	private Integer serviceStar;

	private Integer aimbianceStar;

	private Integer noiseStar;

	private String privateNote;

	private Integer approveStatus;
	
	private Long restaurant_id;
	
	private Date createdAt;
	
	private Date updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getOverallStar() {
		return overallStar;
	}

	public void setOverallStar(Integer overallStar) {
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

	public Integer getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(Integer approveStatus) {
		this.approveStatus = approveStatus;
	}

	public Long getRestaurant_id() {
		return restaurant_id;
	}

	public void setRestaurant_id(Long restaurant_id) {
		this.restaurant_id = restaurant_id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public CommentResponse getResponse(Comment model) {
		
		this.id = model.getId();
		this.aimbianceStar = model.getAimbianceStar();
		this.foodStar = model.getFoodStar();
		this.serviceStar = model.getServiceStar();
		this.noiseStar = model.getNoiseStar();
		this.content = model.getContent();
		this.listPhoto = model.getListPhoto();
		this.createdAt = model.getCreatedAt();
		this.updatedAt = model.getUpdatedAt();
		this.privateNote = model.getPrivateNote();
		this.restaurant_id = model.getRestaurant().getId();
		
		return this;
	}
	
	public static List<CommentResponse> getListResponse(List<Comment> ls) {
		return ls.stream().map((c) -> new CommentResponse().getResponse(c))
				.collect(Collectors.toList());
		
	}
}
