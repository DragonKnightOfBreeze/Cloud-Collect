package com.windea.demo.cloudcollect.core.validation.validator

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.repository.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.beans.factory.annotation.*
import javax.validation.*

/**唯一收藏标签的校验器。*/
open class UniqueTagValidator : ConstraintValidator<UniqueTag, Tag> {
	@Autowired private lateinit var tagRepository: TagRepository
	
	override fun isValid(value: Tag, context: ConstraintValidatorContext): Boolean {
		return !tagRepository.existsByNameAndUser(value.name, value.user)
	}
}
