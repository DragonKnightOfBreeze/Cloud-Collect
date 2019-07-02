package com.windea.demo.cloudcollect.core.domain.entity;

import com.windea.demo.cloudcollect.core.domain.enums.NoticeType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;

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

	@Column(nullable = false)
	private Boolean read = false;
}
