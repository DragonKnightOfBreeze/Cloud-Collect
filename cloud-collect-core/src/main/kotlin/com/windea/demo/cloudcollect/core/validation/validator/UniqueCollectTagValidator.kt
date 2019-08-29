package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*
import javax.validation.*

/**唯一收藏标签的校验器。*/
@Component
class UniqueCollectTagValidator : ConstraintValidator<UniqueCollectTag, CollectTag> {
	@Autowired private lateinit var service: CollectTagService
	
	override fun isValid(value: CollectTag, context: ConstraintValidatorContext): Boolean {
		val name = value.name
		val userId = value.user.id ?: return false
		return !service.existsByNameAndUserId(name, userId)
	}
}
