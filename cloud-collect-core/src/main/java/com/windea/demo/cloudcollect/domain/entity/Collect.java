package com.windea.demo.cloudcollect.domain.entity;

import com.windea.demo.cloudcollect.domain.enums.CollectMark;
import com.windea.demo.cloudcollect.domain.enums.CollectPrivacy;
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
	private Integer id;

	@NotEmpty
	@Size(min = 1, max = 64)
	@Column(nullable = false, length = 64)
	private String title;

	@Column(nullable = false, length = 512)
	private String url;

	@Nullable
	@Column(length = 512)
	private String logoUrl;

	@NotEmpty
	@Size(min = 1, max = 255)
	@Column(nullable = false, columnDefinition = "text")
	private String summary;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CollectCategory category;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn
	private Set<CollectTag> tags = new LinkedHashSet<>();

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CollectMark mark;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CollectPrivacy privacy;

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn
	private List<CollectComment> commentList = new LinkedList<>();

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private User user;

	@CreatedDate
	@Column
	private LocalDateTime createdTime;

	@LastModifiedDate
	@Column
	private LocalDateTime lastModifiedTime;

	@Column(nullable = false)
	private Boolean deleted = false;
}
