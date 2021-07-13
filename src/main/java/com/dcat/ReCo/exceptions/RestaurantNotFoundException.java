package com.dcat.ReCo.exceptions;

import com.dcat.ReCo.constants.LangConst;

public class RestaurantNotFoundException extends EntityNotFound {

	private static final long serialVersionUID = 1L;
	
	public RestaurantNotFoundException() {
		super(LangConst.NotFound.restaurantNotFound);
	}

}
