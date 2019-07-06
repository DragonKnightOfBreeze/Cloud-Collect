package com.windea.demo.cloudcollect.core.validation.validator;

import com.windea.demo.cloudcollect.core.domain.entity.CollectCategory;
import com.windea.demo.cloudcollect.core.service.CollectCategoryService;
import com.windea.demo.cloudcollect.core.validation.annotation.UniqueCollectCategory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 唯一收藏分类的校验器。
 */
public class UniqueCollectCategoryValidator implements ConstraintValidator<UniqueCollectCategory, CollectCategory> {
	@Autowired
	private CollectCategoryService service;

	@Override
	public boolean isValid(CollectCategory value, ConstraintValidatorContext context) {
		return !service.exists(value);
	}
}
