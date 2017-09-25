package com.rbao.east.entity;

public class PersonalMessage extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5159536390730481147L;

	private Integer id;

    private Integer userId;

    private Integer marital;

    private Integer children;

    private Integer income;

    private Integer social;

    private String socialId;

    private Integer housing;

    private Integer car;

    private Integer overdue;

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

    public Integer getMarital() {
        return marital;
    }

    public void setMarital(Integer marital) {
        this.marital = marital;
    }

    public Integer getChildren() {
        return children;
    }

    public void setChildren(Integer children) {
        this.children = children;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Integer getSocial() {
        return social;
    }

    public void setSocial(Integer social) {
        this.social = social;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId == null ? null : socialId.trim();
    }

    public Integer getHousing() {
        return housing;
    }

    public void setHousing(Integer housing) {
        this.housing = housing;
    }

    public Integer getCar() {
        return car;
    }

    public void setCar(Integer car) {
        this.car = car;
    }

    public Integer getOverdue() {
        return overdue;
    }

    public void setOverdue(Integer overdue) {
        this.overdue = overdue;
    }
}