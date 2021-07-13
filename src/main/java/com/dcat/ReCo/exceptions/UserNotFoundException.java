package com.dcat.ReCo.exceptions;

import com.dcat.ReCo.constants.LangConst;

public class UserNotFoundException extends EntityNotFound {

	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException() {
		super(LangConst.NotFound.userNotFound);
	}
	
}
