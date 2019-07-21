package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.boot.context.properties.*
import org.springframework.stereotype.*
import javax.validation.*

/**用户名的校验器。*/
@Component
@ConfigurationProperties("com.windea.validation.username")
class UsernameValidator : ConstraintValidator<Username, String> {
	lateinit var validRegex: String
	
	
	override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
		//6~16位的字母、数字和下划线。
		return value.matches(validRegex.toRegex())
	}
}
