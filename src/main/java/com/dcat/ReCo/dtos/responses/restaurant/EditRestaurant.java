package com.dcat.ReCo.dtos.responses.restaurant;

import java.util.Collection;
import java.util.stream.Collectors;

import com.dcat.ReCo.dtos.DTOMapping;
import com.dcat.ReCo.models.Restaurant;

public class EditRestaurant implements DTOMapping<EditRestaurant, Restaurant> {

	Long             id;
	String           name;
	String           introduction;
	Collection<Long> tags;
	String           cuisine;
	String           suitable;
	String           space;
	String           parking;
	Double           minPrice;
	Double           maxPrice;
	String           openTime;
	String           payment;
	Long             district;
	Long             addressId;
	String           address;
	Double           longtitude;
	Double           latitude;
	String           logo;
	String           carousel;
	String           menu;
	Integer          approveStatus;
	Long             ownerId;

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public Collection<Long> getTags() {
		return tags;
	}

	public void setTags(Collection<Long> tags) {
		this.tags = tags;
	}

	public String getCuisine() {
		return cuisine;
	}

	public String getSuitable() {
		return suitable;
	}

	public String getSpace() {
		return space;
	}

	public String getParking() {
		return parking;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public String getOpenTime() {
		return openTime;
	}

	public String getPayment() {
		return payment;
	}

	public Long getDistrict() {
		return district;
	}

	public String getAddress() {
		return address;
	}

	public Double getLongtitude() {
		return longtitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public String getLogo() {
		return logo;
	}

	public String getCarousel() {
		return carousel;
	}

	public String getMenu() {
		return menu;
	}

	public Long getAddressId() {
		return addressId;
	}

	public Integer getApproveStatus() {
		return approveStatus;
	}

	@Override
	public EditRestaurant getDTO(Restaurant model) {
		this.id            = model.getId();
		this.name          = model.getName();
		this.introduction  = model.getIntroduction();
		this.tags          = model.getTags().stream().map((tag) -> tag.getId())
				.collect(Collectors.toList());
		this.cuisine       = model.getCuisine();
		this.suitable      = model.getSuitable();
		this.space         = model.getSpace();
		this.parking       = model.getParking();
		this.minPrice      = model.getMinPrice();
		this.maxPrice      = model.getMaxPrice();
		this.openTime      = model.getOpenTime();
		this.payment       = model.getPayment();
		this.address       = model.getAddress().getDetail();
		this.addressId     = model.getAddress().getId();
		this.district      = model.getAddress().getDistrict().getId();
		this.longtitude    = model.getAddress().getLongtitude();
		this.latitude      = model.getAddress().getLatitude();
		this.logo          = model.getLogo();
		this.carousel      = model.getCarousel();
		this.menu          = model.getMenu();
		this.approveStatus = model.getApproveStatus();
		this.ownerId = model.getOwner() != null ? model.getOwner().getId() : null;
		
		return this;
	}

}
