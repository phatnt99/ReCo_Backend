package com.dcat.ReCo.models;

import java.util.List;

public interface RestaurantAndListTag {
	Long getId();

	List<Tag> getTags();

	Address getAddress();

	interface Address {
		District getDistrict();

		interface District {
			Long getId();
		}
	}
	
	public interface Tag {
		Long getId();
	}
}
