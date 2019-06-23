package com.windea.demo.cloudcollect.domain.entity;

import com.windea.demo.cloudcollect.domain.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
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
}
