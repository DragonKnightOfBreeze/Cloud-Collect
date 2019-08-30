package com.windea.demo.cloudcollect.core.domain.entity

import com.windea.demo.cloudcollect.core.validation.annotation.*
import com.windea.demo.cloudcollect.core.validation.group.*
import org.springframework.data.annotation.*
import org.springframework.data.jpa.domain.support.*
import java.io.*
import java.time.*
import java.util.*
import javax.persistence.*
import javax.persistence.Id
import javax.validation.constraints.*

/**收藏的分类。一个收藏可以有多个分类。*/
@Entity
@EntityListeners(AuditingEntityListener::class)
@UniqueCollectCategory
class CollectCategory(
	/**编号。*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	var id: Long? = null,
	
	/**名字。*/
	@Column(nullable = false, length = 32)
	@field:NotEmpty(message = "{validation.CollectCategory.name.NotEmpty}", groups = [Default::class])
	@field:Size(min = 1, max = 32, message = "{validation.CollectCategory.name.Size}", groups = [Default::class])
	var name: String,
	
	/**概述。*/
	@Column(nullable = false)
	@field:NotEmpty(message = "{validation.CollectCategory.summary.NotEmpty}", groups = [Default::class])
	@field:Size(min = 1, max = 255, message = "{validation.CollectCategory.summary.Size}", groups = [Default::class])
	var summary: String = "……",
	
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
	
	
	override fun equals(other: Any?) = other is CollectCategory && other.id == id
	
	override fun hashCode() = Objects.hash(super.hashCode(), id.hashCode())
}
