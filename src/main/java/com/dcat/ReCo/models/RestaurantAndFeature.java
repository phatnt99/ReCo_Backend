package com.dcat.ReCo.models;

import java.util.List;

public interface RestaurantAndFeature {
	Long getId();

	List<Tag> getTags();

	Address getAddress();
	
	Double getMinPrice();
	
	Double getMaxPrice();
	
	Integer getStar1();

	Integer getStar2();
	
	Integer getStar3();
	
	Integer getStar4();
	
	Integer getStar5();
	
	Double getStarAverage();
	
	Double getStarFood();
	
	Double getStarAmbiance();
	
	Double getStarService();
	
	Double getStarNoise();
	

	interface Address {
		District getDistrict();

		interface District {
			Long getId();
		}
	}
	
	interface Tag {
		Long getId();
	}
}
