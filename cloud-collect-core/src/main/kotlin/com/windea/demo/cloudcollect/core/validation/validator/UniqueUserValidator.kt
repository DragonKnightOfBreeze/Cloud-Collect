package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.stereotype.*
import javax.validation.*

/** 唯一用户的校验器。*/
@Component
class UniqueUserValidator(
	private val service: UserService
) : ConstraintValidator<UniqueUser, User> {
	override fun isValid(value: User, context: ConstraintValidatorContext): Boolean {
		return !service.exists(value)
	}
}
