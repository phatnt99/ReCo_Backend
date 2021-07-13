package com.dcat.ReCo.dtos.restaurant.projections;

import com.dcat.ReCo.dtos.address.AddressBasic;

public interface RestaurantWithComment {
	
	Long getId();

	String getName();
	
	String getLogo();
	
	AddressBasic getAddress();
	
}
