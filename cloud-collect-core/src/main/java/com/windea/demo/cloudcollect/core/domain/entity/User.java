package com.windea.demo.cloudcollect.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.windea.demo.cloudcollect.core.domain.enums.Role;
import com.windea.demo.cloudcollect.core.validation.annotation.Password;
import com.windea.demo.cloudcollect.core.validation.annotation.Username;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * 用户。
 */
@Data
@NoArgsConstructor
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 5006865220326542020L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 用户名。
	 */
	@NotEmpty(message = "validation.User.username.NotEmpty")
	@Username(message = "validation.User.username.ValidUsername")
	@Column(unique = true, nullable = false, length = 16)
	private String username;

	/**
	 * 邮箱。
	 */
	@NotEmpty(message = "validation.User.email.NotEmpty")
	@Email(message = "validation.User.email.Email")
	@Column(unique = true, nullable = false, length = 64)
	private String email;

	/**
	 * 密码。
	 * 这里存储的是加密后的密码，可以进行参数验证，不能限制长度。
	 */
	@NotEmpty(message = "validation.User.password.NotEmpty")
	@Password(message = "validation.User.password.ValidPassword")
	@Column(nullable = false)
	private String password;

	/**
	 * 昵称。
	 */
	@NotEmpty(message = "validation.User.nickname.NotEmpty")
	@Size(min = 1, max = 64, message = "validation.User.nickname.Size")
	@Column(nullable = false, length = 64)
	private String nickname;

	/**
	 * 头像地址。
	 */
	@Nullable
	@Column(length = 512)
	private String avatarUrl;

	/**
	 * 背景地址。
	 */
	@Nullable
	@Column(length = 512)
	private String backgroundUrl;

	/**
	 * 简介。
	 */
	@NotEmpty(message = "validation.User.introduce.NotEmpty")
	@Size(min = 1, max = 255, message = "validation.User.introduce.Size")
	@Column(nullable = false, columnDefinition = "text")
	private String introduce;

	/**
	 * 身份。
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role = Role.NORMAL;

	/**
	 * 是否已经激活。
	 */
	@Column(nullable = false)
	private Boolean activated = false;

	/**
	 * 注册时间。
	 */
	@CreatedDate
	@Column
	private LocalDateTime registerTime;

	/**
	 * 资料更新时间。
	 */
	@LastModifiedDate
	@Column
	private LocalDateTime updateTime;

	/**
	 * 该用户关注的用户列表。
	 */
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "followByUserList")
	private List<User> followToUserList;

	/**
	 * 关注该用户的用户列表。
	 */
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "followToUserList")
	private List<User> followByUserList;

	/**
	 * 该用户点赞的收藏列表。
	 */
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.MERGE, mappedBy = "praiseByUserList")
	private List<Collect> praiseToCollectList = new LinkedList<>();

	/**
	 * 收藏列表。
	 */
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Collect> collectList = new LinkedList<>();

	/**
	 * 通知列表。
	 */
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Notice> noticeList = new LinkedList<>();


	/**
	 * 该用户关注的用户数量。
	 */
	@Transient
	public Integer getFollowToUserCount() {
		return followToUserList.size();
	}

	/**
	 * 关注该用户的用户数量。
	 */
	@Transient
	public Integer getFollowByUserCount() {
		return followByUserList.size();
	}

	/**
	 * 该用户点赞的收藏数量。
	 */
	@Transient
	public Integer getPraiseToCollectCount() {
		return praiseToCollectList.size();
	}

	/**
	 * 收藏数量。
	 */
	@Transient
	public Integer getCollectCount() {
		return collectList.size();
	}

	/**
	 * 评论数量。
	 */
	@Transient
	public Integer getNoticeCount() {
		return noticeList.size();
	}
}
