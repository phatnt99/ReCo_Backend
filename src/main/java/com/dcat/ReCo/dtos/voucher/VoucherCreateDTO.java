package com.dcat.ReCo.dtos.voucher;

import java.time.LocalDate;

import com.dcat.ReCo.models.Voucher;

public class VoucherCreateDTO {

	String    title;
	String    image;
	String    value;
	Long      restaurantId;
	String    code;
	LocalDate fromTime;
	LocalDate toTime;
	// List relation code
	String listRelation;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getListRelation() {
		return listRelation;
	}

	public void setListRelation(String listRelation) {
		this.listRelation = listRelation;
	}

	public Voucher toEntity() {
		Voucher voucher = new Voucher();
		voucher.setTitle(title);
		voucher.setCode(code);
		voucher.setFromTime(fromTime);
		voucher.setToTime(toTime);
		voucher.setImage(image);
		
		return voucher;
	}
}
