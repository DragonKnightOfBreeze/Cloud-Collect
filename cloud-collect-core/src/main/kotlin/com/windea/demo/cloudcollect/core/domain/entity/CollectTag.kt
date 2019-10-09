package com.windea.demo.cloudcollect.core.domain.entity

import com.windea.demo.cloudcollect.core.validation.annotation.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.annotation.*
import org.springframework.data.jpa.domain.support.*
import java.io.*
import java.time.*
import javax.persistence.*
import javax.persistence.Id
import javax.validation.constraints.*

@ApiModel("收藏的标签。一个收藏可以带有多个标签。")
@Entity
@EntityListeners(AuditingEntityListener::class)
@UniqueCollectTag(groups = [Create::class])
data class CollectTag(
	@ApiModelProperty("编号。")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	@ApiModelProperty("名字。")
	@field:NotEmpty(message = "{validation.CollectTag.name.NotEmpty}", groups = [Create::class, Modify::class])
	@field:Size(min = 1, max = 64, message = "{validation.CollectTag.name.Size}", groups = [Create::class, Modify::class])
	@Column(nullable = false, length = 64)
	var name: String,
	
	@ApiModelProperty("概述。")
	@field:NotEmpty(message = "{validation.CollectTag.summary.NotEmpty}", groups = [Create::class, Modify::class])
	@field:Size(min = 1, max = 255, message = "{validation.CollectTag.summary.Size}", groups = [Create::class, Modify::class])
	@Column(nullable = false)
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
	
	@ApiModelProperty("收藏数量。")
	@Transient
	var collectCount: Long = 0
	
	
	override fun equals(other: Any?) = other === this || (other is CollectTag && other.id == id)
	
	override fun hashCode() = id.hashCode()
}
