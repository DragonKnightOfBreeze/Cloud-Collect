package com.windea.demo.cloudcollect.core.validation.annotation

import com.windea.demo.cloudcollect.core.validation.validator.*
import javax.validation.*
import kotlin.reflect.*

/**用户名的校验注解。*/
@MustBeDocumented
@Constraint(validatedBy = [UsernameValidator::class])
@Target(AnnotationTarget.FIELD)
annotation class Username(
	val message: String = "Username is not correct.",
	val groups: Array<KClass<*>> = [],
	val payload: Array<KClass<out Payload>> = []
)
