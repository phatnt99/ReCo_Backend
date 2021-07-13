package com.dcat.ReCo.dtos;

public class RestaurantDistanceDTO {
	private Long   id;
	private Double latitude;
	private Double longtitude;
	private Double distance;

	public RestaurantDistanceDTO(Long id, Double latitude, Double longtitude) {
		super();
		this.id         = id;
		this.latitude   = latitude;
		this.longtitude = longtitude;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(Double longtitude) {
		this.longtitude = longtitude;
	}

}
