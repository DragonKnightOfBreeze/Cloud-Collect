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

@ApiModel("收藏的分类。一个收藏可以有多个分类。")
@Entity
@EntityListeners(AuditingEntityListener::class)
@UniqueCollectCategory(groups = [Create::class])
data class CollectCategory(
	@ApiModelProperty("编号。")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	@ApiModelProperty("名字。")
	@field:NotEmpty(message = "{validation.CollectCategory.name.NotEmpty}", groups = [Create::class, Modify::class])
	@field:Size(min = 1, max = 64, message = "{validation.CollectCategory.name.Size}", groups = [Create::class, Modify::class])
	@Column(nullable = false, length = 64)
	var name: String,
	
	@ApiModelProperty("概述。")
	@field:NotEmpty(message = "{validation.CollectCategory.summary.NotEmpty}", groups = [Create::class, Modify::class])
	@field:Size(min = 1, max = 255, message = "{validation.CollectCategory.summary.Size}", groups = [Create::class, Modify::class])
	@Column(nullable = false)
	var summary: String = "",
	
	@ApiModelProperty("所属用户。")
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	var user: User
) : Serializable {
	@ApiModelProperty("创建时间。")
	@Column
	@CreatedDate
	lateinit var createdTime: LocalDateTime
	
	@ApiModelProperty("最后更新时间。")
	@Column
	@LastModifiedDate
	lateinit var lastModifiedTime: LocalDateTime
	
	@ApiModelProperty("收藏数量。")
	@Transient
	var collectCount: Long = 0
	
	
	override fun equals(other: Any?) = other === this || (other is CollectCategory && other.id == id)
	
	override fun hashCode() = id.hashCode()
}
