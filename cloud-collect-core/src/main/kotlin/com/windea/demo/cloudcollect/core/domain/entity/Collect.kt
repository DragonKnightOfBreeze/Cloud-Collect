package com.windea.demo.cloudcollect.core.domain.entity

import com.fasterxml.jackson.annotation.*
import com.windea.demo.cloudcollect.core.*
import com.windea.demo.cloudcollect.core.enums.*
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

@ApiModel("收藏。")
@Entity
@EntityListeners(AuditingEntityListener::class)
@UniqueCollect(message = "{validation.Collect.UniqueCollect}", groups = [Create::class])
data class Collect(
	@ApiModelProperty("编号。")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0, //NOTE 其类型可为Long，默认值推荐为0
	
	@ApiModelProperty("名字。")
	@get:NotEmpty(message = "{validation.Collect.name.NotEmpty}", groups = [Create::class, Modify::class])
	@get:Size(max = 64, message = "{validation.Collect.name.Size}", groups = [Create::class, Modify::class])
	@Column(nullable = false, length = 64)
	var name: String,
	
	@ApiModelProperty("概述。")
	@get:Size(max = 255, message = "{validation.Collect.summary.Size}", groups = [Create::class, Modify::class])
	@Column(nullable = false)
	var summary: String = "",
	
	@ApiModelProperty("链接地址。")
	@Column(nullable = false, length = 512)
	var url: String,
	
	@ApiModelProperty("标志地址。")
	@Column(nullable = false, length = 512)
	var logoUrl: String,
	
	@ApiModelProperty("收藏的分类。")
	@ManyToOne
	var category: Category? = null,
	
	@ApiModelProperty("收藏的标签。")
	@ManyToMany(fetch = FetchType.EAGER)
	@JvmSuppressWildcards //NOTE 防止Jpa报错
	var tags: Set<Tag> = setOf(),
	
	@ApiModelProperty("收藏的类型。")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	var type: CollectType = CollectType.NONE,
	
	@ApiModelProperty("所属用户。")
	@ManyToOne
	val user: User
) : Serializable {
	@ApiModelProperty("创建时间。")
	@Column
	@CreatedDate
	@JsonFormat(pattern = GlobalConfig.dateFormat)
	var createdTime: LocalDateTime? = null
	
	@ApiModelProperty("最后更新时间。")
	@Column
	@LastModifiedDate
	@JsonFormat(pattern = GlobalConfig.dateFormat)
	var lastModifiedTime: LocalDateTime? = null
	
	@ApiModelProperty("点赞该收藏的用户列表。懒加载。")
	@JsonIgnore
	@ManyToMany(mappedBy = "praiseToCollectList")
	val praiseByUserList: MutableList<User> = mutableListOf()
	
	@ApiModelProperty("点赞用户数量。")
	@Transient
	var praiseByUserCount: Long = 0
	
	@ApiModelProperty("评论数量。")
	@Transient
	var commentCount: Long = 0
	
	override fun equals(other: Any?) = other === this || (other is Collect && other.id == id)
	
	override fun hashCode() = id.hashCode()
}
