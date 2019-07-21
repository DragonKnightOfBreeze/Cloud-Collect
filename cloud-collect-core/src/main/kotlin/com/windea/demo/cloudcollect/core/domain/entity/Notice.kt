package com.windea.demo.cloudcollect.core.domain.entity

import com.windea.demo.cloudcollect.core.domain.enums.*
import org.springframework.data.annotation.*
import java.io.*
import java.time.*
import javax.persistence.*
import javax.persistence.Id

/**通知。*/
@Entity
data class Notice(
	/**标题。*/
	@Column(nullable = false)
	var title: String = "",
	
	/**内容。*/
	@Column(nullable = false, length = 32)
	var content: String = "",
	
	/**通知的类型。*/
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	var type: NoticeType = NoticeType.SYSTEM,
	
	/**所属用户。*/
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	var user: User = User()
) : Serializable {
	/**编号。*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null
	
	@Column
		/**是否已读。*/
	var isRead: Boolean = false
	
	/**创建时间。*/
	@Column
	@CreatedDate
	var createdTime: LocalDateTime? = null
	
	/**最后更新时间。*/
	@Column
	@LastModifiedDate
	var lastModifiedTime: LocalDateTime? = null
	
	
	override fun equals(other: Any?): Boolean {
		return other is Notice && other.id == id
	}
}
