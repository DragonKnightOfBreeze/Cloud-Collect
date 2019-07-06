package com.windea.demo.cloudcollect.core.validation.validator;

import com.windea.demo.cloudcollect.core.domain.entity.Collect;
import com.windea.demo.cloudcollect.core.service.CollectService;
import com.windea.demo.cloudcollect.core.validation.annotation.UniqueCollect;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 唯一收藏的校验器。
 */
public class UniqueCollectValidator implements ConstraintValidator<UniqueCollect, Collect> {
	@Autowired private CollectService service;

	@Override
	public boolean isValid(Collect value, ConstraintValidatorContext context) {
		return !service.exists(value);
	}
}
