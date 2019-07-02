package com.windea.demo.cloudcollect.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.windea.demo.cloudcollect.core.domain.enums.CollectPrivacy;
import com.windea.demo.cloudcollect.core.domain.enums.CollectType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
@Entity
public class Collect implements Serializable {
	private static final long serialVersionUID = -6764369348818887548L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 所属用户。
	 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private User user;

	/**
	 * 标题。
	 */
	@NotEmpty(message = "validation.Collect.title.NotEmpty")
	@Size(min = 1, max = 64, message = "validation.Collect.title.Size")
	@Column(nullable = false, length = 64)
	private String title;

	/**
	 * 链接地址。
	 */
	@Column(nullable = false, length = 512)
	private String url;

	/**
	 * 标志地址。
	 */
	@Nullable
	@Column(length = 512)
	private String logoUrl;

	/**
	 * 概述。
	 */
	@NotEmpty(message = "validation.Collect.summary.NotEmpty")
	@Size(min = 1, max = 255, message = "validation.Collect.summary.Size")
	@Column(nullable = false, columnDefinition = "text")
	private String summary;

	/**
	 * 收藏的分类。
	 */
	@Nullable
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private CollectCategory category;

	/**
	 * 收藏的标签。
	 */
	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Set<CollectTag> tags = new LinkedHashSet<>();

	/**
	 * 收藏的类型。
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CollectType type = CollectType.NONE;

	/**
	 * 收藏的隐私权限。
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CollectPrivacy privacy = CollectPrivacy.PUBLIC;

	/**
	 * 点赞信息。
	 */
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "collect")
	private Praise praise = new Praise();

	/**
	 * 评论列表。
	 */
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "collect")
	private List<Comment> commentList = new LinkedList<>();

	/**
	 * 是否已经删除。
	 */
	@Column(nullable = false)
	private Boolean deleted = false;

	@CreatedDate
	@Column
	private LocalDateTime createdTime;

	@LastModifiedDate
	@Column
	private LocalDateTime lastModifiedTime;
}
