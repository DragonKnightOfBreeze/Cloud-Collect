package com.windea.demo.cloudcollect.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * 收藏的分类。
 * <p>
 * 一个收藏只能带有一个分类。
 */
@Data
@NoArgsConstructor
@Entity
public class CollectCategory implements Serializable {
	private static final long serialVersionUID = -8860788073635562232L;

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
	@NotEmpty(message = "validation.CollectCategory.name.NotEmpty")
	@Size(min = 1, max = 32, message = "validation.CollectCategory.name.Size")
	@Column(nullable = false, length = 32)
	private String name;

	/**
	 * 概述。
	 */
	@NotEmpty(message = "validation.CollectCategory.summary.NotEmpty")
	@Size(min = 1, max = 255, message = "validation.CollectCategory.summary.Size")
	@Column(nullable = false, columnDefinition = "text")
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

	/**
	 * 收藏列表。懒加载。
	 */
	@JsonIgnore
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "category")
	private List<Collect> collectList = new LinkedList<>();

	/**
	 * 收藏数量。
	 */
	@JsonGetter
	@Transient
	public Integer getCollectCount() {
		return collectList.size();
	}
}
