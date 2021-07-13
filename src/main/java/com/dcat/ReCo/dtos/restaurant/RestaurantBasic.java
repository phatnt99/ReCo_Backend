package com.dcat.ReCo.dtos.restaurant;

import com.dcat.ReCo.dtos.address.AddressBasic;

public interface RestaurantBasic {
	
	Long getId();

	String getName();
	
	String getLogo();
	
	AddressBasic getAddress();
}
