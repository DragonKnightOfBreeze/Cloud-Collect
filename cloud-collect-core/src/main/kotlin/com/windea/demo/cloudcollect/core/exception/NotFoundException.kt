package com.windea.demo.cloudcollect.core.exception

import com.windea.demo.cloudcollect.core.domain.enums.*

/**页面未找到时的异常。 */
class NotFoundException : RuntimeException {
	constructor() : super(ResponseResult.NOT_FOUND.message)
	
	constructor(throwable: Throwable) : super(ResponseResult.NOT_FOUND.message, throwable)
}
