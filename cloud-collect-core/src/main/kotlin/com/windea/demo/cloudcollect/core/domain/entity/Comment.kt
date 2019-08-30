package com.windea.demo.cloudcollect.core.domain.entity

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

@ApiModel("评论。一个收藏可以带有多条评论。")
@Entity
@EntityListeners(AuditingEntityListener::class)
class Comment(
	@ApiModelProperty("编号。")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	@ApiModelProperty("内容。")
	@Column(nullable = false)
	@field:NotEmpty(message = "{validation.CollectComment.content.NotEmpty}", groups = [Default::class])
	@field:Size(min = 1, max = 255, message = "{validation.CollectComment.content.Size}", groups = [Default::class])
	var content: String = "……",
	
	@ApiModelProperty("所属收藏。")
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	var collect: Collect,
	
	@ApiModelProperty("发起该评论的用户。")
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	var sponsorByUser: User,
	
	@ApiModelProperty("该评论回复的评论。")
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)
	var replyToComment: Comment? = null
) : Serializable {
	@ApiModelProperty("回复此评论的评论数量。")
	@Transient
	var replyByCommentCount: Long = 0
	
	@ApiModelProperty("创建时间。")
	@Column
	@CreatedDate
	lateinit var createdTime: LocalDateTime
	
	@ApiModelProperty("最后更新时间。")
	@Column
	@LastModifiedDate
	lateinit var lastModifiedTime: LocalDateTime
	
	
	override fun equals(other: Any?) = other is Comment && other.id == id
	
	override fun hashCode() = Objects.hash(super.hashCode(), id.hashCode())
}
