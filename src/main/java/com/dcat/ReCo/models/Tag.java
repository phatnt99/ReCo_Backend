package com.dcat.ReCo.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tags")
public class Tag extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column(name = "name")
	private String name;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "resource_id")
	private Resource resource;

	@JsonIgnore
	@ManyToMany(mappedBy = "tags")
	private List<Restaurant> restaurants;

	@JsonIgnore
	@ManyToMany(mappedBy = "tags")
	private List<Review> reviews;

	@Formula("(select count(r.id) from tags t join tag_restaurant tr on t.id = tr.tag_id join restaurants r on tr.restaurant_id = r.id where t.id = id)")
	@JsonIgnore
	private Integer countUsing;

	public Tag() {
		super();
	}

	public Tag(String name, Resource resource) {
		super();
		this.name     = name;
		this.resource = resource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Integer getCountUsing() {
		return countUsing;
	}

	public void setCountUsing(Integer countUsing) {
		this.countUsing = countUsing;
	}

}
