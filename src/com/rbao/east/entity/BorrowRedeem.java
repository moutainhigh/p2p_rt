package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BorrowRedeem extends BaseEntity{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static final Integer STATUS_AUDITING=1;//赎回申请中
	
	public static final Integer STATUS_SUCCESS=2;//赎回申请成功
	
	public static final Integer STATUS_FAIL=3;//赎回申请失败
	
	
	public static final Map<Integer, String> ALL_REDDEM=new HashMap<Integer, String>();
	
	static {
		ALL_REDDEM.put(STATUS_AUDITING, "申请中");
		ALL_REDDEM.put(STATUS_SUCCESS, "赎回成功");
		ALL_REDDEM.put(STATUS_FAIL, "赎回失败");
	}
	

	private Integer id;

    private Integer tenderId;

    private Integer redeemStatus;

    private BigDecimal redeemMoney;

    private BigDecimal redeemFee;

    private BigDecimal redeemBackmoney;

    private String createIp;

    private Date createTime;

    private Integer auditUser;

    private Date auditTime;

    private String auditIp;
    
    private String borrowTitle;
    
    private String userAccount;
    
    private String redeemRemark;
    
    private String redeemView;
    
    private Integer userId;
    
    
    
    
    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRedeemView() {
		return redeemView;
	}

	public void setRedeemView(String redeemView) {
		this.redeemView = redeemView;
	}

	public String getRedeemRemark() {
		return redeemRemark;
	}

	public void setRedeemRemark(String redeemRemark) {
		this.redeemRemark = redeemRemark;
	}

	public String getBorrowTitle() {
		return borrowTitle;
	}

	public void setBorrowTitle(String borrowTitle) {
		this.borrowTitle = borrowTitle;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTenderId() {
        return tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }

    public Integer getRedeemStatus() {
        return redeemStatus;
    }

    public void setRedeemStatus(Integer redeemStatus) {
        this.redeemStatus = redeemStatus;
        this.setRedeemView(ALL_REDDEM.get(redeemStatus));
    }

    public BigDecimal getRedeemMoney() {
        return redeemMoney;
    }

    public void setRedeemMoney(BigDecimal redeemMoney) {
        this.redeemMoney = redeemMoney;
    }

    public BigDecimal getRedeemFee() {
        return redeemFee;
    }

    public void setRedeemFee(BigDecimal redeemFee) {
        this.redeemFee = redeemFee;
    }

    public BigDecimal getRedeemBackmoney() {
        return redeemBackmoney;
    }

    public void setRedeemBackmoney(BigDecimal redeemBackmoney) {
        this.redeemBackmoney = redeemBackmoney;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp == null ? null : createIp.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(Integer auditUser) {
        this.auditUser = auditUser;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditIp() {
        return auditIp;
    }

    public void setAuditIp(String auditIp) {
        this.auditIp = auditIp == null ? null : auditIp.trim();
    }
}