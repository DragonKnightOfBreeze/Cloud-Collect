package com.windea.demo.cloudcollect.core.domain.entity;

import com.windea.demo.cloudcollect.core.validation.annotation.UniqueCollectCategory;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 收藏的分类。
 * <p>
 * 一个收藏只能带有一个分类。
 */
@Data
@NoArgsConstructor
@UniqueCollectCategory
@Entity
public class CollectCategory implements Serializable {
	private static final long serialVersionUID = -8860788073635562232L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 名字。
	 */
	@NonNull
	@NotEmpty(message = "validation.CollectCategory.name.NotEmpty")
	@Size(min = 1, max = 32, message = "validation.CollectCategory.name.Size")
	@Column(nullable = false, length = 32)
	private String name = "";

	/**
	 * 概述。
	 */
	@NonNull
	@NotEmpty(message = "validation.CollectCategory.summary.NotEmpty")
	@Size(min = 1, max = 255, message = "validation.CollectCategory.summary.Size")
	@Column(nullable = false)
	private String summary = "";

	/**
	 * 所属用户。
	 */
	@NonNull
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, optional = false)
	private User user;

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

	public CollectCategory(String name, String summary, User user) {
		this.name = name;
		this.summary = summary;
		this.user = user;
	}
}
