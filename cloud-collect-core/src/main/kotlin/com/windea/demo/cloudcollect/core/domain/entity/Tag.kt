package com.windea.demo.cloudcollect.core.domain.entity

import com.fasterxml.jackson.annotation.*
import com.windea.demo.cloudcollect.core.GlobalConfig.dateFormat
import com.windea.demo.cloudcollect.core.validation.annotation.*
import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.annotation.*
import org.springframework.data.jpa.domain.support.*
import java.io.*
import java.time.*
import javax.persistence.*
import javax.persistence.Id
import javax.persistence.Transient
import javax.validation.constraints.*

@ApiModel("收藏的标签。一个收藏可以带有多个标签。")
@Entity
@EntityListeners(AuditingEntityListener::class)
@UniqueTag(message = "{validation.Tag.UniqueTag}", groups = [Create::class])
data class Tag(
	@ApiModelProperty("编号。")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,
	
	@ApiModelProperty("名字。")
	@get:NotEmpty(message = "{validation.Tag.name.NotEmpty}", groups = [Create::class, Modify::class])
	@get:Size(max = 64, message = "{validation.Tag.name.Size}", groups = [Create::class, Modify::class])
	@Column(nullable = false, length = 64)
	var name: String,
	
	@ApiModelProperty("概述。")
	@get:Size(max = 255, message = "{validation.Tag.summary.Size}", groups = [Create::class, Modify::class])
	@Column(nullable = false)
	var summary: String = "",
	
	@ApiModelProperty("所属用户。")
	@ManyToOne
	val user: User
) : Serializable {
	@ApiModelProperty("创建时间。")
	@Column
	@CreatedDate
	@JsonFormat(pattern = dateFormat)
	var createdTime: LocalDateTime? = null
	
	@ApiModelProperty("最后更新时间。")
	@Column
	@LastModifiedDate
	@JsonFormat(pattern = dateFormat)
	var lastModifiedTime: LocalDateTime? = null
	
	@ApiModelProperty("收藏数量。")
	@Transient
	var collectCount: Long = 0
	
	override fun equals(other: Any?) = other === this || (other is Tag && other.id == id)
	
	override fun hashCode() = id.hashCode()
}
