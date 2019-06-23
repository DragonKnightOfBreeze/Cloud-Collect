package com.windea.demo.cloudcollect.domain.entity;

import com.windea.demo.cloudcollect.domain.enums.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户。
 */
@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 5006865220326542020L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true, nullable = false, length = 16)
	private String username;

	@Column(unique = true, nullable = false, length = 64)
	private String email;

	//这里存储的是加密后的密码，可以进行参数验证，不能限制长度
	@Column(nullable = false)
	private String password;

	@Column(nullable = false, length = 64)
	private String nickname;

	@Nullable
	@Column(length = 512)
	private String avatarUrl;

	@Nullable
	@Column(length = 512)
	private String backgroundUrl;

	@Column(nullable = false, length = 65535, columnDefinition = "text")
	private String introduce;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@CreatedDate
	@Column
	private LocalDateTime registerTime;

	@LastModifiedDate
	@Column
	private LocalDateTime updateTime;

	@Column(nullable = false)
	private Boolean activated = false;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Nullable
	public String getAvatarUrl() {
		return avatarUrl;
	}

	@Nullable
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	public void setBackgroundUrl(String backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDateTime getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(LocalDateTime registerTime) {
		this.registerTime = registerTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getActivated() {
		return activated;
	}

	public void setActivated(Boolean activated) {
		this.activated = activated;
	}
}
