package com.rbao.east.entity;

import java.util.Date;

public class UserBank extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 457713964694718809L;

	private Integer id;

    private Integer userId;

    private Integer bankId;

    private String bankBranch;

    private String bankCity;

    private Date bankAddtime;

    private String bankAddip;
    
    private String bankAccount;
    
    private User user;
    
    private AllBank allBank;
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AllBank getAllBank() {
		return allBank;
	}

	public void setAllBank(AllBank allBank) {
		this.allBank = allBank;
	}

/**
 * 用户名
 */
    private String userAccount;
    private String userRealname;//用户真实姓名 
    /**
     * 银行名
     * @return
     */
    private String bankName;
    
    
    
    
    public String getUserRealname() {
		return userRealname;
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}

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

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch == null ? null : bankBranch.trim();
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity == null ? null : bankCity.trim();
    }

    public Date getBankAddtime() {
        return bankAddtime;
    }

    public void setBankAddtime(Date bankAddtime) {
        this.bankAddtime = bankAddtime;
    }

    public String getBankAddip() {
        return bankAddip;
    }

    public void setBankAddip(String bankAddip) {
        this.bankAddip = bankAddip == null ? null : bankAddip.trim();
    }

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	
    
}