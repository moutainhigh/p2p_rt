package com.rbao.east.entity;

public class ContentChannel extends BaseEntity{
    private Integer id;

    private Integer contentId;

    private Integer channelId;

    private Integer contentSequence;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getContentSequence() {
        return contentSequence;
    }

    public void setContentSequence(Integer contentSequence) {
        this.contentSequence = contentSequence;
    }
}