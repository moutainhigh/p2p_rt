package com.rbao.east.entity;

import java.util.Date;
import java.util.List;

public class Question extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer userId;

    private String userName;
    
    private String userImg;

    private Date questionDate;

    private String title;

    private String content;

    private Integer broswerNum;

    private Integer replyNum;

    private Integer sameNum;

    private Integer hidden;
    
    private List<Tag> tags;
    
	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Date getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(Date questionDate) {
        this.questionDate = questionDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getBroswerNum() {
        return broswerNum;
    }

    public void setBroswerNum(Integer broswerNum) {
        this.broswerNum = broswerNum;
    }

    public Integer getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(Integer replyNum) {
        this.replyNum = replyNum;
    }

    public Integer getSameNum() {
        return sameNum;
    }

    public void setSameNum(Integer sameNum) {
        this.sameNum = sameNum;
    }

    public Integer getHidden() {
        return hidden;
    }

    public void setHidden(Integer hidden) {
        this.hidden = hidden;
    }
}