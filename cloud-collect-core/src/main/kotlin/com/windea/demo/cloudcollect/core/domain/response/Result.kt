package com.windea.demo.cloudcollect.core.domain.response

import com.windea.demo.cloudcollect.core.annotation.*
import java.io.*

@Domain
data class Result(
	val message: String?
) : Serializable
