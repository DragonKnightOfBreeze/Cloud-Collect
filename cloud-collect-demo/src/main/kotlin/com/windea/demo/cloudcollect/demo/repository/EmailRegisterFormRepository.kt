package com.windea.demo.cloudcollect.demo.repository

import com.windea.demo.cloudcollect.demo.domain.*
import org.springframework.data.jpa.repository.*

interface EmailRegisterFormRepository : JpaRepository<EmailRegisterForm, Long>
