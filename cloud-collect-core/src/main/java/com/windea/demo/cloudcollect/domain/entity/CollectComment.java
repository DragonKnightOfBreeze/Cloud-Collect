package com.windea.demo.cloudcollect.domain.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 收藏的评论。
 * <p>
 * 一个收藏可以带有多条评论。
 */
public class CollectComment implements Serializable {
	private static final long serialVersionUID = -5681480852773287799L;

	private Integer id;

	private User sponsorUser;

	@Nullable
	private User replyUser;

	private String content;

	private User user;

	@CreatedDate
	private LocalDateTime createdTime;

	@LastModifiedDate
	private LocalDateTime lastModifiedTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getSponsorUser() {
		return sponsorUser;
	}

	public void setSponsorUser(User sponsorUser) {
		this.sponsorUser = sponsorUser;
	}

	@Nullable
	public User getReplyUser() {
		return replyUser;
	}

	public void setReplyUser(@Nullable User replyUser) {
		this.replyUser = replyUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public LocalDateTime getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
}
