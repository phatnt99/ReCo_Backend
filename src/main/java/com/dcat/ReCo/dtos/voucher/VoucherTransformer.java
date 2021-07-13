package com.dcat.ReCo.dtos.voucher;

import java.time.LocalDate;

import com.dcat.ReCo.dtos.restaurant.RestaurantTransformer;

public class VoucherTransformer {
	Long id;
	String title;
	String image;
	String code;
	String value;
	String content;
	LocalDate fromTime;
	LocalDate toTime;
	RestaurantTransformer restaurant;
	Long count;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDate getFromTime() {
		return fromTime;
	}

	public void setFromTime(LocalDate fromTime) {
		this.fromTime = fromTime;
	}

	public LocalDate getToTime() {
		return toTime;
	}

	public void setToTime(LocalDate toTime) {
		this.toTime = toTime;
	}

	public RestaurantTransformer getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(RestaurantTransformer restaurant) {
		this.restaurant = restaurant;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
