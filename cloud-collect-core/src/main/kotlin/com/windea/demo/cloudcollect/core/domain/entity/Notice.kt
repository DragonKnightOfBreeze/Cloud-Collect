package com.windea.demo.cloudcollect.core.domain.entity

import com.windea.demo.cloudcollect.core.domain.enums.*
import org.springframework.data.annotation.*
import org.springframework.data.jpa.domain.support.*
import java.io.*
import java.time.*
import java.util.*
import javax.persistence.*
import javax.persistence.Id

/**通知。*/
@Entity
@EntityListeners(AuditingEntityListener::class)
class Notice(
	/**编号。*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	/**标题。*/
	@Column(nullable = false)
	var title: String,
	
	/**内容。*/
	@Column(nullable = false, length = 32)
	var content: String = "……",
	
	/**通知的类型。*/
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	var type: NoticeType = NoticeType.SYSTEM,
	
	@Column
	/**是否已读。*/
	var readStatus: Boolean = false,
	
	/**所属用户。*/
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	var user: User
) : Serializable {
	/**创建时间。*/
	@Column
	@CreatedDate
	var createdTime: LocalDateTime? = null
	
	/**最后更新时间。*/
	@Column
	@LastModifiedDate
	var lastModifiedTime: LocalDateTime? = null
	
	
	override fun equals(other: Any?) = other is Notice && other.id == id
	
	override fun hashCode() = Objects.hash(super.hashCode(), id.hashCode())
}
