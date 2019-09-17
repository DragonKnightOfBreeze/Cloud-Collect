package com.windea.demo.cloudcollect.core.validation.annotation

import com.windea.demo.cloudcollect.core.validation.validator.*
import javax.validation.*
import kotlin.reflect.*

/**唯一收藏的校验注解。*/
@MustBeDocumented
@Constraint(validatedBy = [UniqueCollectValidator::class])
@Target(AnnotationTarget.CLASS)
annotation class UniqueCollect(
	val message: String = "Collect is duplicate.",
	val groups: Array<KClass<*>> = [],
	val payload: Array<KClass<out Payload>> = []
)
