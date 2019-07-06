package com.windea.demo.cloudcollect.core.validation.annotation;

import com.windea.demo.cloudcollect.core.validation.validator.UniqueCollectValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 唯一收藏的校验注解。
 */
@Documented
@Constraint(validatedBy = {UniqueCollectValidator.class})
@Target(TYPE_USE)
@Retention(RUNTIME)
public @interface UniqueCollect {
	String message() default "Collect is duplicate.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
