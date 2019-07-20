package com.windea.demo.cloudcollect.core.domain.entity

import org.springframework.data.annotation.*
import java.io.*
import java.time.*
import javax.persistence.*
import javax.persistence.Id
import javax.validation.constraints.*

/**
 * 评论。
 *
 * 一个收藏可以带有多条评论。
 */
@Entity
class Comment(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	/** 内容。*/
	@NotEmpty(message = "{validation.CollectComment.content.NotEmpty}")
	@Size(min = 1, max = 255, message = "{validation.CollectComment.content.Size}")
	@Column(nullable = false)
	var content: String = "",
	
	/** 所属收藏。*/
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	var collect: Collect = Collect(),
	
	/** 发起该评论的用户。*/
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	var sponsorByUser: User = User(),
	
	/** 该评论回复的评论。*/
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)
	var replyToComment: Comment? = null,
	
	/** 创建时间。*/
	@CreatedDate
	@Column
	var createdTime: LocalDateTime? = null,
	
	/** 最后更新时间。*/
	@LastModifiedDate
	@Column
	var lastModifiedTime: LocalDateTime? = null
) : Serializable
