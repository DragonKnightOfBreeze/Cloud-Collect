package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.stereotype.*
import javax.validation.*

/** 唯一收藏标签的校验器。*/
@Component
class UniqueCollectTagValidator(
	private val service: CollectTagService
) : ConstraintValidator<UniqueCollectTag, CollectTag> {
	override fun isValid(value: CollectTag, context: ConstraintValidatorContext): Boolean {
		return !service.exists(value)
	}
}
