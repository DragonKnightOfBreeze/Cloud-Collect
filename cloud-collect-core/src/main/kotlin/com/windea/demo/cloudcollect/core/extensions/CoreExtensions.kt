package com.windea.demo.cloudcollect.core.extensions

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.response.*
import org.springframework.security.core.*
import org.springframework.security.core.context.*

fun Authentication.toUser() = (this.principal as UserDetailsVo).delegateUser

val currentAuthentication: Authentication? get() = SecurityContextHolder.getContext().authentication

val currentUser: User? get() = currentAuthentication?.toUser()
