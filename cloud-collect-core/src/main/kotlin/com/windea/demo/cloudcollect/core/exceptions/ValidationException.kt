package com.windea.demo.cloudcollect.core.exceptions

import org.springframework.validation.*

/**参数校验的异常。 */
class ValidationException(
	bindingResult: BindingResult
) : RuntimeException(bindingResult.allErrors.first().defaultMessage)
