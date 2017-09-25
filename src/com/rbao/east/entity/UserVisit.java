package com.rbao.east.entity;

import java.util.Date;

public class UserVisit {
    private Integer id;

    private Integer userId;

    private Integer visitUserid;

    private String visitAddip;

    private Date visitAddtime;

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

    public Integer getVisitUserid() {
        return visitUserid;
    }

    public void setVisitUserid(Integer visitUserid) {
        this.visitUserid = visitUserid;
    }

    public String getVisitAddip() {
        return visitAddip;
    }

    public void setVisitAddip(String visitAddip) {
        this.visitAddip = visitAddip == null ? null : visitAddip.trim();
    }

    public Date getVisitAddtime() {
        return visitAddtime;
    }

    public void setVisitAddtime(Date visitAddtime) {
        this.visitAddtime = visitAddtime;
    }
}