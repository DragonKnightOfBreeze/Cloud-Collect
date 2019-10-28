package com.windea.demo.cloudcollect.core.domain.entity

import com.windea.demo.cloudcollect.core.enums.*
import io.swagger.annotations.*
import org.springframework.data.annotation.*
import org.springframework.data.jpa.domain.support.*
import java.io.*
import java.time.*
import javax.persistence.*
import javax.persistence.Id

@ApiModel("通知。")
@Entity
@EntityListeners(AuditingEntityListener::class)
data class Notice(
	@ApiModelProperty("编号。")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,
	
	@ApiModelProperty("标题。")
	@Column(nullable = false)
	val title: String,
	
	@ApiModelProperty("内容。")
	@Column(nullable = false, length = 512)
	val content: String,
	
	@ApiModelProperty("通知的类型。")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	val type: NoticeType = NoticeType.SYSTEM,
	
	@ApiModelProperty("所属用户。")
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	val user: User
) : Serializable {
	@Column
	@ApiModelProperty("是否已读。")
	var readStatus: Boolean = false
	
	@ApiModelProperty("创建时间。")
	@Column
	@CreatedDate
	lateinit var createdTime: LocalDateTime
	
	override fun equals(other: Any?) = other === this || (other is Notice && other.id == id)
	
	override fun hashCode() = id.hashCode()
}
