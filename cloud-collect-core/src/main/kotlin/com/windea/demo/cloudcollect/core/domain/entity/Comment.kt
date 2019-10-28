package com.windea.demo.cloudcollect.core.domain.entity

import com.windea.demo.cloudcollect.core.validation.group.*
import io.swagger.annotations.*
import org.springframework.data.annotation.*
import org.springframework.data.jpa.domain.support.*
import java.io.*
import java.time.*
import javax.persistence.*
import javax.persistence.Id
import javax.validation.constraints.*

@ApiModel("评论。一个收藏可以带有多条评论。")
@Entity
@EntityListeners(AuditingEntityListener::class)
data class Comment(
	@ApiModelProperty("编号。")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,
	
	@ApiModelProperty("内容。")
	@Column(nullable = false, length = 512)
	@get:NotEmpty(message = "{validation.CollectComment.content.NotEmpty}", groups = [Create::class])
	@get:Size(min = 1, max = 512, message = "{validation.CollectComment.content.Size}", groups = [Create::class])
	val content: String,
	
	@ApiModelProperty("所属收藏。")
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	val collect: Collect,
	
	@ApiModelProperty("发起该评论的用户。")
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	val sponsorByUser: User,
	
	@ApiModelProperty("该评论回复的评论。")
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)
	val replyToComment: Comment? = null
) : Serializable {
	@ApiModelProperty("创建时间。")
	@Column
	@CreatedDate
	lateinit var createdTime: LocalDateTime
	
	@ApiModelProperty("回复此评论的评论数量。")
	@Transient
	var replyByCommentCount: Long = 0
	
	override fun equals(other: Any?) = other === this || (other is Comment && other.id == id)
	
	override fun hashCode() = id.hashCode()
}
