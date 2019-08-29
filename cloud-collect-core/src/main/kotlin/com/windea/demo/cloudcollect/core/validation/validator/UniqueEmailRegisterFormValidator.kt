package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.domain.request.*
import com.windea.demo.cloudcollect.core.service.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*
import javax.validation.*

/**唯一用户的校验器。*/
@Component
class UniqueEmailRegisterFormValidator : ConstraintValidator<UniqueEmailRegisterForm, EmailRegisterForm> {
	@Autowired private lateinit var service: UserService
	
	override fun isValid(value: EmailRegisterForm, context: ConstraintValidatorContext): Boolean {
		val username = value.username
		val email = value.email
		return !service.existsByUsernameOrEmail(username, email)
	}
}
