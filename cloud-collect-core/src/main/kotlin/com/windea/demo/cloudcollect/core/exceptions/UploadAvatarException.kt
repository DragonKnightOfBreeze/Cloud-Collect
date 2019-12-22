package com.windea.demo.cloudcollect.core.exceptions

import com.windea.demo.cloudcollect.core.enums.*

/**上传用户头像时的异常。*/
class UploadAvatarException : RuntimeException(ResultStatus.UPLOAD_AVATAR_FAILED.message)
