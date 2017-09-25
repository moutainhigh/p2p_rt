package com.rbao.east.entity;

import java.util.Date;

public class NoticeType extends BaseEntity{
	
	private static final long serialVersionUID = -8901945612123332268L;
	
    private Integer id;

    private Short noticeSequence;

    private String noticeName;

    private String noticeCode;

    private Date noticeAddtime;

    private String noticeAddip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getNoticeSequence() {
        return noticeSequence;
    }

    public void setNoticeSequence(Short noticeSequence) {
        this.noticeSequence = noticeSequence;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName == null ? null : noticeName.trim();
    }

    public String getNoticeCode() {
        return noticeCode;
    }

    public void setNoticeCode(String noticeCode) {
        this.noticeCode = noticeCode == null ? null : noticeCode.trim();
    }

    public Date getNoticeAddtime() {
        return noticeAddtime;
    }

    public void setNoticeAddtime(Date noticeAddtime) {
        this.noticeAddtime = noticeAddtime;
    }

    public String getNoticeAddip() {
        return noticeAddip;
    }

    public void setNoticeAddip(String noticeAddip) {
        this.noticeAddip = noticeAddip == null ? null : noticeAddip.trim();
    }
}