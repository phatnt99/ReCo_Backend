package com.dcat.ReCo.dtos.comment;

import org.modelmapper.ModelMapper;

import com.dcat.ReCo.dtos.BaseTransformer;
import com.dcat.ReCo.dtos.JsonMapper;
import com.dcat.ReCo.dtos.restaurant.RestaurantTransformer;
import com.dcat.ReCo.dtos.user.UserTransformer;
import com.dcat.ReCo.models.Comment;

public class CommentTransformer extends BaseTransformer
		implements JsonMapper<CommentTransformer, Comment> {

	private Long                  id;
	private String                content;
	private String                listPhoto;
	private Double                overallStar;
	private Integer               foodStar;
	private Integer               serviceStar;
	private Integer               aimbianceStar;
	private Integer               noiseStar;
	private String                privateNote;
	private RestaurantTransformer restaurant;
	private UserTransformer       user;

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

	public Double getOverallStar() {
		return overallStar;
	}

	public void setOverallStar(Double overallStar) {
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

	public RestaurantTransformer getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantTransformer restaurant) {
		this.restaurant = restaurant;
	}

	public UserTransformer getUser() {
		return user;
	}

	public void setUser(UserTransformer user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public CommentTransformer getJson(Comment c) {
		ModelMapper mapper = new ModelMapper();

		return mapper.map(c, CommentTransformer.class);
	}

}
