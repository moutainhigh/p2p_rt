package com.rbao.east.entity;


public class Area extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String areaName;

	private String areaCode;

	private Integer parentId;

	private String areaDomain;

	private Integer areaSequence;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName == null ? null : areaName.trim();
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode == null ? null : areaCode.trim();
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getAreaDomain() {
		return areaDomain;
	}

	public void setAreaDomain(String areaDomain) {
		this.areaDomain = areaDomain == null ? null : areaDomain.trim();
	}

	public Integer getAreaSequence() {
		return areaSequence;
	}

	public void setAreaSequence(Integer areaSequence) {
		this.areaSequence = areaSequence;
	}
}