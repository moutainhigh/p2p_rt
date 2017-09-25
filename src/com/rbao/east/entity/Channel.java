package com.rbao.east.entity;

import java.util.Date;
import java.util.List;


public class Channel extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7142721288364713307L;

	private Integer id;

    private Integer channelParentId;

    private String channelTitle;

    private String channelCode;

    private Integer channelSequence;

    private Integer channelDisplay;

    private Integer channelIsUrl;

    private String channelUrl;

    private String channelDescribe;

    private Date channelCreateTime;

    private Date channelUpdateTime;

    private String channelContent;
    
    private List<Channel> children;
    
    private Channel parentChannel ;
    
    private String keyword;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChannelParentId() {
        return channelParentId;
    }

    public void setChannelParentId(Integer channelParentId) {
        this.channelParentId = channelParentId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle == null ? null : channelTitle.trim();
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public Integer getChannelSequence() {
        return channelSequence;
    }

    public void setChannelSequence(Integer channelSequence) {
        this.channelSequence = channelSequence;
    }

    public Integer getChannelDisplay() {
        return channelDisplay;
    }

    public void setChannelDisplay(Integer channelDisplay) {
        this.channelDisplay = channelDisplay;
    }

    public Integer getChannelIsUrl() {
        return channelIsUrl;
    }

    public void setChannelIsUrl(Integer channelIsUrl) {
        this.channelIsUrl = channelIsUrl;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl == null ? null : channelUrl.trim();
    }

    public String getChannelDescribe() {
        return channelDescribe;
    }

    public void setChannelDescribe(String channelDescribe) {
        this.channelDescribe = channelDescribe == null ? null : channelDescribe.trim();
    }

    public Date getChannelCreateTime() {
        return channelCreateTime;
    }

    public void setChannelCreateTime(Date channelCreateTime) {
        this.channelCreateTime = channelCreateTime;
    }

    public Date getChannelUpdateTime() {
        return channelUpdateTime;
    }

    public void setChannelUpdateTime(Date channelUpdateTime) {
       
        this.channelUpdateTime = channelUpdateTime;
    }

    public String getChannelContent() {
        return channelContent;
    }

    public void setChannelContent(String channelContent) {
        this.channelContent = channelContent == null ? null : channelContent.trim();
    }

	public List<Channel> getChildren() {
		return children;
	}

	public void setChildren(List<Channel> children) {
		this.children = children;
	}

	public Channel getParentChannel() {
		return parentChannel;
	}

	public void setParentChannel(Channel parentChannel) {
		this.parentChannel = parentChannel;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}