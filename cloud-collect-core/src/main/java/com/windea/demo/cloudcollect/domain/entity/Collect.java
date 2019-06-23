package com.windea.demo.cloudcollect.domain.entity;

import com.windea.demo.cloudcollect.domain.enums.CollectMark;
import com.windea.demo.cloudcollect.domain.enums.CollectPrivacy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 收藏。
 */
@Entity
public class Collect implements Serializable {
	private static final long serialVersionUID = -6764369348818887548L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 64)
	private String title;

	@Column(nullable = false, length = 512)
	private String url;

	@Nullable
	@Column(length = 512)
	private String logoUrl;

	@Column(nullable = false, length = 65535, columnDefinition = "text")
	private String summary;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CollectCategory category;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn
	private Set<CollectTag> tags = new LinkedHashSet<>();

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CollectMark mark;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CollectPrivacy privacy;

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn
	private List<CollectComment> commentList = new LinkedList<>();

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private User user;

	@CreatedDate
	@Column
	private LocalDateTime createdTime;

	@LastModifiedDate
	@Column
	private LocalDateTime lastModifiedTime;

	@Column(nullable = false)
	private Boolean deleted = false;


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

	@Nullable
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
