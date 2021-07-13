package com.dcat.ReCo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public abstract class EntityNotFound extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EntityNotFound(String message) {
        super(message);
    }
}
