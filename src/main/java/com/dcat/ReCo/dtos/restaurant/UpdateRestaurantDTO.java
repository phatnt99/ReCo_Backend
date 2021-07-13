package com.dcat.ReCo.dtos.restaurant;

import com.dcat.ReCo.dtos.CreateRestaurantDTO;

public class UpdateRestaurantDTO extends CreateRestaurantDTO {

	Long id;
	String menuUrl;
	String carouselUrl;
	Long addressId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getCarouselUrl() {
		return carouselUrl;
	}

	public void setCarouselUrl(String carouselUrl) {
		this.carouselUrl = carouselUrl;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

}
