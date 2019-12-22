package com.windea.demo.cloudcollect.core.domain.response

import com.windea.demo.cloudcollect.core.annotation.*
import java.io.*

@Domain
data class Message(
	val message: String?
) : Serializable
