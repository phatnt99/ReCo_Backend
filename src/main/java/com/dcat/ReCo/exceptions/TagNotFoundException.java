package com.dcat.ReCo.exceptions;

import com.dcat.ReCo.constants.LangConst;

public class TagNotFoundException extends EntityNotFound {

	private static final long serialVersionUID = 1L;
	
	public TagNotFoundException() {
		super(LangConst.NotFound.restaurantNotFound);
	}

}
