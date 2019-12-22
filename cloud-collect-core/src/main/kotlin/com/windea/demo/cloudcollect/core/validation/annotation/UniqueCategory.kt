package com.windea.demo.cloudcollect.core.validation.annotation

import com.windea.demo.cloudcollect.core.validation.validator.*
import javax.validation.*
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.*

/**唯一收藏分类的校验注解。*/
@MustBeDocumented
@Constraint(validatedBy = [UniqueCategoryValidator::class])
@Target(CLASS)
annotation class UniqueCategory(
	val message: String = "Collect category is duplicate.",
	val groups: Array<KClass<*>> = [],
	val payload: Array<KClass<out Payload>> = []
)
