package com.rbao.east.entity;

public class Nation extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String nationName;

	private String nationCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName == null ? null : nationName.trim();
	}

	public String getNationCode() {
		return nationCode;
	}

	public void setNationCode(String nationCode) {
		this.nationCode = nationCode == null ? null : nationCode.trim();
	}
}