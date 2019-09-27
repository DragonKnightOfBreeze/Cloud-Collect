package com.windea.demo.cloudcollect.core.exceptions

import com.windea.demo.cloudcollect.core.enums.*

/**功能未实现的异常。 */
class NotImplementedException : RuntimeException(ResultStatus.NOT_IMPLEMENTED.message)
