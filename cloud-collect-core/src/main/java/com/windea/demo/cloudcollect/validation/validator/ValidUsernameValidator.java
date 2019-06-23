package com.windea.demo.cloudcollect.validation.validator;

import com.windea.demo.cloudcollect.exception.NotImplementedException;
import com.windea.demo.cloudcollect.validation.annotation.ValidUsername;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 用户名格式的校验器。
 */
public class ValidUsernameValidator implements ConstraintValidator<ValidUsername, String> {
	@Override
	public void initialize(ValidUsername constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		throw new NotImplementedException();
	}
}
