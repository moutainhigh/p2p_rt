package com.rbao.east.entity;

import java.util.Date;

public class UserCredit extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8397200877512043211L;

	private Integer id;

    private Integer userId;
    
    private String userAccount;

	private Integer creditValue;
	
	private Integer creditValueUsable ;//可用积分

    private Integer creditOperator;

    private String creditAddIp;

    private Date creditAddTime;

    private String creditUpdateIp;

    private Date creditUpdateTime;

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

    public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	
    public Integer getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(Integer creditValue) {
        this.creditValue = creditValue;
    }

    public Integer getCreditOperator() {
        return creditOperator;
    }

    public void setCreditOperator(Integer creditOperator) {
        this.creditOperator = creditOperator;
    }

    public String getCreditAddIp() {
        return creditAddIp;
    }

    public void setCreditAddIp(String creditAddIp) {
        this.creditAddIp = creditAddIp == null ? null : creditAddIp.trim();
    }

    public Date getCreditAddTime() {
        return creditAddTime;
    }

    public void setCreditAddTime(Date creditAddTime) {
        this.creditAddTime = creditAddTime;
    }

    public String getCreditUpdateIp() {
        return creditUpdateIp;
    }

    public void setCreditUpdateIp(String creditUpdateIp) {
        this.creditUpdateIp = creditUpdateIp == null ? null : creditUpdateIp.trim();
    }

    public Date getCreditUpdateTime() {
        return creditUpdateTime;
    }

    public void setCreditUpdateTime(Date creditUpdateTime) {
        this.creditUpdateTime = creditUpdateTime;
    }

	public Integer getCreditValueUsable() {
		return creditValueUsable;
	}

	public void setCreditValueUsable(Integer creditValueUsable) {
		this.creditValueUsable = creditValueUsable;
	}
}