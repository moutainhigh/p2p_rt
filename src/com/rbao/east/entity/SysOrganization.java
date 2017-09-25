package com.rbao.east.entity;

import java.util.Date;

public class SysOrganization {
    private Integer id;

    private String orgName;

    private String orgCode;

    private Integer orgSequence;

    private Integer orgParentId;

    private String orgDescripe;

    private Date orgAddtime;

    private String orgAddip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public Integer getOrgSequence() {
        return orgSequence;
    }

    public void setOrgSequence(Integer orgSequence) {
        this.orgSequence = orgSequence;
    }

    public Integer getOrgParentId() {
        return orgParentId;
    }

    public void setOrgParentId(Integer orgParentId) {
        this.orgParentId = orgParentId;
    }

    public String getOrgDescripe() {
        return orgDescripe;
    }

    public void setOrgDescripe(String orgDescripe) {
        this.orgDescripe = orgDescripe == null ? null : orgDescripe.trim();
    }

    public Date getOrgAddtime() {
        return orgAddtime;
    }

    public void setOrgAddtime(Date orgAddtime) {
        this.orgAddtime = orgAddtime;
    }

    public String getOrgAddip() {
        return orgAddip;
    }

    public void setOrgAddip(String orgAddip) {
        this.orgAddip = orgAddip == null ? null : orgAddip.trim();
    }
}