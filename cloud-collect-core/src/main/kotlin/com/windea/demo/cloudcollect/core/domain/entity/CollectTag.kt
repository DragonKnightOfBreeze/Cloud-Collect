package com.windea.demo.cloudcollect.core.domain.entity

import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.data.annotation.*
import java.io.*
import java.time.*
import java.util.*
import javax.persistence.*
import javax.persistence.Id
import javax.validation.constraints.*

/**收藏的标签。一个收藏可以带有多个标签。*/
@Entity
@UniqueCollectTag
open class CollectTag(
	/**编号。*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	/**名字。*/
	@Column(nullable = false, length = 32)
	@field:NotEmpty(message = "{validation.CollectTag.name.NotEmpty}")
	@field:Size(min = 1, max = 32, message = "{validation.CollectTag.name.Size}")
	var name: String,
	
	/**概述。*/
	@Column(nullable = false)
	@field:NotEmpty(message = "{validation.CollectTag.summary.NotEmpty}")
	@field:Size(min = 1, max = 255, message = "{validation.CollectTag.summary.Size}")
	var summary: String = "",
	
	/**所属用户。*/
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	var user: User,
	
	/**创建时间。*/
	@Column
	@CreatedDate
	var createdTime: LocalDateTime? = null,
	
	/**最后更新时间。*/
	@Column
	@LastModifiedDate
	var lastModifiedTime: LocalDateTime? = null
) : Serializable {
	override fun equals(other: Any?) = other is CollectTag && other.id == id
	
	override fun hashCode() = Objects.hash(super.hashCode(), id.hashCode())
}
