package com.windea.demo.cloudcollect.core.exception

import com.windea.demo.cloudcollect.core.domain.enums.*

/**导出收藏数据时的异常。*/
class ExportDataException : RuntimeException {
	constructor() : super(ResponseResult.EXPORT_DATA_FAILED.message)
	
	constructor(throwable: Throwable) : super(ResponseResult.EXPORT_DATA_FAILED.message, throwable)
}
