package com.dcat.ReCo.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.dcat.ReCo.repositories.UserRepository;

public class UniqueColumnValidator implements 
ConstraintValidator<UniqueColumn, String> {

	String column;
	
	@Autowired
	UserRepository userRepository;

    @Override
    public void initialize(UniqueColumn constraintAnnotation) {
    	column = constraintAnnotation.column();
    }
    
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(column.equals("email"))
			return !userRepository.existsByEmail(value);
		if(column.equals("username"))
			return !userRepository.existsByUsername(value);
		return false;
	}
}
