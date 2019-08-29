package com.windea.demo.cloudcollect.core.domain.entity

import com.fasterxml.jackson.annotation.*
import com.windea.demo.cloudcollect.core.domain.enums.*
import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.data.annotation.*
import java.io.*
import java.time.*
import javax.persistence.*
import javax.persistence.Id
import javax.validation.constraints.*

/**收藏。*/
@Entity
@UniqueCollect
open class Collect(
	/**编号。*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	/**名字。*/
	@Column(nullable = false, length = 64)
	@field:NotEmpty(message = "{validation.Collect.name.NotEmpty}")
	@field:Size(min = 1, max = 64, message = "{validation.Collect.name.Size}")
	var name: String,
	
	/**概述。*/
	@Column(nullable = false)
	@field:NotEmpty(message = "{validation.Collect.summary.NotEmpty}")
	@field:Size(min = 1, max = 255, message = "{validation.Collect.summary.Size}")
	var summary: String = "……",
	
	/**链接地址。*/
	@Column(nullable = false, length = 512)
	var url: String,
	
	/**标志地址。*/
	@Column(length = 512)
	var logoUrl: String,
	
	/**收藏的分类。*/
	@ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.EAGER)
	var category: CollectCategory? = null,
	
	/**收藏的标签。*/
	@ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.EAGER)
	var tags: MutableSet<CollectTag> = mutableSetOf(),
	
	/**收藏的类型。*/
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	var type: CollectType = CollectType.NONE,
	
	/**所属用户。*/
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	var user: User,
	
	/**创建时间。*/
	@Column
	@CreatedDate
	var createdTime: LocalDateTime? = null,
	
	/**最后修改时间。*/
	@Column
	@LastModifiedDate
	var lastModifiedTime: LocalDateTime? = null
) : Serializable {
	/**评论列表。*/
	@JsonIgnore
	@OneToMany(cascade = [CascadeType.ALL], mappedBy = "collect")
	val commentList: MutableList<Comment> = mutableListOf()
	
	/**点赞该收藏的用户列表。懒加载。*/
	@JsonIgnore
	@ManyToMany(cascade = [CascadeType.MERGE], mappedBy = "praiseToCollectList")
	val praiseByUserList: MutableList<User> = mutableListOf()
	
	/**评论数量。*/
	val commentCount: Int get() = commentList.size
	
	/**点赞该收藏的用户数量。*/
	val praiseByUserCount get() = praiseByUserList.count()
	
	
	override fun equals(other: Any?) = other is Collect && other.id == id
	
	override fun hashCode() = id.hashCode()
}
