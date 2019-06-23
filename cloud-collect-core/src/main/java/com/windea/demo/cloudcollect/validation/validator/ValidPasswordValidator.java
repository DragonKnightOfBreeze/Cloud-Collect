package com.windea.demo.cloudcollect.validation.validator;

import com.windea.demo.cloudcollect.exception.NotImplementedException;
import com.windea.demo.cloudcollect.validation.annotation.ValidPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 密码格式的校验器。
 */
public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {
	@Override
	public void initialize(ValidPassword constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		throw new NotImplementedException();
	}
}
