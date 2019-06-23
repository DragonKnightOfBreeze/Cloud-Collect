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

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private NoticeType type;

	@Nullable
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn
	private Collect relatedCollect;

	@Nullable
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn
	private Comment relatedComment;

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private User user;

	@Column(nullable = false)
	private Boolean read = false;
}
