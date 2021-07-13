package com.dcat.ReCo.exceptions;

import com.dcat.ReCo.constants.LangConst;

public class CredentialNotValidException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public CredentialNotValidException() {
		super(LangConst.credentialNotValid);
	}
}
