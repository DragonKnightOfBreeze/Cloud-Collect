package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.beans.factory.annotation.*
import javax.validation.*

/**唯一用户的校验器。*/
open class UniqueUserValidator : ConstraintValidator<UniqueUser, User> {
	@Autowired private lateinit var userService: UserService
	
	override fun isValid(value: User, context: ConstraintValidatorContext): Boolean {
		return !userService.existsByUsernameOrEmail(value.username, value.email)
	}
}
