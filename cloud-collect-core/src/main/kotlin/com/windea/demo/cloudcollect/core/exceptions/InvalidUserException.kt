package com.windea.demo.cloudcollect.core.exceptions

import com.windea.demo.cloudcollect.core.enums.*

/**非法的用户的异常。 */
class InvalidUserException : RuntimeException(ResultStatus.INVALID_USER.message)
