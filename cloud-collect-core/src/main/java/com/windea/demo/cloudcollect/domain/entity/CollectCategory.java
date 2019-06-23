package com.windea.demo.cloudcollect.domain.entity;

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
	private Integer id;

	@NotEmpty(message = "validation.CollectCategory.name.NotEmpty")
	@Size(min = 1, max = 32, message = "validation.CollectCategory.name.Size")
	@Column(nullable = false, length = 32)
	private String name;

	@NotEmpty(message = "validation.CollectCategory.summary.NotEmpty")
	@Size(min = 1, max = 255, message = "validation.CollectCategory.summary.Size")
	@Column(nullable = false, columnDefinition = "text")
	private String summary;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private User user;

	@CreatedDate
	@Column
	private LocalDateTime createdTime;

	@LastModifiedDate
	@Column
	private LocalDateTime lastModifiedTime;
}
