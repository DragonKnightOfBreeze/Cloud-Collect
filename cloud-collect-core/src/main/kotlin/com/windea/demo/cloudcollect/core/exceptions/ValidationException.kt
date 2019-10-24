package com.windea.demo.cloudcollect.core.exceptions

import com.windea.demo.cloudcollect.core.enums.*
import org.springframework.validation.*

/**参数校验的异常。 */
class ValidationException(
	var validationErrors: List<ObjectError>
) : RuntimeException(ResultStatus.VALIDATION_ERROR.message)
