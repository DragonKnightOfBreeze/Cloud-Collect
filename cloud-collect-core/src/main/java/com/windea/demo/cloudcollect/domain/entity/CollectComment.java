package com.windea.demo.cloudcollect.domain.entity;

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
 * 收藏的评论。
 * <p>
 * 一个收藏可以带有多条评论。
 */
@Data
@NoArgsConstructor
@Entity
public class CollectComment implements Serializable {
	private static final long serialVersionUID = -5681480852773287799L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private User sponsorUser;

	@Nullable
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn
	private User replyUser;

	@NotEmpty
	@Size(min = 1, max = 255)
	@Column(nullable = false, columnDefinition = "text")
	private String content;

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
