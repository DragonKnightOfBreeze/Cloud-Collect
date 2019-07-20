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

/** 收藏。*/
@UniqueCollect
@Entity
data class Collect(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	/** 名字。*/
	@NotEmpty(message = "{validation.Collect.name.NotEmpty}")
	@Size(min = 1, max = 64, message = "{validation.Collect.name.Size}")
	@Column(nullable = false, length = 64)
	var name: String = "",
	
	/** 概述。*/
	@NotEmpty(message = "{validation.Collect.summary.NotEmpty}")
	@Size(min = 1, max = 255, message = "{validation.Collect.summary.Size}")
	@Column(nullable = false)
	var summary: String = "",
	
	/** 链接地址。*/
	@Column(nullable = false, length = 512)
	var url: String = "",
	
	/** 标志地址。*/
	@Column(length = 512)
	var logoUrl: String = "",
	
	/** 所属用户。*/
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	var user: User = User(),
	
	/** 收藏的分类。*/
	@ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.EAGER)
	var category: CollectCategory? = null,
	
	/** 收藏的标签。*/
	@ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], fetch = FetchType.EAGER)
	var tags: MutableSet<CollectTag> = mutableSetOf(),
	
	/** 收藏的类型。*/
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	var type: CollectType = CollectType.NONE,
	
	/** 删除状态。*/
	@Column
	var deleteStatus: Boolean = false,
	
	/** 创建时间。*/
	@CreatedDate
	@Column
	var createdTime: LocalDateTime? = null,
	
	/** 最后修改时间。*/
	@LastModifiedDate
	@Column
	var lastModifiedTime: LocalDateTime? = null,
	
	/** 点赞该收藏的用户列表。懒加载。*/
	@JsonIgnore
	@ManyToMany(cascade = [CascadeType.MERGE], mappedBy = "praiseToCollectList")
	var praiseByUserList: MutableList<User> = mutableListOf()
) : Serializable
