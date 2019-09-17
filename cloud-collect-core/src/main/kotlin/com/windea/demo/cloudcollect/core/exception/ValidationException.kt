package com.windea.demo.cloudcollect.core.exception

import com.windea.demo.cloudcollect.core.domain.enums.*
import org.springframework.validation.*

/**参数校验的异常。 */
class ValidationException : RuntimeException {
	var validationErrors: List<ObjectError>
	
	constructor(validationErrors: List<ObjectError>) : super(ResponseResult.VALIDATION_ERROR.message) {
		this.validationErrors = validationErrors
	}
	
	constructor(validationErrors: List<ObjectError>, throwable: Throwable) : super(ResponseResult.VALIDATION_ERROR.message, throwable) {
		this.validationErrors = validationErrors
	}
}
