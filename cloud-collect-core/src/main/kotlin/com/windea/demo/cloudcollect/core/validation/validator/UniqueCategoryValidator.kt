package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.beans.factory.annotation.*
import javax.validation.*

/**唯一收藏分类的校验器。*/
open class UniqueCategoryValidator : ConstraintValidator<UniqueCategory, Category> {
	@Autowired private lateinit var categoryService: CategoryService
	
	override fun isValid(value: Category, context: ConstraintValidatorContext): Boolean {
		return !categoryService.existsByNameAndUser(value.name, value.user)
	}
}
