package com.windea.demo.cloudcollect.core.exceptions

import com.windea.demo.cloudcollect.core.enums.*

/**导出收藏数据时的异常。*/
class ExportDataException : RuntimeException(ResultStatus.EXPORT_DATA_FAILED.message)
