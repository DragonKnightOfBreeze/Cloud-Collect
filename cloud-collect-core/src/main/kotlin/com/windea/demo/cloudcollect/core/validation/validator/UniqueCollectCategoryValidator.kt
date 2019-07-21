package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.stereotype.*
import javax.validation.*

/**唯一收藏分类的校验器。*/
@Component
class UniqueCollectCategoryValidator(
	private val service: CollectCategoryService
) : ConstraintValidator<UniqueCollectCategory, CollectCategory> {
	override fun isValid(value: CollectCategory, context: ConstraintValidatorContext): Boolean {
		return !service.exists(value)
	}
}
