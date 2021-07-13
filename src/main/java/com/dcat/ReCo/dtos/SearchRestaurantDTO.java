package com.dcat.ReCo.dtos;

import java.util.Collection;

public class SearchRestaurantDTO extends BaseDTO {

	private static final long serialVersionUID = 1L;

	private Collection<Long> district;

	private Collection<Long> dishes;

	private Collection<Long> type;

	private Long minPrice;

	private Long maxPrice;

	private Collection<Long> nation;
	
	private Double longtitude;
	
	private Double latitude;

	public SearchRestaurantDTO() {
		super();
	}

	public Collection<Long> getDistrict() {
		return district;
	}

	public void setDistrict(Collection<Long> district) {
		this.district = district;
	}

	public Collection<Long> getDishes() {
		return dishes;
	}

	public void setDishes(Collection<Long> dishes) {
		this.dishes = dishes;
	}

	public Long getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Long minPrice) {
		this.minPrice = minPrice;
	}

	public Long getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Long maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Collection<Long> getNation() {
		return nation;
	}

	public void setNation(Collection<Long> nation) {
		this.nation = nation;
	}

	public Collection<Long> getType() {
		return type;
	}

	public void setType(Collection<Long> type) {
		this.type = type;
	}

	public Double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(Double longtitude) {
		this.longtitude = longtitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

}
