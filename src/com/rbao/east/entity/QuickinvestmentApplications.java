package com.rbao.east.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QuickinvestmentApplications extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//状态
	public static final Map<Integer,String> ALL_QUICKINVESTMENT_STATUS = new HashMap<Integer,String>();
	public static final Integer QUICKINVESTMENT_STATUS_WAIT = 1; //待审核
	public static final Integer QUICKINVESTMENT_STATUS_SUCCESS = 2;//通过
	public static final Integer QUICKINVESTMENT_STATUS_FAIL= 3;//未通过
	
	static{
		ALL_QUICKINVESTMENT_STATUS.put(QUICKINVESTMENT_STATUS_WAIT, "待审核");
		ALL_QUICKINVESTMENT_STATUS.put(QUICKINVESTMENT_STATUS_SUCCESS, "通过");
		ALL_QUICKINVESTMENT_STATUS.put(QUICKINVESTMENT_STATUS_FAIL, "未通过");
	}

	private Integer id;

    private String investUserName;

    private String investUserTel;

    private String investUserSex;

    private String investUserArea;

    private Integer investUserCycle;

    private Integer investMoney;

    private String investBeginTime;

    private String investEndTime;

    private Date investAddDatetime;

    private String investAddIp;

    private Integer investVerifyUser;

    private String investVerifyRemark;

    private Date investVerifyDatetime;

    private Integer investVerifyStatus;

    private Integer investProductsId;
    
    private String viewStatus;
    
    private User virifyUser;
    
    private FinancialProducts investProduct;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvestUserName() {
        return investUserName;
    }

    public void setInvestUserName(String investUserName) {
        this.investUserName = investUserName == null ? null : investUserName.trim();
    }

    public String getInvestUserTel() {
        return investUserTel;
    }

    public void setInvestUserTel(String investUserTel) {
        this.investUserTel = investUserTel == null ? null : investUserTel.trim();
    }

    public String getInvestUserSex() {
        return investUserSex;
    }

    public void setInvestUserSex(String investUserSex) {
        this.investUserSex = investUserSex == null ? null : investUserSex.trim();
    }

    public String getInvestUserArea() {
        return investUserArea;
    }

    public void setInvestUserArea(String investUserArea) {
        this.investUserArea = investUserArea == null ? null : investUserArea.trim();
    }

    public Integer getInvestUserCycle() {
        return investUserCycle;
    }

    public void setInvestUserCycle(Integer investUserCycle) {
        this.investUserCycle = investUserCycle;
    }

    public Integer getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(Integer investMoney) {
        this.investMoney = investMoney;
    }

    public String getInvestBeginTime() {
        return investBeginTime;
    }

    public void setInvestBeginTime(String investBeginTime) {
        this.investBeginTime = investBeginTime == null ? null : investBeginTime.trim();
    }

    public String getInvestEndTime() {
        return investEndTime;
    }

    public void setInvestEndTime(String investEndTime) {
        this.investEndTime = investEndTime == null ? null : investEndTime.trim();
    }

    public Date getInvestAddDatetime() {
        return investAddDatetime;
    }

    public void setInvestAddDatetime(Date investAddDatetime) {
        this.investAddDatetime = investAddDatetime;
    }

    public String getInvestAddIp() {
        return investAddIp;
    }

    public void setInvestAddIp(String investAddIp) {
        this.investAddIp = investAddIp == null ? null : investAddIp.trim();
    }

    public Integer getInvestVerifyUser() {
        return investVerifyUser;
    }

    public void setInvestVerifyUser(Integer investVerifyUser) {
        this.investVerifyUser = investVerifyUser;
    }

    public String getInvestVerifyRemark() {
        return investVerifyRemark;
    }

    public void setInvestVerifyRemark(String investVerifyRemark) {
        this.investVerifyRemark = investVerifyRemark == null ? null : investVerifyRemark.trim();
    }

    public Date getInvestVerifyDatetime() {
        return investVerifyDatetime;
    }

    public void setInvestVerifyDatetime(Date investVerifyDatetime) {
        this.investVerifyDatetime = investVerifyDatetime;
    }

    public Integer getInvestVerifyStatus() {
        return investVerifyStatus;
    }

    public void setInvestVerifyStatus(Integer investVerifyStatus) {
        this.investVerifyStatus = investVerifyStatus;
        setViewStatus(ALL_QUICKINVESTMENT_STATUS.get(investVerifyStatus));
    }

    public Integer getInvestProductsId() {
        return investProductsId;
    }

    public void setInvestProductsId(Integer investProductsId) {
        this.investProductsId = investProductsId;
    }

	public String getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}

	public User getVirifyUser() {
		return virifyUser;
	}

	public void setVirifyUser(User virifyUser) {
		this.virifyUser = virifyUser;
	}

	public FinancialProducts getInvestProduct() {
		return investProduct;
	}

	public void setInvestProduct(FinancialProducts investProduct) {
		this.investProduct = investProduct;
	}
}