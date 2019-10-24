package com.windea.demo.cloudcollect.core.exceptions

import com.windea.demo.cloudcollect.core.enums.*

/**导入收藏数据的异常。*/
class ImportDataException : RuntimeException(ResultStatus.IMPORT_DATA_FAILED.message)
