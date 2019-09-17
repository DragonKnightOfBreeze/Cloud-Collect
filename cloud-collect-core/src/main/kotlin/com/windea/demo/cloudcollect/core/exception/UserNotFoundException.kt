package com.windea.demo.cloudcollect.core.exception

import com.windea.demo.cloudcollect.core.domain.enums.*
import org.springframework.security.core.userdetails.*

/**用户未找到的异常。 */
class UserNotFoundException : UsernameNotFoundException {
	constructor() : super(ResponseResult.USER_NOT_FOUND.message)
	
	constructor(throwable: Throwable) : super(ResponseResult.USER_NOT_FOUND.message, throwable)
}
