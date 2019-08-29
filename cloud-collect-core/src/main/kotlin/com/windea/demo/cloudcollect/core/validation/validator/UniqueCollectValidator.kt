package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*
import javax.validation.*

/**唯一收藏的校验器。*/
@Component
class UniqueCollectValidator : ConstraintValidator<UniqueCollect, Collect> {
	@Autowired private lateinit var service: CollectService
	
	override fun isValid(value: Collect, context: ConstraintValidatorContext): Boolean {
		val name = value.name
		val userId = value.user.id ?: return false
		return !service.existsByNameAndUserId(name, userId)
	}
}
