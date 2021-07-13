package com.dcat.ReCo.dtos.responses.restaurant;

import java.io.Serializable;

import com.dcat.ReCo.dtos.DTOMapping;
import com.dcat.ReCo.models.Restaurant;

public class CreateOneRestaurant
		implements Serializable, DTOMapping<CreateOneRestaurant, Restaurant> {

	private static final long serialVersionUID = 1L;
	Long                      id;
	String                    name;

	@Override
	public CreateOneRestaurant getDTO(Restaurant model) {
		this.id   = model.getId();
		this.name = model.getName();
		return this;
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

	public void setName(String name) {
		this.name = name;
	}

}
