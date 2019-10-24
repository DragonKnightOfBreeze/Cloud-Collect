package com.windea.demo.cloudcollect.core.exceptions

import com.windea.demo.cloudcollect.core.enums.*

/**页面未找到时的异常。 */
class NotFoundException : RuntimeException(ResultStatus.NOT_FOUND.message)
