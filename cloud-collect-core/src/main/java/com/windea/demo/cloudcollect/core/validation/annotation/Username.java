package com.windea.demo.cloudcollect.core.validation.annotation;

import com.windea.demo.cloudcollect.core.validation.validator.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 用户名的校验注解。
 */
@Documented
@Constraint(validatedBy = {UsernameValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Username {
	String message() default "Username is not correct.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
