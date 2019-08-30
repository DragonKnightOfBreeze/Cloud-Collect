package com.windea.demo.cloudcollect.demo.validation.validator

import com.windea.demo.cloudcollect.demo.properties.*
import com.windea.demo.cloudcollect.demo.validation.annotation.*
import org.springframework.beans.factory.annotation.*
import javax.validation.*

/**密码的校验器。*/
open class PasswordValidator : ConstraintValidator<Password, String> {
	@Autowired private lateinit var validationProperties: ValidationProperties
	
	override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
		//6~16位的字母、数字和下划线，以字母开头。
		return value.matches(validationProperties.password.toRegex())
	}
}
