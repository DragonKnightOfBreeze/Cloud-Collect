package com.windea.demo.cloudcollect.domain.entity;

import com.windea.demo.cloudcollect.domain.enums.Role;
import com.windea.demo.cloudcollect.validation.annotation.ValidPassword;
import com.windea.demo.cloudcollect.validation.annotation.ValidUsername;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
	private Integer id;

	@NotEmpty(message = "validation.User.username.NotEmpty")
	@ValidUsername(message = "validation.User.username.ValidUsername")
	@Column(unique = true, nullable = false, length = 16)
	private String username;

	@NotEmpty(message = "validation.User.email.NotEmpty")
	@Email(message = "validation.User.email.Email")
	@Column(unique = true, nullable = false, length = 64)
	private String email;

	//这里存储的是加密后的密码，可以进行参数验证，不能限制长度
	@NotEmpty(message = "validation.User.password.NotEmpty")
	@ValidPassword(message = "validation.User.password.ValidPassword")
	@Column(nullable = false)
	private String password;

	@NotEmpty(message = "validation.User.nickname.NotEmpty")
	@Size(min = 1, max = 64, message = "validation.User.nickname.Size")
	@Column(nullable = false, length = 64)
	private String nickname;

	@Nullable
	@Column(length = 512)
	private String avatarUrl;

	@Nullable
	@Column(length = 512)
	private String backgroundUrl;

	@NotEmpty(message = "validation.User.introduce.NotEmpty")
	@Size(min = 1, max = 255, message = "validation.User.introduce.Size")
	@Column(nullable = false, columnDefinition = "text")
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
}
