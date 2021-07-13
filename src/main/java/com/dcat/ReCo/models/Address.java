package com.dcat.ReCo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="address")
public class Address extends BaseModel {

	private static final long serialVersionUID = 1L;

	private String detail;
	
	@ManyToOne
	@JoinColumn(name = "district")
	private District district;
	
	private Double longtitude;
	
	private Double latitude;
	
	@Column(name = "link_url")
	private String linkUrl;
	
	@JsonIgnore
	@OneToOne(mappedBy = "address")
	private Restaurant restaurant;

	public Address() {
		super();
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}
	
}
