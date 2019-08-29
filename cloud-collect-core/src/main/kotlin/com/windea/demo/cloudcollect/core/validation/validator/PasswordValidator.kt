package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.properties.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.stereotype.*
import javax.validation.*

/**密码的校验器。*/
@Component
class PasswordValidator(
	private val validationProperties: ValidationProperties
) : ConstraintValidator<Password, String> {
	override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
		//6~16位的字母、数字和下划线，以字母开头。
		return value.matches(validationProperties.password.toRegex())
	}
}
