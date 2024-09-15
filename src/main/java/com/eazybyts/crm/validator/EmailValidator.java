package com.eazybyts.crm.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.eazybyts.crm.annotation.UniqueEmail;
import com.eazybyts.crm.service.AppUserService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class EmailValidator implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	private AppUserService appUserService;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return null == appUserService.findUserByEmail(value);
	}

}
