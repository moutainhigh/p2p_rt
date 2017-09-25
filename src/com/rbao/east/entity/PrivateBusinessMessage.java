package com.rbao.east.entity;

import java.util.Date;

public class PrivateBusinessMessage extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2291192222873231668L;

	private Integer id;

    private Integer userId;

    private Integer privateType;

    private Date establishTime;

    private String runSite;

    private String rentMoney;

    private String rentDate;

    private String taxId;

    private String registerId;

    private String profit;

    private String hireNumber;

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

    public Integer getPrivateType() {
        return privateType;
    }

    public void setPrivateType(Integer privateType) {
        this.privateType = privateType;
    }

    public Date getEstablishTime() {
        return establishTime;
    }

    public void setEstablishTime(Date establishTime) {
        this.establishTime = establishTime;
    }

    public String getRunSite() {
        return runSite;
    }

    public void setRunSite(String runSite) {
        this.runSite = runSite == null ? null : runSite.trim();
    }

    public String getRentMoney() {
        return rentMoney;
    }

    public void setRentMoney(String rentMoney) {
        this.rentMoney = rentMoney == null ? null : rentMoney.trim();
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate == null ? null : rentDate.trim();
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId == null ? null : taxId.trim();
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId == null ? null : registerId.trim();
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit == null ? null : profit.trim();
    }

    public String getHireNumber() {
        return hireNumber;
    }

    public void setHireNumber(String hireNumber) {
        this.hireNumber = hireNumber == null ? null : hireNumber.trim();
    }
}