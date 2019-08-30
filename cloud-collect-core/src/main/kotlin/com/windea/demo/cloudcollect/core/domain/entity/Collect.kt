package com.windea.demo.cloudcollect.core.domain.entity

import com.fasterxml.jackson.annotation.*
import com.windea.demo.cloudcollect.core.domain.enums.*
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
import javax.validation.*
import javax.validation.constraints.*

@ApiModel("收藏。")
@Entity
@EntityListeners(AuditingEntityListener::class)
@UniqueCollect
class Collect(
	@ApiModelProperty("编号。")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	@ApiModelProperty("名字。")
	@Column(nullable = false, length = 64)
	@field:NotEmpty(message = "{validation.Collect.name.NotEmpty}", groups = [Default::class])
	@field:Size(min = 1, max = 64, message = "{validation.Collect.name.Size}", groups = [Default::class])
	var name: String,
	
	@ApiModelProperty("概述。")
	@Column(nullable = false)
	@field:NotEmpty(message = "{validation.Collect.summary.NotEmpty}", groups = [Default::class])
	@field:Size(min = 1, max = 255, message = "{validation.Collect.summary.Size}", groups = [Default::class])
	var summary: String = "……",
	
	@ApiModelProperty("链接地址。")
	@Column(nullable = false, length = 512)
	var url: String,
	
	@ApiModelProperty("标志地址。")
	@Column(length = 512)
	var logoUrl: String,
	
	@ApiModelProperty("收藏的分类。")
	@ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.EAGER)
	@field:Valid
	var category: CollectCategory? = null,
	
	@ApiModelProperty("收藏的标签。")
	@ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.EAGER)
	@field:Valid
	var tags: MutableSet<CollectTag> = mutableSetOf(),
	
	@ApiModelProperty("收藏的类型。")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	var type: CollectType = CollectType.NONE,
	
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
	
	@ApiModelProperty("点赞该收藏的用户列表。懒加载。")
	@JsonIgnore
	@ManyToMany(cascade = [CascadeType.MERGE], mappedBy = "praiseToCollectList")
	val praiseByUserList: MutableList<User> = mutableListOf()
	
	
	override fun equals(other: Any?) = other is Collect && other.id == id
	
	override fun hashCode() = Objects.hash(super.hashCode(), id.hashCode())
}
