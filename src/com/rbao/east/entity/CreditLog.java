package com.rbao.east.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreditLog extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5536477625522193007L;
	public static final Map<Integer,String> CREDITOPERATETYPE=new HashMap<Integer, String>();
	public static final Integer CREDIT_ADD=1;
	public static final Integer CREDIT_RELEASE=2;
	static{
		CREDITOPERATETYPE.put(CREDIT_ADD, "增加");
		CREDITOPERATETYPE.put(CREDIT_RELEASE, "减少");
	}

	private Integer id;

    private Integer userId;
    
	private Integer creditTypeId;

    private Integer creditOperateType;

    private Integer creditOperateValue;

    private String creditOperateRemark;

    private Date creditOperateDatetime;

    private String creditOperateIp;

    private Integer creditOperater;
    
	private User user = new User();
    
    private User operUser = new User() ;
    
    private CreditType creditType = new CreditType();

    public CreditType getCreditType() {
		return creditType;
	}

	public void setCreditType(CreditType creditType) {
		this.creditType = creditType;
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

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getOperUser() {
		return operUser;
	}

	public void setOperUser(User operUser) {
		this.operUser = operUser;
	}

    public Integer getCreditTypeId() {
        return creditTypeId;
    }

    public void setCreditTypeId(Integer creditTypeId) {
        this.creditTypeId = creditTypeId;
    }

    public Integer getCreditOperateType() {
        return creditOperateType;
    }

    public void setCreditOperateType(Integer creditOperateType) {
        this.creditOperateType = creditOperateType;
    }

    public Integer getCreditOperateValue() {
        return creditOperateValue;
    }

    public void setCreditOperateValue(Integer creditOperateValue) {
        this.creditOperateValue = creditOperateValue;
    }

    public String getCreditOperateRemark() {
        return creditOperateRemark;
    }

    public void setCreditOperateRemark(String creditOperateRemark) {
        this.creditOperateRemark = creditOperateRemark == null ? null : creditOperateRemark.trim();
    }

    public Date getCreditOperateDatetime() {
        return creditOperateDatetime;
    }

    public void setCreditOperateDatetime(Date creditOperateDatetime) {
        this.creditOperateDatetime = creditOperateDatetime;
    }

    public String getCreditOperateIp() {
        return creditOperateIp;
    }

    public void setCreditOperateIp(String creditOperateIp) {
        this.creditOperateIp = creditOperateIp == null ? null : creditOperateIp.trim();
    }

    public Integer getCreditOperater() {
        return creditOperater;
    }

    public void setCreditOperater(Integer creditOperater) {
        this.creditOperater = creditOperater;
    }
}