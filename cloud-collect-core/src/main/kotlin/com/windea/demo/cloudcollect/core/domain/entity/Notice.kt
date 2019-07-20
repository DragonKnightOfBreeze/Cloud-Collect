package com.windea.demo.cloudcollect.core.domain.entity

import com.windea.demo.cloudcollect.core.domain.enums.*
import org.springframework.data.annotation.*
import java.io.*
import java.time.*
import javax.persistence.*
import javax.persistence.Id

/**通知。*/
@Entity
class Notice(
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	/** 标题。*/
	@Column(nullable = false)
	var title: String = "",
	
	/** 内容。*/
	@Column(nullable = false, length = 32)
	var content: String = "",
	
	/** 通知的类型。*/
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	var type: NoticeType = NoticeType.SYSTEM,
	
	/** 阅读状态。*/
	@Column
	var readStatus: Boolean = false,
	
	/** 所属用户。*/
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	var user: User = User(),
	
	/** 创建时间。*/
	@CreatedDate
	@Column
	var createdTime: LocalDateTime? = null,
	
	/** 最后更新时间。*/
	@LastModifiedDate
	@Column
	var lastModifiedTime: LocalDateTime? = null
) : Serializable
