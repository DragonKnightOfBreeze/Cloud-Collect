package com.windea.demo.cloudcollect.core.domain.entity

import com.fasterxml.jackson.annotation.*
import com.windea.demo.cloudcollect.core.*
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

@ApiModel("收藏的分类")
@Entity
@EntityListeners(AuditingEntityListener::class)
@UniqueCategory(message = "{validation.Category.UniqueCategory}", groups = [Create::class])
data class Category(
	@ApiModelProperty("编号")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,

	@ApiModelProperty("名字")
	@get:NotEmpty(message = "{validation.Category.name.NotEmpty}", groups = [Create::class, Modify::class])
	@get:Size(max = 32, message = "{validation.Category.name.Size}", groups = [Create::class, Modify::class])
	@Column(nullable = false, length = 32)
	var name: String,

	@ApiModelProperty("概述")
	@get:Size(max = 255, message = "{validation.Category.summary.Size}", groups = [Create::class, Modify::class])
	@Column(nullable = false)
	var summary: String = "",

	@ApiModelProperty("所属用户")
	@ManyToOne
	val user: User
) : Serializable {
	@ApiModelProperty("创建时间")
	@Column
	@CreatedDate
	@JsonFormat(pattern = GlobalConfig.dateFormat)
	var createdTime: LocalDateTime? = null

	@ApiModelProperty("最后更新时间")
	@Column
	@LastModifiedDate
	@JsonFormat(pattern = GlobalConfig.dateFormat)
	var lastModifiedTime: LocalDateTime? = null

	@ApiModelProperty("相关的收藏列表")
	@JsonIgnore
	@OneToMany(mappedBy = "category", cascade = [CascadeType.DETACH])
	val collects: MutableList<Collect> = mutableListOf()

	@ApiModelProperty("收藏数量")
	@Transient
	var collectCount: Long = 0

	override fun equals(other: Any?) = other === this || (other is Category && other.id == id)

	override fun hashCode() = id.hashCode()
}
