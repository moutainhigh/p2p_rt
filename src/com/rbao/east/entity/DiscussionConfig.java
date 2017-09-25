package com.rbao.east.entity;

public class DiscussionConfig extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String description;

	private String disKey;

	private String disValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public String getDisKey() {
		return disKey;
	}

	public void setDisKey(String disKey) {
		this.disKey = disKey == null ? null : disKey.trim();
	}

	public String getDisValue() {
		return disValue;
	}

	public void setDisValue(String disValue) {
		this.disValue = disValue == null ? null : disValue.trim();
	}
}