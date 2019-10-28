package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.beans.factory.annotation.*
import javax.validation.*

/**唯一收藏分类的校验器。*/
open class UniqueCollectCategoryValidator : ConstraintValidator<UniqueCollectCategory, CollectCategory> {
	@Autowired private lateinit var categoryRepository: CollectCategoryRepository
	
	override fun isValid(value: CollectCategory, context: ConstraintValidatorContext): Boolean {
		return !categoryRepository.existsByNameAndUser(value.name, value.user)
	}
}
