package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 充值实体
 * */
public class AccountRecharge extends BaseEntity{
	
	private static final long serialVersionUID = -2157735432188426931L;
	
	//充值记录状态
	public static final Integer RECHAREGE_STATUS_WAIT_CHAECK = 1;//待审核
	public static final Integer RECHAREGE_STATUS_CHECK_SUCC = 2;//通过
	public static final Integer RECHAREGE_STATUS_CHECK_FAIL = 3;//未通过
	
	//充值记录类型
	public static final String RECHARGE_TYPE_ON="1";//线上充值
	public static final String RECHARGE_TYPE_OFF="2";//线下充值
	public static final String RECHARGE_TYPE_back="3";//后台充值
	
	public static final Map<Integer , String> ALL_RECHAREGE_STATUS = new HashMap<Integer, String>();
	public static final Map<String , String> ALL_RECHAREGE_TYPE = new HashMap<String, String>();
	
	static {
		ALL_RECHAREGE_STATUS.put(RECHAREGE_STATUS_WAIT_CHAECK, "待审核");
		ALL_RECHAREGE_STATUS.put(RECHAREGE_STATUS_CHECK_SUCC, "审核通过");
		ALL_RECHAREGE_STATUS.put(RECHAREGE_STATUS_CHECK_FAIL, "审核未通过");
		
		ALL_RECHAREGE_TYPE.put(RECHARGE_TYPE_ON, "线上充值");
		ALL_RECHAREGE_TYPE.put(RECHARGE_TYPE_OFF, "线下充值");
		ALL_RECHAREGE_TYPE.put(RECHARGE_TYPE_back, "后台充值");
	}
	
    private Integer id;

    private String rechargeTradeNo;//订单号

    private Integer userId;

    private Integer rechargeStatus;//状态

    private BigDecimal rechargeMoney;//金额

    private String rechargePayment;

    private String rechargeType;//充值类型

    private String rechargeRemark;

    private BigDecimal rechargeFee;//充值费用

    private Integer verifyUserid;

    private Date verifyTime;

    private String verifyRemark;

    private Date rechargeAddtime;

    private String rechargeAddip;

    private String rechargeReturn;
    
    private User user;//用户
    
    private User verifyUser;//审核人
    
    private String viewRechargeStatus;
    
    private String viewRechargeType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRechargeTradeNo() {
        return rechargeTradeNo;
    }

    public void setRechargeTradeNo(String rechargeTradeNo) {
        this.rechargeTradeNo = rechargeTradeNo == null ? null : rechargeTradeNo.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRechargeStatus() {
        return rechargeStatus;
    }

    public void setRechargeStatus(Integer rechargeStatus) {
        this.rechargeStatus = rechargeStatus;
        setViewRechargeStatus(ALL_RECHAREGE_STATUS.get(rechargeStatus));
    }

    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(BigDecimal rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public String getRechargePayment() {
        return rechargePayment;
    }

    public void setRechargePayment(String rechargePayment) {
        this.rechargePayment = rechargePayment == null ? null : rechargePayment.trim();
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType == null ? null : rechargeType.trim();
    }

    public String getRechargeRemark() {
        return rechargeRemark;
    }

    public void setRechargeRemark(String rechargeRemark) {
        this.rechargeRemark = rechargeRemark == null ? null : rechargeRemark.trim();
    }

    public BigDecimal getRechargeFee() {
        return rechargeFee;
    }

    public void setRechargeFee(BigDecimal rechargeFee) {
        this.rechargeFee = rechargeFee;
    }

    public Integer getVerifyUserid() {
        return verifyUserid;
    }

    public void setVerifyUserid(Integer verifyUserid) {
        this.verifyUserid = verifyUserid;
    }

    public Date getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getVerifyRemark() {
        return verifyRemark;
    }

    public void setVerifyRemark(String verifyRemark) {
        this.verifyRemark = verifyRemark == null ? null : verifyRemark.trim();
    }

    public Date getRechargeAddtime() {
        return rechargeAddtime;
    }

    public void setRechargeAddtime(Date rechargeAddtime) {
        this.rechargeAddtime = rechargeAddtime;
    }

    public String getRechargeAddip() {
        return rechargeAddip;
    }

    public void setRechargeAddip(String rechargeAddip) {
        this.rechargeAddip = rechargeAddip == null ? null : rechargeAddip.trim();
    }

    public String getRechargeReturn() {
        return rechargeReturn;
    }

    public void setRechargeReturn(String rechargeReturn) {
        this.rechargeReturn = rechargeReturn == null ? null : rechargeReturn.trim();
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getVerifyUser() {
		return verifyUser;
	}

	public void setVerifyUser(User verifyUser) {
		this.verifyUser = verifyUser;
	}

	public String getViewRechargeStatus() {
		return viewRechargeStatus;
	}

	public void setViewRechargeStatus(String viewRechargeStatus) {
		this.viewRechargeStatus = viewRechargeStatus;
	}

	public String getViewRechargeType() {
		return viewRechargeType;
	}

	public void setViewRechargeType(String viewRechargeType) {
		this.viewRechargeType = viewRechargeType;
	}
}