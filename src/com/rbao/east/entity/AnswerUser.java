package com.rbao.east.entity;

public class AnswerUser extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer answerId;

	private Integer userId;

	private Integer hasvote;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getHasvote() {
		return hasvote;
	}

	public void setHasvote(Integer hasvote) {
		this.hasvote = hasvote;
	}
}