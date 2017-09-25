package com.rbao.east.entity;

public class Tag extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String tagName;

	private String tagCode;

	private Integer tagSequence;

	private Integer tagIshidden;
	
	private Integer tagCount;
	
	public Integer getTagCount() {
		return tagCount;
	}

	public void setTagCount(Integer tagCount) {
		this.tagCount = tagCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName == null ? null : tagName.trim();
	}

	public String getTagCode() {
		return tagCode;
	}

	public void setTagCode(String tagCode) {
		this.tagCode = tagCode == null ? null : tagCode.trim();
	}

	public Integer getTagSequence() {
		return tagSequence;
	}

	public void setTagSequence(Integer tagSequence) {
		this.tagSequence = tagSequence;
	}

	public Integer getTagIshidden() {
		return tagIshidden;
	}

	public void setTagIshidden(Integer tagIshidden) {
		this.tagIshidden = tagIshidden;
	}
}