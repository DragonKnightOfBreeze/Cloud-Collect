package com.windea.demo.cloudcollect.core.validation.annotation;

import com.windea.demo.cloudcollect.core.validation.validator.UniqueUserValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 唯一用户的校验注解。
 */
@Documented
@Constraint(validatedBy = {UniqueUserValidator.class})
@Target(TYPE_USE)
@Retention(RUNTIME)
public @interface UniqueUser {
	String message() default "User is duplicate.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
