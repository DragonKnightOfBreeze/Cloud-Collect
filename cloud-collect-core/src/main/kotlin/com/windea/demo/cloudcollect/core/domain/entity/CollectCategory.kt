package com.windea.demo.cloudcollect.core.domain.entity

import com.windea.demo.cloudcollect.core.validation.annotation.*
import org.springframework.data.annotation.*
import java.io.*
import java.time.*
import javax.persistence.*
import javax.persistence.Id
import javax.validation.constraints.*

/**收藏的分类。一个收藏可以有多个分类。*/
@Entity
@UniqueCollectCategory
data class CollectCategory(
	/**名字。*/
	@Column(nullable = false, length = 32)
	@NotEmpty(message = "{validation.CollectCategory.name.NotEmpty}")
	@Size(min = 1, max = 32, message = "{validation.CollectCategory.name.Size}")
	var name: String = "",
	
	/**概述。*/
	@Column(nullable = false)
	@NotEmpty(message = "{validation.CollectCategory.summary.NotEmpty}")
	@Size(min = 1, max = 255, message = "{validation.CollectCategory.summary.Size}")
	var summary: String = "",
	
	/**所属用户。*/
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	var user: User = User()
) : Serializable {
	/**编号。*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null
	
	/**创建时间。*/
	@Column
	@CreatedDate
	var createdTime: LocalDateTime? = null
	
	/**最后更新时间。*/
	@Column
	@LastModifiedDate
	var lastModifiedTime: LocalDateTime? = null
	
	
	override fun equals(other: Any?) = other is CollectCategory && other.id == id
	
	override fun hashCode() = id.hashCode()
}
