package com.windea.demo.cloudcollect.core.exceptions

import com.windea.demo.cloudcollect.core.enums.*

/**不正确的验证码的异常。*/
class IncorrectAuthCodeException : RuntimeException(ResultStatus.INCORRECT_AUTH_CODE.message)
