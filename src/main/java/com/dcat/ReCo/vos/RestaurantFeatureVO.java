package com.dcat.ReCo.vos;

import java.util.ArrayList;
import java.util.List;

public class RestaurantFeatureVO {
	private Long       restaurantId;
	private List<Long> features;
	private Long       district;

	public RestaurantFeatureVO() {
		features = new ArrayList<Long>();
	}

	public RestaurantFeatureVO(Long restaurantId, List<Long> features,
			Long district) {
		super();
		this.restaurantId = restaurantId;
		this.features     = features;
		this.district     = district;
	}
	
	public void add (Long id) {
		features.add(id);
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public List<Long> getFeatures() {
		return features;
	}

	public void setFeatures(List<Long> features) {
		this.features = features;
	}

	public Long getDistrict() {
		return district;
	}

	public void setDistrict(Long district) {
		this.district = district;
	}

}
