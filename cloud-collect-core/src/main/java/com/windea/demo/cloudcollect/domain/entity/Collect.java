package com.windea.demo.cloudcollect.domain.entity;

import com.windea.demo.cloudcollect.domain.enums.CollectMark;
import com.windea.demo.cloudcollect.domain.enums.CollectPrivacy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 收藏。
 */
public class Collect implements Serializable {
	private static final long serialVersionUID = -6764369348818887548L;

	private Integer id;

	private String title;

	private String url;

	private String logoUrl;

	private String summary;

	private CollectCategory category;

	private Set<CollectTag> tags = new LinkedHashSet<>();

	private CollectMark mark;

	private CollectPrivacy privacy;

	private List<CollectComment> commentList = new LinkedList<>();

	private User user;

	@CreatedDate
	private LocalDateTime createdTime;

	@LastModifiedDate
	private LocalDateTime lastModifiedTime;

	private Boolean deleted;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public CollectCategory getCategory() {
		return category;
	}

	public void setCategory(CollectCategory category) {
		this.category = category;
	}

	public Set<CollectTag> getTags() {
		return tags;
	}

	public void setTags(Set<CollectTag> tags) {
		this.tags = tags;
	}

	public CollectMark getMark() {
		return mark;
	}

	public void setMark(CollectMark mark) {
		this.mark = mark;
	}

	public CollectPrivacy getPrivacy() {
		return privacy;
	}

	public void setPrivacy(CollectPrivacy privacy) {
		this.privacy = privacy;
	}

	public List<CollectComment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<CollectComment> commentList) {
		this.commentList = commentList;
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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
}
