package com.dcat.ReCo.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "vouchers")
public class Voucher extends BaseModel {

	private static final long serialVersionUID = 1L;
	private String            title;
	private String            value;
	private String            image;
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	@JsonIgnore
	private Restaurant        restaurant;
	@OneToMany(mappedBy = "voucher")
	@JsonIgnore
	private List<Reservation> reservations;
	private String            code;
	private String            content;
	private LocalDate         fromTime;
	private LocalDate         toTime;
	private String            listRelation;

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

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public List<Reservation> getReservations() {
		return reservations != null ? reservations : new ArrayList<>();
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
