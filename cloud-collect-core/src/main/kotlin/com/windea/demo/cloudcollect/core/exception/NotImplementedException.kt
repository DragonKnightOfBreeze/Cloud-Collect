package com.windea.demo.cloudcollect.core.exception

import com.windea.demo.cloudcollect.core.domain.enums.*

/**功能未实现的异常。 */
class NotImplementedException : RuntimeException {
	constructor() : super(ResponseResult.NOT_IMPLEMENTED.message)
	
	constructor(throwable: Throwable) : super(ResponseResult.NOT_IMPLEMENTED.message, throwable)
}
