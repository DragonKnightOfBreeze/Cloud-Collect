package com.windea.demo.cloudcollect.core.domain.entity;

import com.windea.demo.cloudcollect.core.domain.enums.NoticeType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通知。
 */
@Data
@NoArgsConstructor
@Entity
public class Notice implements Serializable {
	private static final long serialVersionUID = -3954693936641334296L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 所属用户。
	 */
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private User user;

	/**
	 * 通知的类型。
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private NoticeType type;

	/**
	 * 相关收藏。
	 */
	@Nullable
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Collect relatedCollect;

	/**
	 * 相关评论。
	 */
	@Nullable
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Comment relatedComment;

	/**
	 * 是否已读。
	 */
	@Column(nullable = false)
	private Boolean read = false;

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
