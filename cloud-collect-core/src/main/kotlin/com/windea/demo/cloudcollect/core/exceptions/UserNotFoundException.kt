package com.windea.demo.cloudcollect.core.exceptions

import com.windea.demo.cloudcollect.core.enums.*

/**用户未找到的异常。 */
class UserNotFoundException : RuntimeException(ResultStatus.USER_NOT_FOUND.message)
