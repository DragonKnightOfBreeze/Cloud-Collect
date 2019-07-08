package com.windea.demo.cloudcollect.core.domain.entity;

import com.windea.demo.cloudcollect.core.validation.annotation.UniqueCollectTag;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 收藏的标签。
 * <p>
 * 一个收藏可以带有多个标签。
 */
@Data
@NoArgsConstructor
@UniqueCollectTag
@Entity
public class CollectTag implements Serializable {
	private static final long serialVersionUID = 4229783797803970576L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 所属用户。
	 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private User user;

	/**
	 * 名字。
	 */
	@NotEmpty(message = "validation.CollectTag.name.NotEmpty")
	@Size(min = 1, max = 32, message = "validation.CollectTag.name.Size")
	@Column(nullable = false, length = 32)
	private String name;

	/**
	 * 概述。
	 */
	@NotEmpty(message = "validation.CollectTag.summary.NotEmpty")
	@Size(min = 1, max = 255, message = "validation.CollectTag.summary.Size")
	@Column(nullable = false)
	private String summary;

	/**
	 * 创建时间。
	 */
	@CreatedDate
	@Column
	private LocalDateTime createdTime;

	/**
	 * 最后更新时间。
	 */
	@LastModifiedDate
	@Column
	private LocalDateTime lastModifiedTime;
}
