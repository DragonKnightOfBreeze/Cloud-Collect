package com.windea.demo.cloudcollect.core.validation.annotation

import com.windea.demo.cloudcollect.core.validation.validator.*
import javax.validation.*
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.*

/**唯一收藏标签的校验注解。*/
@MustBeDocumented
@Constraint(validatedBy = [UniqueCollectTagValidator::class])
@Target(CLASS)
annotation class UniqueCollectTag(
	val message: String = "Collect category is duplicate.",
	val groups: Array<KClass<*>> = [],
	val payload: Array<KClass<out Payload>> = []
)
