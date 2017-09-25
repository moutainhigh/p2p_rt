package com.rbao.east.entity;

import java.math.BigDecimal;

public class UserAccount extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2505405563280604968L;

	private Integer id;

    private Integer userId;

    private BigDecimal allMoney;

    private BigDecimal availableMoney;

    private BigDecimal unavailableMoney;

    private BigDecimal waitRepossessedCapital;

    private BigDecimal waitRepayCapital;

    private BigDecimal waitRepossessedInterest;

    private BigDecimal waitRepayInterest;

    private BigDecimal payReward;

    private BigDecimal payInterest;

    private BigDecimal getReward;

    private BigDecimal getInterest;
    
    private User user;
    
    private BigDecimal deductionMoney;
    
    private Integer deductionStatus;
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
     * 用户名
     * @return
     */
    private String userAccount;
    /**
     * 真实姓名
     */
    private String userRealname;
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

    public BigDecimal getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(BigDecimal allMoney) {
        this.allMoney = allMoney;
    }

    public BigDecimal getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(BigDecimal availableMoney) {
        this.availableMoney = availableMoney;
    }

    public BigDecimal getUnavailableMoney() {
        return unavailableMoney;
    }

    public void setUnavailableMoney(BigDecimal unavailableMoney) {
        this.unavailableMoney = unavailableMoney;
    }

    public BigDecimal getWaitRepossessedCapital() {
        return waitRepossessedCapital;
    }

    public void setWaitRepossessedCapital(BigDecimal waitRepossessedCapital) {
        this.waitRepossessedCapital = waitRepossessedCapital;
    }

    public BigDecimal getWaitRepayCapital() {
        return waitRepayCapital;
    }

    public void setWaitRepayCapital(BigDecimal waitRepayCapital) {
        this.waitRepayCapital = waitRepayCapital;
    }

    public BigDecimal getWaitRepossessedInterest() {
        return waitRepossessedInterest;
    }

    public void setWaitRepossessedInterest(BigDecimal waitRepossessedInterest) {
        this.waitRepossessedInterest = waitRepossessedInterest;
    }

    public BigDecimal getWaitRepayInterest() {
        return waitRepayInterest;
    }

    public void setWaitRepayInterest(BigDecimal waitRepayInterest) {
        this.waitRepayInterest = waitRepayInterest;
    }

    public BigDecimal getPayReward() {
        return payReward;
    }

    public void setPayReward(BigDecimal payReward) {
        this.payReward = payReward;
    }

    public BigDecimal getPayInterest() {
        return payInterest;
    }

    public void setPayInterest(BigDecimal payInterest) {
        this.payInterest = payInterest;
    }

    public BigDecimal getGetReward() {
        return getReward;
    }

    public void setGetReward(BigDecimal getReward) {
        this.getReward = getReward;
    }

    public BigDecimal getGetInterest() {
        return getInterest;
    }

    public void setGetInterest(BigDecimal getInterest) {
        this.getInterest = getInterest;
    }

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserRealname() {
		return userRealname;
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}

	public BigDecimal getDeductionMoney() {
		return deductionMoney;
	}

	public void setDeductionMoney(BigDecimal deductionMoney) {
		this.deductionMoney = deductionMoney;
	}

	public Integer getDeductionStatus() {
		return deductionStatus;
	}

	public void setDeductionStatus(Integer deductionStatus) {
		this.deductionStatus = deductionStatus;
	}
    
}