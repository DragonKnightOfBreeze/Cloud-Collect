package com.windea.demo.cloudcollect.core.domain.entity;

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

/**
 * 评论。
 * <p>
 * 一个收藏可以带有多条评论。
 */
@Data
@NoArgsConstructor
@Entity
public class Comment implements Serializable {
	private static final long serialVersionUID = -5681480852773287799L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 所属收藏。
	 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Collect collect;

	/**
	 * 发起该评论的用户。
	 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private User sponsorByUser;

	/**
	 * 该评论回复的用户。
	 */
	@Nullable
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private User replyToUser;

	/**
	 * 内容。
	 */
	@NotEmpty(message = "validation.CollectComment.content.NotEmpty")
	@Size(min = 1, max = 255, message = "validation.CollectComment.content.Size")
	@Column(nullable = false, columnDefinition = "text")
	private String content;

	@CreatedDate
	@Column
	private LocalDateTime createdTime;

	@LastModifiedDate
	@Column
	private LocalDateTime lastModifiedTime;
}
