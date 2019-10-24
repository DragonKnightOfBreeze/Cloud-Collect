package com.windea.demo.cloudcollect.core.validation.annotation

import com.windea.demo.cloudcollect.core.validation.validator.*
import javax.validation.*
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.*

/**密码的校验注解。*/
@MustBeDocumented
@Constraint(validatedBy = [PasswordValidator::class])
@Target(FIELD, AnnotationTarget.PROPERTY_GETTER)
annotation class Password(
	val message: String = "Password is not correct.",
	val groups: Array<KClass<*>> = [],
	val payload: Array<KClass<out Payload>> = []
)
