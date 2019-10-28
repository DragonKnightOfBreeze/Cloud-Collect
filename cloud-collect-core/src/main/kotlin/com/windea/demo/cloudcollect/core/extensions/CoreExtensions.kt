package com.windea.demo.cloudcollect.core.extensions

import com.windea.demo.cloudcollect.core.domain.response.*
import org.springframework.security.core.*

fun Authentication.toUser() = (this.principal as UserDetailsVo).delegateUser
