package com.windea.demo.cloudcollect.core.validation.validator;

import com.windea.demo.cloudcollect.core.domain.entity.User;
import com.windea.demo.cloudcollect.core.service.UserService;
import com.windea.demo.cloudcollect.core.validation.annotation.UniqueUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 唯一用户的校验器。
 */
public class UniqueUserValidator implements ConstraintValidator<UniqueUser, User> {
	@Autowired private UserService service;

	@Override
	public boolean isValid(User value, ConstraintValidatorContext context) {
		return !service.exists(value);
	}
}
