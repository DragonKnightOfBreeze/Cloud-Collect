package com.windea.demo.cloudcollect.core.validation.annotation

import com.windea.demo.cloudcollect.core.validation.validator.*
import javax.validation.*
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.*

/**用户名的校验注解。*/
@MustBeDocumented
@Constraint(validatedBy = [UsernameValidator::class])
@Target(FIELD, PROPERTY_GETTER)
annotation class Username(
	val message: String = "Username is not correct.",
	val groups: Array<KClass<*>> = [],
	val payload: Array<KClass<out Payload>> = []
)
