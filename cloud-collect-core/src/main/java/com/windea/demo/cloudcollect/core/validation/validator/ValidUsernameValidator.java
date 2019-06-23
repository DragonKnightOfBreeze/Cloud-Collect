package com.windea.demo.cloudcollect.core.validation.validator;

import com.windea.demo.cloudcollect.core.validation.annotation.ValidUsername;

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
		//6~16位的字母、数字和下划线。
		return value.matches("\\w{6,16}");
	}
}
