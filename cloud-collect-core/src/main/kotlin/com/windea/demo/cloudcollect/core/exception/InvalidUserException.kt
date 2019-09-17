package com.windea.demo.cloudcollect.core.exception

import com.windea.demo.cloudcollect.core.domain.enums.*

/**非法的用户的异常。 */
class InvalidUserException : RuntimeException {
	constructor() : super(ResponseResult.INVALID_USER.message)
	
	constructor(throwable: Throwable) : super(ResponseResult.INVALID_USER.message, throwable)
}
