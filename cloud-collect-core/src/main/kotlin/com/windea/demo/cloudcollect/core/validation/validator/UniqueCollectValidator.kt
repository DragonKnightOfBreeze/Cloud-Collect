package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.beans.factory.annotation.*
import javax.validation.*

/**唯一收藏的校验器。*/
open class UniqueCollectValidator : ConstraintValidator<UniqueCollect, Collect> {
	@Autowired private lateinit var collectRepository: CollectRepository
	
	override fun isValid(value: Collect, context: ConstraintValidatorContext): Boolean {
		val name = value.name
		val userId = value.user.id
		return !collectRepository.existsByNameAndUserId(name, userId)
	}
}
