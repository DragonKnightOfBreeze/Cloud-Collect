package com.windea.demo.cloudcollect.core.domain.entity

import com.windea.demo.cloudcollect.core.domain.enums.*
import io.swagger.annotations.*
import org.springframework.data.annotation.*
import org.springframework.data.jpa.domain.support.*
import java.io.*
import java.time.*
import java.util.*
import javax.persistence.*
import javax.persistence.Id

@ApiModel("通知。")
@Entity
@EntityListeners(AuditingEntityListener::class)
class Notice(
	@ApiModelProperty("编号。")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	@ApiModelProperty("标题。")
	@Column(nullable = false)
	var title: String,
	
	@ApiModelProperty("内容。")
	@Column(nullable = false, length = 32)
	var content: String = "……",
	
	@ApiModelProperty("通知的类型。")
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	var type: NoticeType = NoticeType.SYSTEM,
	
	@Column
	@ApiModelProperty("是否已读。")
	var readStatus: Boolean = false,
	
	@ApiModelProperty("所属用户。")
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	var user: User
) : Serializable {
	@ApiModelProperty("创建时间。")
	@Column
	@CreatedDate
	lateinit var createdTime: LocalDateTime
	
	@ApiModelProperty("最后更新时间。")
	@Column
	@LastModifiedDate
	lateinit var lastModifiedTime: LocalDateTime
	
	
	override fun equals(other: Any?) = other is Notice && other.id == id
	
	override fun hashCode() = Objects.hash(super.hashCode(), id.hashCode())
}
