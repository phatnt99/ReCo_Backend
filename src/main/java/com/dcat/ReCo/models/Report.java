package com.dcat.ReCo.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "reports")
public class Report extends BaseModel {

	private static final long serialVersionUID = 1L;

	private Long reportableId;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private Restaurant restaurant;

	private String content;

	private Integer type;

	@Transient
	private String reportableName;

	public Long getReportableId() {
		return reportableId;
	}

	public void setReportableId(Long reportableId) {
		this.reportableId = reportableId;
	}


	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getReportableName() {
		return reportableName;
	}

	public void setReportableName(String reportableName) {
		this.reportableName = reportableName;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
	public String getRestaurantName() {
		return this.restaurant != null ? this.restaurant.getName() : null;
	}

}
