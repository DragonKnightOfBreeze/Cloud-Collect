package com.windea.demo.cloudcollect.core.validation.annotation;

import com.windea.demo.cloudcollect.core.validation.validator.UniqueCollectTagValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 唯一收藏标签的校验注解。
 */
@Documented
@Constraint(validatedBy = {UniqueCollectTagValidator.class})
@Target(TYPE_USE)
@Retention(RUNTIME)
public @interface UniqueCollectTag {
	String message() default "Collect category is duplicate.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
