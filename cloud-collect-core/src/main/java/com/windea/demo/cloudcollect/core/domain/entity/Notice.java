package com.windea.demo.cloudcollect.core.domain.entity;

import com.windea.demo.cloudcollect.core.domain.enums.NoticeType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.NonNull;

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
	 * 标题。
	 */
	@NonNull
	@Column(nullable = false)
	private String title = "";

	/**
	 * 内容。
	 */
	@NonNull
	@Column(nullable = false, length = 32)
	private String content = "";

	/**
	 * 通知的类型。
	 */
	@NonNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private NoticeType type = NoticeType.SYSTEM;

	/**
	 * 阅读状态。
	 */
	@NonNull
	@Column
	private Boolean readStatus = false;

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

	public Notice(String title, String content, NoticeType type, User user) {
		this.title = title;
		this.content = content;
		this.type = type;
		this.user = user;
	}
}
