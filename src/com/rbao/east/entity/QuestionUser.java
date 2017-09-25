package com.rbao.east.entity;

public class QuestionUser extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer questionId;

    private Integer userId;

    private Integer hasread;

    private Integer hassame;

    private Integer hasreply;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHasread() {
        return hasread;
    }

    public void setHasread(Integer hasread) {
        this.hasread = hasread;
    }

    public Integer getHassame() {
        return hassame;
    }

    public void setHassame(Integer hassame) {
        this.hassame = hassame;
    }

    public Integer getHasreply() {
        return hasreply;
    }

    public void setHasreply(Integer hasreply) {
        this.hasreply = hasreply;
    }
}