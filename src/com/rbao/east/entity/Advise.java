package com.rbao.east.entity;

import java.util.Date;
/**
 * 建议实体
 * */
public class Advise {
    private Integer id;

    private String adviseConnectInfo;//意见信息

    private String adviseContent;//意见内容

    private String adviseFeedback;//意见反馈

    private String adviseAddip;//意见添加ip

    private Date adviseAddtime;//添加时间

    private Integer adviseStatus;//意见状态

    private Integer adviseDealUserid;//意见处理人

    private Integer adviseFeedbackWay;//意见回访方式

    private Date adviseFeedbackDatetime;//回访时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdviseConnectInfo() {
        return adviseConnectInfo;
    }

    public void setAdviseConnectInfo(String adviseConnectInfo) {
        this.adviseConnectInfo = adviseConnectInfo == null ? null : adviseConnectInfo.trim();
    }

    public String getAdviseContent() {
        return adviseContent;
    }

    public void setAdviseContent(String adviseContent) {
        this.adviseContent = adviseContent == null ? null : adviseContent.trim();
    }

    public String getAdviseFeedback() {
        return adviseFeedback;
    }

    public void setAdviseFeedback(String adviseFeedback) {
        this.adviseFeedback = adviseFeedback == null ? null : adviseFeedback.trim();
    }

    public String getAdviseAddip() {
        return adviseAddip;
    }

    public void setAdviseAddip(String adviseAddip) {
        this.adviseAddip = adviseAddip == null ? null : adviseAddip.trim();
    }

    public Date getAdviseAddtime() {
        return adviseAddtime;
    }

    public void setAdviseAddtime(Date adviseAddtime) {
        this.adviseAddtime = adviseAddtime;
    }

    public Integer getAdviseStatus() {
        return adviseStatus;
    }

    public void setAdviseStatus(Integer adviseStatus) {
        this.adviseStatus = adviseStatus;
    }

    public Integer getAdviseDealUserid() {
        return adviseDealUserid;
    }

    public void setAdviseDealUserid(Integer adviseDealUserid) {
        this.adviseDealUserid = adviseDealUserid;
    }

    public Integer getAdviseFeedbackWay() {
        return adviseFeedbackWay;
    }

    public void setAdviseFeedbackWay(Integer adviseFeedbackWay) {
        this.adviseFeedbackWay = adviseFeedbackWay;
    }

    public Date getAdviseFeedbackDatetime() {
        return adviseFeedbackDatetime;
    }

    public void setAdviseFeedbackDatetime(Date adviseFeedbackDatetime) {
        this.adviseFeedbackDatetime = adviseFeedbackDatetime;
    }
}