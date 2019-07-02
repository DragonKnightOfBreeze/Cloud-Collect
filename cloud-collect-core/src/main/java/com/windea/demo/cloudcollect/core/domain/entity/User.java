package com.windea.demo.cloudcollect.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.windea.demo.cloudcollect.core.domain.enums.Role;
import com.windea.demo.cloudcollect.core.validation.annotation.ValidPassword;
import com.windea.demo.cloudcollect.core.validation.annotation.ValidUsername;
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
	@ValidUsername(message = "validation.User.username.ValidUsername")
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
	@ValidPassword(message = "validation.User.password.ValidPassword")
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
	 * 关注信息。
	 */
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
	private Follow follow = new Follow();

	/**
	 * 收藏列表。
	 */
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
	private List<Collect> collectList = new LinkedList<>();

	/**
	 * 通知列表。
	 */
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
	private List<Collect> noticeList = new LinkedList<>();

	/**
	 * 是否已经激活。
	 */
	@Column(nullable = false)
	private Boolean activated = false;

	@CreatedDate
	@Column
	private LocalDateTime registerTime;

	@LastModifiedDate
	@Column
	private LocalDateTime updateTime;
}
