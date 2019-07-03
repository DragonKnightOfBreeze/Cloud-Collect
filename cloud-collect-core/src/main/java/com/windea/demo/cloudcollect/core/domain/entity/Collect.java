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
	 * 名字。
	 */
	@NotEmpty(message = "validation.Collect.name.NotEmpty")
	@Size(min = 1, max = 64, message = "validation.Collect.name.Size")
	@Column(nullable = false, length = 64)
	private String name;

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
	 * 是否已经删除。
	 */
	@Column(nullable = false)
	private Boolean deleted = false;

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
	 * 点赞该收藏的用户列表。
	 */
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "collect")
	private List<User> praiseByUserList = new LinkedList<>();


	/**
	 * 点赞该收藏的用户数量。
	 */
	@Transient
	public Integer getPraiseByUserCount() {
		return praiseByUserList.size();
	}
}
