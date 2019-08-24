package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.beans.factory.annotation.*
import org.springframework.stereotype.*
import javax.validation.*

/**密码的校验器。*/
@Component
class PasswordValidator : ConstraintValidator<Password, String> {
	@Value("\${com.windea.validation.password.regex}")
	private lateinit var regex: String
	
	
	override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
		//6~16位的字母、数字和下划线，以字母开头。
		return value.matches(regex.toRegex())
	}
}
