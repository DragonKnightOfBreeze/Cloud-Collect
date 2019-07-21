package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.stereotype.*
import javax.validation.*

/**唯一收藏的校验器。*/
@Component
class UniqueCollectValidator(
	private val service: CollectService
) : ConstraintValidator<UniqueCollect, Collect> {
	override fun isValid(value: Collect, context: ConstraintValidatorContext): Boolean {
		return !service.exists(value)
	}
}
