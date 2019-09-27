package com.windea.demo.cloudcollect.core.exceptions

import com.windea.demo.cloudcollect.core.enums.*
import org.springframework.security.core.userdetails.*

/**用户未找到的异常。 */
class UserNotFoundException : UsernameNotFoundException(ResultStatus.USER_NOT_FOUND.message)
