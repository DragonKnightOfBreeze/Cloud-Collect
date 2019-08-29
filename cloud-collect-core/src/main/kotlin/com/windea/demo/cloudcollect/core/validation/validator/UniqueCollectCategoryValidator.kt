package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*
import javax.validation.*

/**唯一收藏分类的校验器。*/
@Component
class UniqueCollectCategoryValidator : ConstraintValidator<UniqueCollectCategory, CollectCategory> {
	@Autowired private lateinit var service: CollectCategoryService
	
	override fun isValid(value: CollectCategory, context: ConstraintValidatorContext): Boolean {
		val name = value.name
		val userId = value.user.id ?: return false
		return !service.existsByNameAndUserId(name, userId)
	}
}
