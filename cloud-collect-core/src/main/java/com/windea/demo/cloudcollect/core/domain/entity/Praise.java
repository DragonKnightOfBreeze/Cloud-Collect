package com.windea.demo.cloudcollect.core.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * 点赞信息。
 */
@Data
@NoArgsConstructor
@Entity
public class Praise implements Serializable {
	private static final long serialVersionUID = -3754697767735093276L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 所属收藏。
	 */
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Collect collect;

	/**
	 * 点赞该收藏的用户列表。
	 */
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private List<User> praiseByUserList = new LinkedList<>();

	@CreatedDate
	@Column
	private LocalDateTime createdTime;

	@LastModifiedDate
	@Column
	private LocalDateTime lastModifiedTime;
}
