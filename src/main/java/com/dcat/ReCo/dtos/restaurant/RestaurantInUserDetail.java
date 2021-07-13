package com.dcat.ReCo.dtos.restaurant;

import com.dcat.ReCo.dtos.address.AddressBasic;

public interface RestaurantInUserDetail extends RestaurantBasic {

	String getLogo();

	AddressBasic getAddress();
}
