package com.rbao.east.entity;

import java.util.Date;

public class EducationMessage extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -952190806577231489L;

	private Integer id;

    private Integer userId;

    private Integer maxEducation;

    private String college;

    private String major;

    private Date dateFrom;

    private Date dateTo;

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

    public Integer getMaxEducation() {
        return maxEducation;
    }

    public void setMaxEducation(Integer maxEducation) {
        this.maxEducation = maxEducation;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college == null ? null : college.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}