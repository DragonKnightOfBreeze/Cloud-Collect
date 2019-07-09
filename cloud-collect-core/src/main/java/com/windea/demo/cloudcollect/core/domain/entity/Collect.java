package com.windea.demo.cloudcollect.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.windea.demo.cloudcollect.core.domain.enums.CollectType;
import com.windea.demo.cloudcollect.core.validation.annotation.UniqueCollect;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 收藏。
 */
@Data
@NoArgsConstructor
@UniqueCollect
@Entity
public class Collect implements Serializable {
	private static final long serialVersionUID = -6764369348818887548L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 名字。
	 */
	@NonNull
	@NotEmpty(message = "validation.Collect.name.NotEmpty")
	@Size(min = 1, max = 64, message = "validation.Collect.name.Size")
	@Column(nullable = false, length = 64)
	private String name = "";

	/**
	 * 概述。
	 */
	@NonNull
	@NotEmpty(message = "validation.Collect.summary.NotEmpty")
	@Size(min = 1, max = 255, message = "validation.Collect.summary.Size")
	@Column(nullable = false)
	private String summary = "";

	/**
	 * 链接地址。
	 */
	@NonNull
	@Column(nullable = false, length = 512)
	private String url = "";

	/**
	 * 标志地址。
	 */
	@Nullable
	@Column(length = 512)
	private String logoUrl = "";

	/**
	 * 收藏的分类。
	 */
	@Nullable
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private CollectCategory category;

	/**
	 * 收藏的标签。
	 */
	@NonNull
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	private Set<CollectTag> tags = new LinkedHashSet<>();

	/**
	 * 收藏的类型。
	 */
	@NonNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CollectType type = CollectType.NONE;

	/**
	 * 删除状态。
	 */
	@NonNull
	@Column
	private Boolean deleteStatus = false;

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
	 * 最后修改时间。
	 */
	@LastModifiedDate
	@Column
	private LocalDateTime lastModifiedTime;

	/**
	 * 点赞该收藏的用户列表。懒加载。
	 */
	@NonNull
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "praiseToCollectList")
	private List<User> praiseByUserList = new LinkedList<>();

	public Collect(String name, String summary, String url, @Nullable String logoUrl, User user) {
		this.name = name;
		this.summary = summary;
		this.url = url;
		this.logoUrl = logoUrl;
		this.user = user;
	}
}
