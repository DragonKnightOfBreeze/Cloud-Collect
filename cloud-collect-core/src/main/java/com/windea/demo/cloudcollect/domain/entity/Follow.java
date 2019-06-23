package com.windea.demo.cloudcollect.domain.entity;

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
@Entity
public class Follow implements Serializable {
	private static final long serialVersionUID = -8343689798932868256L;

	@Id
	@GeneratedValue
	private Integer id;

	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private User user;

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn
	private List<User> followUserList = new LinkedList<>();

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn
	private List<User> followedUserList = new LinkedList<>();

	@CreatedDate
	@Column
	private LocalDateTime createdTime;

	@LastModifiedDate
	@Column
	private LocalDateTime lastModifiedTime;


}
