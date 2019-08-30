package com.windea.demo.cloudcollect.demo.validation.annotation

import com.windea.demo.cloudcollect.demo.validation.validator.*
import javax.validation.*
import kotlin.reflect.*

/**密码的校验注解。*/
@MustBeDocumented
@Constraint(validatedBy = [PasswordValidator::class])
@Target(AnnotationTarget.FIELD)
annotation class Password(
	val message: String = "Password is not correct.",
	val groups: Array<KClass<*>> = [],
	val payload: Array<KClass<out Payload>> = []
)
