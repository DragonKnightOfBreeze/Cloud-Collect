package com.windea.demo.cloudcollect.core.domain.entity

import io.swagger.annotations.*
import org.springframework.data.annotation.*
import org.springframework.data.jpa.domain.support.*
import java.io.*
import java.time.*
import javax.persistence.*
import javax.persistence.Id

@ApiModel("浏览记录。")
@Entity
@EntityListeners(AuditingEntityListener::class)
data class History(
	@ApiModelProperty("编号。")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	val id: Long = 0,
	
	//不做任何级联，迫切加载
	@ApiModelProperty("收藏。")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	val collect: Collect,
	
	@ApiModelProperty("用户。")
	@ManyToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, optional = false)
	val user: User
) : Serializable {
	@Column
	@CreatedDate
	lateinit var createdTime: LocalDateTime
	
	override fun equals(other: Any?) = other === this || (other is History && other.id == id)
	
	override fun hashCode() = id.hashCode()
}
