package com.rbao.east.entity;

import java.util.Date;

public class UnitMessage extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4711686078705986567L;

	private Integer id;

    private Integer userId;

    private String companyName;

    private Integer companyIndustry;

    private Integer work;

    private String workGrade;

    private Integer zhiwei;

    private Date serveTime;

    private Date toServeTime;

    private Integer workYear;

    private String workTel;

    private String companyAdd;

    private String companyWebsite;

    private String remark;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public Integer getCompanyIndustry() {
        return companyIndustry;
    }

    public void setCompanyIndustry(Integer companyIndustry) {
        this.companyIndustry = companyIndustry;
    }

    public Integer getWork() {
        return work;
    }

    public void setWork(Integer work) {
        this.work = work;
    }

    public String getWorkGrade() {
        return workGrade;
    }

    public void setWorkGrade(String workGrade) {
        this.workGrade = workGrade == null ? null : workGrade.trim();
    }

    public Integer getZhiwei() {
        return zhiwei;
    }

    public void setZhiwei(Integer zhiwei) {
        this.zhiwei = zhiwei;
    }

    public Date getServeTime() {
        return serveTime;
    }

    public void setServeTime(Date serveTime) {
        this.serveTime = serveTime;
    }

    public Date getToServeTime() {
        return toServeTime;
    }

    public void setToServeTime(Date toServeTime) {
        this.toServeTime = toServeTime;
    }

    public Integer getWorkYear() {
        return workYear;
    }

    public void setWorkYear(Integer workYear) {
        this.workYear = workYear;
    }

    public String getWorkTel() {
        return workTel;
    }

    public void setWorkTel(String workTel) {
        this.workTel = workTel == null ? null : workTel.trim();
    }

    public String getCompanyAdd() {
        return companyAdd;
    }

    public void setCompanyAdd(String companyAdd) {
        this.companyAdd = companyAdd == null ? null : companyAdd.trim();
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite == null ? null : companyWebsite.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}