package com.windea.demo.cloudcollect.core.domain.entity

import com.fasterxml.jackson.annotation.*
import com.windea.demo.cloudcollect.core.GlobalConfig.dateFormat
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
	
	@ApiModelProperty("收藏。")
	@ManyToOne
	val collect: Collect,
	
	@ApiModelProperty("用户。")
	@ManyToOne
	val user: User
) : Serializable {
	@ApiModelProperty("创建时间。")
	@Column
	@CreatedDate
	@JsonFormat(pattern = dateFormat)
	var createdTime: LocalDateTime? = null
	
	override fun equals(other: Any?) = other === this || (other is History && other.id == id)
	
	override fun hashCode() = id.hashCode()
}
