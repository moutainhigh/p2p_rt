package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CashStage extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6478000387839182801L;

	private Integer id;

    private BigDecimal minMoney;

    private BigDecimal maxMoney;

    private BigDecimal cashFee;

    private Integer addUser;

    private Date addTime;

    private String addIp;

    private Integer updateUser;

    private Date updateTime;

    private String updateIp;

    private String remark;
    
    private User addUsers;
    
    private User updateUsers;
    

    public User getAddUsers() {
		return addUsers;
	}

	public void setAddUsers(User addUsers) {
		this.addUsers = addUsers;
	}

	public User getUpdateUsers() {
		return updateUsers;
	}

	public void setUpdateUsers(User updateUsers) {
		this.updateUsers = updateUsers;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(BigDecimal minMoney) {
        this.minMoney = minMoney;
    }

    public BigDecimal getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(BigDecimal maxMoney) {
        this.maxMoney = maxMoney;
    }

    public BigDecimal getCashFee() {
        return cashFee;
    }

    public void setCashFee(BigDecimal cashFee) {
        this.cashFee = cashFee;
    }

    public Integer getAddUser() {
        return addUser;
    }

    public void setAddUser(Integer addUser) {
        this.addUser = addUser;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp == null ? null : updateIp.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}