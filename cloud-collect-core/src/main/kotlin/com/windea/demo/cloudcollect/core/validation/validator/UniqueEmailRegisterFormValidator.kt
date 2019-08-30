package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.domain.request.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.beans.factory.annotation.*
import javax.validation.*

/**唯一用户的校验器。*/
open class UniqueEmailRegisterFormValidator : ConstraintValidator<UniqueEmailRegisterForm, EmailRegisterForm> {
	@Autowired private lateinit var userRepository: UserRepository
	
	override fun isValid(value: EmailRegisterForm, context: ConstraintValidatorContext): Boolean {
		val username = value.username
		val email = value.email
		return !userRepository.existsByUsernameOrEmail(username, email)
	}
}
