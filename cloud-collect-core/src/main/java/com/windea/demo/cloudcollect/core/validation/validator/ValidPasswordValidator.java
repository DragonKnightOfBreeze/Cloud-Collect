package com.windea.demo.cloudcollect.core.validation.validator;

import com.windea.demo.cloudcollect.core.validation.annotation.ValidPassword;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 密码格式的校验器。
 */
public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {
	@Value("${com.windea.validation.validPassword}")
	private String validPassword;


	@Override
	public void initialize(ValidPassword constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		//6~16位的字母、数字和下划线，以字母开头。
		return value.matches(validPassword);
	}
}
