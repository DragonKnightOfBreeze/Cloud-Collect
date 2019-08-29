package com.windea.demo.cloudcollect.core.validation.annotation

import com.windea.demo.cloudcollect.core.validation.validator.*
import javax.validation.*
import kotlin.reflect.*

/**唯一用户的校验注解。*/
@MustBeDocumented
@Constraint(validatedBy = [UniqueEmailRegisterFormValidator::class])
@Target(AnnotationTarget.CLASS)
annotation class UniqueEmailRegisterForm(
	val message: String = "User is duplicate.",
	val groups: Array<KClass<*>> = [],
	val payload: Array<KClass<out Payload>> = []
)
