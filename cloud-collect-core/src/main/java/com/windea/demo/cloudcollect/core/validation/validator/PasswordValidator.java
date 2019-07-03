package com.windea.demo.cloudcollect.core.validation.validator;

import com.windea.demo.cloudcollect.core.validation.annotation.Password;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 密码的校验器。
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {
	@Value("${com.windea.validation.validPassword}")
	private String validPassword;


	@Override
	public void initialize(Password constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		//6~16位的字母、数字和下划线，以字母开头。
		return value.matches(validPassword);
	}
}
