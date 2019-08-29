package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.properties.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.stereotype.*
import javax.validation.*

/**用户名的校验器。*/
@Component
class UsernameValidator(
	private val validationProperties: ValidationProperties
) : ConstraintValidator<Username, String> {
	override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
		//6~16位的字母、数字和下划线。
		return value.matches(validationProperties.username.toRegex())
	}
}
