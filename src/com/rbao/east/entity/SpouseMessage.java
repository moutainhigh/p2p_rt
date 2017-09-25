package com.rbao.east.entity;

public class SpouseMessage extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6342835895709712139L;

	private Integer id;

    private Integer userId;

    private String spouseName;

    private String monthlyPay;

    private String phone;

    private String unitPhone;

    private Integer unitType;

    private Integer postType;

    private String unitAddress;

    private String unitIncome;

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

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName == null ? null : spouseName.trim();
    }

    public String getMonthlyPay() {
        return monthlyPay;
    }

    public void setMonthlyPay(String monthlyPay) {
        this.monthlyPay = monthlyPay == null ? null : monthlyPay.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getUnitPhone() {
        return unitPhone;
    }

    public void setUnitPhone(String unitPhone) {
        this.unitPhone = unitPhone == null ? null : unitPhone.trim();
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public String getUnitAddress() {
        return unitAddress;
    }

    public void setUnitAddress(String unitAddress) {
        this.unitAddress = unitAddress == null ? null : unitAddress.trim();
    }

    public String getUnitIncome() {
        return unitIncome;
    }

    public void setUnitIncome(String unitIncome) {
        this.unitIncome = unitIncome == null ? null : unitIncome.trim();
    }
}