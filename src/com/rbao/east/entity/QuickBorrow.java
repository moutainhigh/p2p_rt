package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QuickBorrow extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7046277730080436748L;
	
	public static final Integer STATUS_NEW = 1; //待审
	public static final Integer STATUS_FIRST_AUDIT_YES = 2; //初审通过
	public static final Integer STATUS_FIRST_AUDIT_NO = 3; //初审未通过
	public static final Integer STATUS_SECOND_AUDIT_YES = 4; //复审通过
	public static final Integer STATUS_SECOND_AUDIT_NO = 5; //

	public static final Map<Integer,String> ALL_STATUS = new HashMap<Integer,String>();
	static {
		ALL_STATUS.put(STATUS_NEW, "待审");
		ALL_STATUS.put(STATUS_FIRST_AUDIT_YES, "初审通过");
		ALL_STATUS.put(STATUS_FIRST_AUDIT_NO, "初审不通过");
		ALL_STATUS.put(STATUS_SECOND_AUDIT_YES, "复审通过");
		ALL_STATUS.put(STATUS_SECOND_AUDIT_NO, "复审不通过");
	}

	private Integer id;

    private Integer userId;

    private String userName;

    private String userTel;

    private BigDecimal borrowAmount;

    private String borrowPeriod;

    private String borrowUse;

    private BigDecimal interestrateMin;

    private BigDecimal interestrateMax;

    private String userProvince;

    private String userCity;

    private String userArea;

    private Integer status;

    private Date createTime;

    private String createIp;

    private Integer fistAuditUserid;

    private Date fistAuditTime;

    private String firstAuditIp;

    private String firstAuditRemark;

    private Integer secondAuditUserid;

    private Date secondAuditTime;

    private String secondAuditIp;

    private String secondAuditRemark;
    //新增字段
    private String borrowWay;//借款方式
    private String mainUse;//企业主营业务
    private BigDecimal interestMonth;//企业每月盈利
    
    
    
    private String userRealname;
    
    
    public String getUserRealname() {
		return userRealname;
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
	}

	public String getMainUse() {
		return mainUse;
	}

	public void setMainUse(String mainUse) {
		this.mainUse = mainUse;
	}

	public BigDecimal getInterestMonth() {
		return interestMonth;
	}

	public void setInterestMonth(BigDecimal interestMonth) {
		this.interestMonth = interestMonth;
	}

	public String getBorrowWay() {
		return borrowWay;
	}

	public void setBorrowWay(String borrowWay) {
		this.borrowWay = borrowWay;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel == null ? null : userTel.trim();
    }

    public BigDecimal getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(BigDecimal borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod == null ? null : borrowPeriod.trim();
    }

    public String getBorrowUse() {
        return borrowUse;
    }

    public void setBorrowUse(String borrowUse) {
        this.borrowUse = borrowUse == null ? null : borrowUse.trim();
    }

    public BigDecimal getInterestrateMin() {
        return interestrateMin;
    }

    public void setInterestrateMin(BigDecimal interestrateMin) {
        this.interestrateMin = interestrateMin;
    }

    public BigDecimal getInterestrateMax() {
        return interestrateMax;
    }

    public void setInterestrateMax(BigDecimal interestrateMax) {
        this.interestrateMax = interestrateMax;
    }

    public String getUserProvince() {
        return userProvince;
    }

    public void setUserProvince(String userProvince) {
        this.userProvince = userProvince == null ? null : userProvince.trim();
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity == null ? null : userCity.trim();
    }

    public String getUserArea() {
        return userArea;
    }

    public void setUserArea(String userArea) {
        this.userArea = userArea == null ? null : userArea.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp == null ? null : createIp.trim();
    }

    public Integer getFistAuditUserid() {
        return fistAuditUserid;
    }

    public void setFistAuditUserid(Integer fistAuditUserid) {
        this.fistAuditUserid = fistAuditUserid;
    }

    public Date getFistAuditTime() {
        return fistAuditTime;
    }

    public void setFistAuditTime(Date fistAuditTime) {
        this.fistAuditTime = fistAuditTime;
    }

    public String getFirstAuditIp() {
        return firstAuditIp;
    }

    public void setFirstAuditIp(String firstAuditIp) {
        this.firstAuditIp = firstAuditIp == null ? null : firstAuditIp.trim();
    }

    public String getFirstAuditRemark() {
        return firstAuditRemark;
    }

    public void setFirstAuditRemark(String firstAuditRemark) {
        this.firstAuditRemark = firstAuditRemark == null ? null : firstAuditRemark.trim();
    }

    public Integer getSecondAuditUserid() {
        return secondAuditUserid;
    }

    public void setSecondAuditUserid(Integer secondAuditUserid) {
        this.secondAuditUserid = secondAuditUserid;
    }

    public Date getSecondAuditTime() {
        return secondAuditTime;
    }

    public void setSecondAuditTime(Date secondAuditTime) {
        this.secondAuditTime = secondAuditTime;
    }

    public String getSecondAuditIp() {
        return secondAuditIp;
    }

    public void setSecondAuditIp(String secondAuditIp) {
        this.secondAuditIp = secondAuditIp == null ? null : secondAuditIp.trim();
    }

    public String getSecondAuditRemark() {
        return secondAuditRemark;
    }

    public void setSecondAuditRemark(String secondAuditRemark) {
        this.secondAuditRemark = secondAuditRemark == null ? null : secondAuditRemark.trim();
    }
}