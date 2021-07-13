package com.dcat.ReCo.exceptions;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dcat.ReCo.utils.https.HttpResponse;

@ControllerAdvice
public class ResponseExceptionHandler {
	
	@ExceptionHandler(PasswordNotMatchException.class)
	protected ResponseEntity<Object> handlePasswordNotMatch(PasswordNotMatchException ex) {
		HttpResponse apiError = new HttpResponse(HttpStatus.BAD_REQUEST, false, ex.getMessage());
		return apiError.send();
	}
	
	@ExceptionHandler(CredentialNotValidException.class)
	protected ResponseEntity<Object> handleCredentialNotValid(CredentialNotValidException ex) {
		HttpResponse apiError = new HttpResponse(HttpStatus.UNAUTHORIZED, false, ex.getMessage());
		return apiError.send();
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		HashMap<String, String> mapErros = new HashMap<String, String>();
		List<FieldError> errors = ex.getBindingResult().getFieldErrors();
		for(FieldError er : errors) {
			mapErros.put(er.getField(), er.getDefaultMessage());
		}
		
		return new HttpResponse(HttpStatus.BAD_REQUEST, false, mapErros).send();
	}
	
	@ExceptionHandler(EntityNotFound.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFound e) {
		return new HttpResponse(HttpStatus.NOT_FOUND, false, e.getMessage()).send();
		
	}

}
