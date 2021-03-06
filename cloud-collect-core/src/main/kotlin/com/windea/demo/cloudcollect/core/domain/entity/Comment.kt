package com.windea.demo.cloudcollect.core.domain.entity

import com.fasterxml.jackson.annotation.*
import com.windea.demo.cloudcollect.core.*
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

@ApiModel("评论。一个收藏可以带有多条评论。")
@Entity
@EntityListeners(AuditingEntityListener::class)
data class Comment(
	@ApiModelProperty("编号。")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,
	
	@ApiModelProperty("内容。")
	@get:NotEmpty(message = "{validation.Comment.content.NotEmpty}", groups = [Create::class])
	@get:Size(max = 255, message = "{validation.Comment.content.Size}", groups = [Create::class])
	@Column(nullable = false)
	val content: String,
	
	@ApiModelProperty("所属收藏。")
	@ManyToOne
	val collect: Collect,
	
	@ApiModelProperty("发起该评论的用户。")
	@ManyToOne
	val sponsorByUser: User,
	
	@ApiModelProperty("该评论回复的评论。")
	@ManyToOne
	val replyToComment: Comment? = null
) : Serializable {
	@ApiModelProperty("创建时间。")
	@Column
	@CreatedDate
	@JsonFormat(pattern = GlobalConfig.dateFormat)
	var createdTime: LocalDateTime? = null
	
	@ApiModelProperty("回复该评论的评论列表。")
	@JsonIgnore
	@OneToMany(mappedBy = "replyToComment", cascade = [CascadeType.DETACH])
	val replyByComments: MutableList<Comment> = mutableListOf()
	
	@ApiModelProperty("回复此评论的评论数量。")
	@Transient
	var replyToCommentCount: Long = 0
	
	override fun equals(other: Any?) = other === this || (other is Comment && other.id == id)
	
	override fun hashCode() = id.hashCode()
}
