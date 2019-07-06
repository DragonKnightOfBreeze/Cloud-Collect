package com.windea.demo.cloudcollect.core.validation.validator;

import com.windea.demo.cloudcollect.core.validation.annotation.Username;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 用户名的校验器。
 */
public class UsernameValidator implements ConstraintValidator<Username, String> {
	@Value("${com.windea.validation.validUsername}")
	private String validUsername;


	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		//6~16位的字母、数字和下划线。
		return value.matches(validUsername);
	}
}
