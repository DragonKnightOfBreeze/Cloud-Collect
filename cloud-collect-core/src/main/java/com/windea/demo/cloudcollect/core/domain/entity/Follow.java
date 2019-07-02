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
 * 关注信息。
 */
@Data
@NoArgsConstructor
@Entity
public class Follow implements Serializable {
	private static final long serialVersionUID = -8343689798932868256L;

	@Id
	@GeneratedValue
	private Long id;

	/**
	 * 所属用户。
	 */
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private User user;

	/**
	 * 该用户关注的用户列表。
	 */
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private List<User> followToUserList = new LinkedList<>();

	/**
	 * 关注该用户的用户列表。
	 */
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private List<User> followByUserList = new LinkedList<>();

	@CreatedDate
	@Column
	private LocalDateTime createdTime;

	@LastModifiedDate
	@Column
	private LocalDateTime lastModifiedTime;
}
