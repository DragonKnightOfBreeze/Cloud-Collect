package com.windea.demo.cloudcollect.core.validation.validator;

import com.windea.demo.cloudcollect.core.domain.entity.CollectTag;
import com.windea.demo.cloudcollect.core.service.CollectTagService;
import com.windea.demo.cloudcollect.core.validation.annotation.UniqueCollectTag;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 唯一收藏标签的校验器。
 */
public class UniqueCollectTagValidator implements ConstraintValidator<UniqueCollectTag, CollectTag> {
	@Autowired
	private CollectTagService service;

	@Override
	public boolean isValid(CollectTag value, ConstraintValidatorContext context) {
		return !service.exists(value);
	}
}
