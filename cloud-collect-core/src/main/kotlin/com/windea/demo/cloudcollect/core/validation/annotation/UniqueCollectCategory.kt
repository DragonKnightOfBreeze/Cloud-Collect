package com.windea.demo.cloudcollect.core.validation.annotation

import com.windea.demo.cloudcollect.core.validation.validator.*
import javax.validation.*
import kotlin.reflect.*

/**唯一收藏分类的校验注解。*/
@MustBeDocumented
@Constraint(validatedBy = [UniqueCollectCategoryValidator::class])
@Target(AnnotationTarget.CLASS)
annotation class UniqueCollectCategory(
	val message: String = "Collect category is duplicate.",
	val groups: Array<KClass<*>> = [],
	val payload: Array<KClass<out Payload>> = []
)
