package com.rbao.east.entity;

public class Dictionary extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String dicName;

	private String dicCode;

	private Integer parentId;

	private String dicUrl;

	private String dicAttach;

	private Integer dicSequence;

	private Integer dicIshidden;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDicName() {
		return dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName == null ? null : dicName.trim();
	}

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode == null ? null : dicCode.trim();
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getDicUrl() {
		return dicUrl;
	}

	public void setDicUrl(String dicUrl) {
		this.dicUrl = dicUrl == null ? null : dicUrl.trim();
	}

	public String getDicAttach() {
		return dicAttach;
	}

	public void setDicAttach(String dicAttach) {
		this.dicAttach = dicAttach == null ? null : dicAttach.trim();
	}

	public Integer getDicSequence() {
		return dicSequence;
	}

	public void setDicSequence(Integer dicSequence) {
		this.dicSequence = dicSequence;
	}

	public Integer getDicIshidden() {
		return dicIshidden;
	}

	public void setDicIshidden(Integer dicIshidden) {
		this.dicIshidden = dicIshidden;
	}
}