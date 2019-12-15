package com.windea.demo.cloudcollect.core.extensions

import com.windea.demo.cloudcollect.core.domain.entity.*
import com.windea.demo.cloudcollect.core.domain.response.*
import org.springframework.security.core.*
import org.springframework.security.core.context.*

//principal可能是String，也可能是UserDetails

val currentAuthentication: Authentication? get() = SecurityContextHolder.getContext().authentication

val currentUser: User? get() = (currentAuthentication?.principal as? UserDetailsVo)?.delegateUser
