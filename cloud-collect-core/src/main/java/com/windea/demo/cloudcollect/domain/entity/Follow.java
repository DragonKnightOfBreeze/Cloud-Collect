package com.windea.demo.cloudcollect.domain.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * 关注信息。
 */
public class Follow implements Serializable {
	private static final long serialVersionUID = -8343689798932868256L;

	private Integer id;

	private User user;

	private List<User> followUserList = new LinkedList<>();

	private List<User> followedUserList = new LinkedList<>();

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getFollowUserList() {
		return followUserList;
	}

	public void setFollowUserList(List<User> followUserList) {
		this.followUserList = followUserList;
	}

	public List<User> getFollowedUserList() {
		return followedUserList;
	}

	public void setFollowedUserList(List<User> followedUserList) {
		this.followedUserList = followedUserList;
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
