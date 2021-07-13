package com.dcat.ReCo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordNotMatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PasswordNotMatchException() {
        super("Sai mật khẩu");
    }

}
