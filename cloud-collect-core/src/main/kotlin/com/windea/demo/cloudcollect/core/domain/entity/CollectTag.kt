package com.windea.demo.cloudcollect.core.domain.entity

import com.windea.demo.cloudcollect.core.validation.annotation.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.annotation.*
import org.springframework.data.jpa.domain.support.*
import java.io.*
import java.time.*
import java.util.*
import javax.persistence.*
import javax.persistence.Id
import javax.validation.constraints.*

@ApiModel("收藏的标签。一个收藏可以带有多个标签。")
@Entity
@EntityListeners(AuditingEntityListener::class)
@UniqueCollectTag
class CollectTag(
	@ApiModelProperty("编号。")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	@ApiModelProperty("名字。")
	@Column(nullable = false, length = 32)
	@field:NotEmpty(message = "{validation.CollectTag.name.NotEmpty}", groups = [Default::class])
	@field:Size(min = 1, max = 32, message = "{validation.CollectTag.name.Size}", groups = [Default::class])
	var name: String,
	
	@ApiModelProperty("概述。")
	@Column(nullable = false)
	@field:NotEmpty(message = "{validation.CollectTag.summary.NotEmpty}", groups = [Default::class])
	@field:Size(min = 1, max = 255, message = "{validation.CollectTag.summary.Size}", groups = [Default::class])
	var summary: String = "",
	
	@ApiModelProperty("所属用户。")
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	var user: User
) : Serializable {
	@ApiModelProperty("创建时间。")
	@Column
	@CreatedDate
	var createdTime: LocalDateTime? = null
	
	@ApiModelProperty("最后更新时间。")
	@Column
	@LastModifiedDate
	var lastModifiedTime: LocalDateTime? = null
	
	
	override fun equals(other: Any?) = other is CollectTag && other.id == id
	
	override fun hashCode() = Objects.hash(super.hashCode(), id.hashCode())
}
