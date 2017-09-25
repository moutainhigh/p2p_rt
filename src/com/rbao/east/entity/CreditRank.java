package com.rbao.east.entity;

import java.io.Serializable;
import java.util.Date;

public class CreditRank extends BaseEntity implements Serializable{
    private Integer id;

    private String rankName;

    private Integer rankValue;

    private Integer rankMincredit;

    private Integer rankMaxcredit;

    private String rankIcon;

    private String rankRemarks;

    private Date rankAddtime;

    private String rankAddip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName == null ? null : rankName.trim();
    }

    public Integer getRankValue() {
        return rankValue;
    }

    public void setRankValue(Integer rankValue) {
        this.rankValue = rankValue;
    }

    public Integer getRankMincredit() {
        return rankMincredit;
    }

    public void setRankMincredit(Integer rankMincredit) {
        this.rankMincredit = rankMincredit;
    }

    public Integer getRankMaxcredit() {
        return rankMaxcredit;
    }

    public void setRankMaxcredit(Integer rankMaxcredit) {
        this.rankMaxcredit = rankMaxcredit;
    }

    public String getRankIcon() {
        return rankIcon;
    }

    public void setRankIcon(String rankIcon) {
        this.rankIcon = rankIcon == null ? null : rankIcon.trim();
    }

    public String getRankRemarks() {
        return rankRemarks;
    }

    public void setRankRemarks(String rankRemarks) {
        this.rankRemarks = rankRemarks == null ? null : rankRemarks.trim();
    }

    public Date getRankAddtime() {
        return rankAddtime;
    }

    public void setRankAddtime(Date rankAddtime) {
        this.rankAddtime = rankAddtime;
    }

    public String getRankAddip() {
        return rankAddip;
    }

    public void setRankAddip(String rankAddip) {
        this.rankAddip = rankAddip == null ? null : rankAddip.trim();
    }
}