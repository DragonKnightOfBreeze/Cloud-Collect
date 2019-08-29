package com.windea.demo.cloudcollect.core.domain.entity

import org.springframework.data.annotation.*
import java.io.*
import java.time.*
import java.util.*
import javax.persistence.*
import javax.persistence.Id
import javax.validation.constraints.*

/**评论。一个收藏可以带有多条评论。*/
@Entity
class Comment(
	/**编号。*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	/**内容。*/
	@Column(nullable = false)
	@field:NotEmpty(message = "{validation.CollectComment.content.NotEmpty}")
	@field:Size(min = 1, max = 255, message = "{validation.CollectComment.content.Size}")
	var content: String = "……",
	
	/**所属收藏。*/
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	var collect: Collect,
	
	/**发起该评论的用户。*/
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	var sponsorByUser: User,
	
	/**该评论回复的评论。*/
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)
	var replyToComment: Comment? = null,
	
	/**创建时间。*/
	@Column
	@CreatedDate
	var createdTime: LocalDateTime? = null,
	
	/**最后更新时间。*/
	@Column
	@LastModifiedDate
	var lastModifiedTime: LocalDateTime? = null
) : Serializable {
	override fun equals(other: Any?) = other is Comment && other.id == id
	
	override fun hashCode() = Objects.hash(super.hashCode(), id.hashCode())
}
