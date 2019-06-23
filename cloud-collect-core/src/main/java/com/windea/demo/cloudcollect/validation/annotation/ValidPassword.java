package com.windea.demo.cloudcollect.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 密码格式的校验注解。
 */
@Documented
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface ValidPassword {
	String message() default "validation.User.password.ValidPassword";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
