package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Borrow extends BaseEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5658700765009362721L;
	
	public static final String BORROW_TYPE_LI = "LI"; //给力标
	public static final String BORROW_TYPE_ZHUAN = "ZHUAN"; //流转标
	public static final String BORROW_TYPE_MIAO = "MIAO"; //秒标
	public static final String BORROW_TYPE_JING = "JING"; //净值标
	public static final String BORROW_TYPE_XING = "XING"; //信用标
	public static final String BORROW_TYPE_DING = "DING"; //定活通
	public static final String BORROW_TYPE_ZHUAN_DF = "DING_DF"; //变种流转标

	public static final Integer STATUS_NEW = 1;//新建、待审核
	public static final Integer STATUS_FIRSTAUDIT_YES = 2;//初审通过
	public static final Integer STATUS_FIRSTAUDIT_NO = 3;//初审未通过
	public static final Integer STATUS_FULL = 4;//满标
	public static final Integer STATUS_REPLYING = 5;//收益中
	public static final Integer STATUS_REPLY_SUCCESS = 6;//还款完成
	public static final Integer STATUS_OVERDUE = 7;//逾期
	public static final Integer STATUS_CANCEL_BY_SELF = 8;//自己取消
	public static final Integer STATUS_CANCEL_BY_ADMIN = 9;//平台取消
	public static final Integer STATUS_REVIEW_YES = 10;//复审通过
	public static final Integer STATUS_REVIEW_NO = 11;//复审未通过
	public static final Integer STATUS_FLOW_STANDARD=12;//已流标
	
	public static final Integer IS_DAY_Y = 1; //天标
	public static final Integer IS_DAY_N = 2;//月标
	
	public static final Integer REPAYMENT_STYLE_ONETIME = 1;//一次性还款
	public static final Integer REPAYMENT_STYLE_MONTHLY = 2;//按月分期
	public static final Integer REPAYMENT_STYLE_MONTHLY_INTEREST_DUE_ALL = 3;//每月还息到期还本
	
	public static final String USE_SHORT="1";//短期周转
	public static final String USE_BUSINESS="2";//生意周转
	public static final String USE_LIFE="3";//生活周转
	public static final String USE_SHOP="4";//购物消费
	public static final String USE_CASH="5";//不提现借款
	public static final String USE_WORK="6";//创业借款
	public static final String USE_ELSE="7";//其他借款
	
	public static final String BID_RANK_ONE="1";// 一星标
	public static final String BID_RANK_TWO="2";// 二星标
	public static final String BID_RANK_THREE="3";// 三星标
	public static final String BID_RANK_FORE="4";// 四星标
	public static final String BID_RANK_Five="5";// 五星标
	public static final String BID_RANK_Six="6";// 六星标
	
	public static final String CREDIT_TYPE_1="1";// 信用
	public static final String CREDIT_TYPE_2="2";// 质押
	public static final String CREDIT_TYPE_3="3";// 抵押
	public static final String CREDIT_TYPE_4="4";// 担保
	
	public static final Map<Integer,String> ALL_STATUS = new HashMap<Integer,String>();
	public static final Map<Integer,String> ALL_ISDAY = new HashMap<Integer,String>();
	public static final Map<Integer,String> ALL_REPAYMENT_STYLE = new TreeMap<Integer,String>();
	public static final Map<String,String> ALL_BORROW_USE = new TreeMap<String,String>();
	public static final Map<String, String> ALL_BID_RANK=new TreeMap<String,String>();
	public static final Map<String, String> ALL_CREDIT_TYPE = new TreeMap<String,String>();
	
	static {
		ALL_STATUS.put(STATUS_FLOW_STANDARD, "已流标");
		ALL_STATUS.put(STATUS_NEW, "待审核");
		ALL_STATUS.put(STATUS_FIRSTAUDIT_YES, "初审通过");
		ALL_STATUS.put(STATUS_FIRSTAUDIT_NO, "初审未通过");
		//ALL_STATUS.put(STATUS_FULL, "满标待复审");
		ALL_STATUS.put(STATUS_FULL, "复审中");
		ALL_STATUS.put(STATUS_REPLYING, "收益中");
		ALL_STATUS.put(STATUS_REPLY_SUCCESS, "还款完成");
		ALL_STATUS.put(STATUS_OVERDUE, "逾期");
		ALL_STATUS.put(STATUS_CANCEL_BY_SELF, "自己取消");
		ALL_STATUS.put(STATUS_CANCEL_BY_ADMIN, "平台取消");
		ALL_STATUS.put(STATUS_REVIEW_NO, "复审未通过");
		
		//还款方式
		ALL_REPAYMENT_STYLE.put(REPAYMENT_STYLE_MONTHLY_INTEREST_DUE_ALL, "先息后本");
		ALL_REPAYMENT_STYLE.put(REPAYMENT_STYLE_ONETIME, "到期还本付息");
		ALL_REPAYMENT_STYLE.put(REPAYMENT_STYLE_MONTHLY, "按月分期");
		
		
		//借款用途
		ALL_BORROW_USE.put(USE_SHORT, "短期周转");
		ALL_BORROW_USE.put(USE_BUSINESS, "生意周转");
		ALL_BORROW_USE.put(USE_LIFE, "生活周转");
		ALL_BORROW_USE.put(USE_SHOP, "购物消费");
		ALL_BORROW_USE.put(USE_CASH, "不提现借款");
		ALL_BORROW_USE.put(USE_WORK, "创业借款");
		ALL_BORROW_USE.put(USE_ELSE, "其他借款");
		
		//标星等级
		ALL_BID_RANK.put(BID_RANK_ONE, "一星标");
		ALL_BID_RANK.put(BID_RANK_TWO, "二星标");
		ALL_BID_RANK.put(BID_RANK_THREE, "三星标");
		ALL_BID_RANK.put(BID_RANK_FORE, "四星标");
		ALL_BID_RANK.put(BID_RANK_Five, "五星标");
		ALL_BID_RANK.put(BID_RANK_Six, "六星标");
		
		//信息类型
		ALL_CREDIT_TYPE.put(CREDIT_TYPE_1, "信用");
		ALL_CREDIT_TYPE.put(CREDIT_TYPE_2, "质押");
		ALL_CREDIT_TYPE.put(CREDIT_TYPE_3, "抵押");
		ALL_CREDIT_TYPE.put(CREDIT_TYPE_4, "担保");
	}
	
	
	private Integer id;

    private Integer siteId;

    private Integer userId;

    private String borrowTitle;

    private Integer borrowStatus;

    private Integer borrowSequence;

    private Integer borrowHits;

    private String vouchAward;

    private String vouchUser;

    private String vouchAmount;

    private Integer vouchTimes;

    private Date publishDatetime;

    private Integer verifyTrialUser; //初审人

    private Date verifyTrialTime;//初审时间

    private String verifyTrialRemark;

    private Integer verifyReviewUser;

    private Date verifyReviewTime;

    private String verifyReviewRemark;

    private Integer repaymentUser;

    private BigDecimal repaymentAmount;

    private BigDecimal monthlyRepayment;

    private BigDecimal paidAmount;

    private BigDecimal paidInterest;

    private Date repaymentTime;

    private Date successTime;

    private Date endTime;

    private BigDecimal paymentAmount;

    private String borrowUse;

    private Integer borrowTimeLimit;

    private Integer repaymentStyle;

    private BigDecimal tenderSum;

    private BigDecimal borrowSum; //借贷总金额

    private Integer tenderTimes;

    private BigDecimal annualInterestRate; //年利率
    
    private BigDecimal monthlyInterestRate;//月利率。程序中需要用来传值，跟数据库字段没有关联。

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

    private Integer validTime; //有效时间
    
    private Date allowTenderTime; //允许投标时间

    private String tenderAward;

    private BigDecimal partAmount;

    private BigDecimal proportionRate; //奖励比例

    private Integer isFalse;

    private Integer openAccount;

    private Integer openBorrow;

    private Integer openTender;

    private Integer openCredit;

    private Date borrowAddtime;

    private String borrowAddip;

    private Integer bidKind;

    private String tenderPassword;

    private Integer isDay;

    private String payDatetime;

    private String borrowContent;
    
    private Integer userTypeId;
    private Integer bidRank;
    private String creditType;
    private BigDecimal minWaitRepossess ;//限制投标人代收款
    private String borrowNo ;
    private BigDecimal amountPerCopies; //每份金额
    
    private String agreementPath ;
    
    private String borrowTypeCode ;//标种code
    
    private String viewStatus ;
    private String viewIsDay ;
    private String viewRepaymentStyle ;
    
    private User user;
    
    private User trialUser;//初审人
    
    private User reviewUser;//复审人
    
    private User lastRepaymentUser;//最后还款人
    
    private User vouchUsr;//担保人
    
    private String viewBorrowUse;
    
    private BorrowType borrowType;
    
    private String userAccount;
    
    private String borrowTypeName;
    
    private BigDecimal tenderProgressRate;
    
    private String userRealName;
    
    private String borrowPicture;//标logo
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBorrowTitle() {
        return borrowTitle;
    }

    public void setBorrowTitle(String borrowTitle) {
        this.borrowTitle = borrowTitle == null ? null : borrowTitle.trim();
    }

    public Integer getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(Integer borrowStatus) {
        this.borrowStatus = borrowStatus;
        setViewStatus(ALL_STATUS.get(borrowStatus));
    }

    public Integer getBorrowSequence() {
        return borrowSequence;
    }

    public void setBorrowSequence(Integer borrowSequence) {
        this.borrowSequence = borrowSequence;
    }

    public Integer getBorrowHits() {
        return borrowHits;
    }

    public void setBorrowHits(Integer borrowHits) {
        this.borrowHits = borrowHits;
    }

    public String getVouchAward() {
        return vouchAward;
    }

    public void setVouchAward(String vouchAward) {
        this.vouchAward = vouchAward == null ? null : vouchAward.trim();
    }

    public String getVouchUser() {
        return vouchUser;
    }

    public void setVouchUser(String vouchUser) {
        this.vouchUser = vouchUser == null ? null : vouchUser.trim();
    }

    public String getVouchAmount() {
        return vouchAmount;
    }

    public void setVouchAmount(String vouchAmount) {
        this.vouchAmount = vouchAmount == null ? null : vouchAmount.trim();
    }

    public Integer getVouchTimes() {
        return vouchTimes;
    }

    public void setVouchTimes(Integer vouchTimes) {
        this.vouchTimes = vouchTimes;
    }

    public Date getPublishDatetime() {
        return publishDatetime;
    }

    public void setPublishDatetime(Date publishDatetime) {
        this.publishDatetime = publishDatetime;
    }

    public Integer getVerifyTrialUser() {
        return verifyTrialUser;
    }

    public void setVerifyTrialUser(Integer verifyTrialUser) {
        this.verifyTrialUser = verifyTrialUser;
    }

    public Date getVerifyTrialTime() {
        return verifyTrialTime;
    }

    public void setVerifyTrialTime(Date verifyTrialTime) {
        this.verifyTrialTime = verifyTrialTime;
    }

    public String getVerifyTrialRemark() {
        return verifyTrialRemark;
    }

    public void setVerifyTrialRemark(String verifyTrialRemark) {
        this.verifyTrialRemark = verifyTrialRemark == null ? null : verifyTrialRemark.trim();
    }

    public Integer getVerifyReviewUser() {
        return verifyReviewUser;
    }

    public void setVerifyReviewUser(Integer verifyReviewUser) {
        this.verifyReviewUser = verifyReviewUser;
    }

    public Date getVerifyReviewTime() {
        return verifyReviewTime;
    }

    public void setVerifyReviewTime(Date verifyReviewTime) {
        this.verifyReviewTime = verifyReviewTime;
    }

    public String getVerifyReviewRemark() {
        return verifyReviewRemark;
    }

    public void setVerifyReviewRemark(String verifyReviewRemark) {
        this.verifyReviewRemark = verifyReviewRemark == null ? null : verifyReviewRemark.trim();
    }

    public Integer getRepaymentUser() {
        return repaymentUser;
    }

    public void setRepaymentUser(Integer repaymentUser) {
        this.repaymentUser = repaymentUser;
    }

    public BigDecimal getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(BigDecimal repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public BigDecimal getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public void setMonthlyRepayment(BigDecimal monthlyRepayment) {
        this.monthlyRepayment = monthlyRepayment;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getPaidInterest() {
        return paidInterest;
    }

    public void setPaidInterest(BigDecimal paidInterest) {
        this.paidInterest = paidInterest;
    }

    public Date getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(Date repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public Date getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getBorrowUse() {
        return borrowUse;
    }

    public void setBorrowUse(String borrowUse) {
        this.borrowUse = borrowUse == null ? null : borrowUse.trim();
        if(borrowUse!=null){
        	setViewBorrowUse(ALL_BORROW_USE.get(borrowUse));
        }
    }

    public Integer getBorrowTimeLimit() {
        return borrowTimeLimit;
    }

    public void setBorrowTimeLimit(Integer borrowTimeLimit) {
        this.borrowTimeLimit = borrowTimeLimit;
    }

    public Integer getRepaymentStyle() {
        return repaymentStyle;
    }

    public void setRepaymentStyle(Integer repaymentStyle) {
        this.repaymentStyle = repaymentStyle;
        setViewRepaymentStyle(ALL_REPAYMENT_STYLE.get(repaymentStyle));
    }

    public BigDecimal getTenderSum() {
        return tenderSum;
    }

    public void setTenderSum(BigDecimal tenderSum) {
        this.tenderSum = tenderSum;
    }

    public BigDecimal getBorrowSum() {
        return borrowSum;
    }

    public void setBorrowSum(BigDecimal borrowSum) {
        this.borrowSum = borrowSum;
    }

    public Integer getTenderTimes() {
        return tenderTimes;
    }

    public void setTenderTimes(Integer tenderTimes) {
        this.tenderTimes = tenderTimes;
    }

    public BigDecimal getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void setAnnualInterestRate(BigDecimal annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public String getTenderAward() {
        return tenderAward;
    }

    public void setTenderAward(String tenderAward) {
        this.tenderAward = tenderAward == null ? null : tenderAward.trim();
    }

    public BigDecimal getPartAmount() {
        return partAmount;
    }

    public void setPartAmount(BigDecimal partAmount) {
        this.partAmount = partAmount;
    }

    public BigDecimal getProportionRate() {
        return proportionRate;
    }

    public void setProportionRate(BigDecimal proportionRate) {
        this.proportionRate = proportionRate;
    }

    public Integer getIsFalse() {
        return isFalse;
    }

    public void setIsFalse(Integer isFalse) {
        this.isFalse = isFalse;
    }

    public Integer getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(Integer openAccount) {
        this.openAccount = openAccount;
    }

    public Integer getOpenBorrow() {
        return openBorrow;
    }

    public void setOpenBorrow(Integer openBorrow) {
        this.openBorrow = openBorrow;
    }

    public Integer getOpenTender() {
        return openTender;
    }

    public void setOpenTender(Integer openTender) {
        this.openTender = openTender;
    }

    public Integer getOpenCredit() {
        return openCredit;
    }

    public void setOpenCredit(Integer openCredit) {
        this.openCredit = openCredit;
    }

    public Date getBorrowAddtime() {
        return borrowAddtime;
    }

    public void setBorrowAddtime(Date borrowAddtime) {
        this.borrowAddtime = borrowAddtime;
    }

    public String getBorrowAddip() {
        return borrowAddip;
    }

    public void setBorrowAddip(String borrowAddip) {
        this.borrowAddip = borrowAddip == null ? null : borrowAddip.trim();
    }

    public Integer getBidKind() {
        return bidKind;
    }

    public void setBidKind(Integer bidKind) {
        this.bidKind = bidKind;
    }

    public String getTenderPassword() {
        return tenderPassword;
    }

    public void setTenderPassword(String tenderPassword) {
        this.tenderPassword = tenderPassword == null ? null : tenderPassword.trim();
    }

    public Integer getIsDay() {
        return isDay;
    }

    public void setIsDay(Integer isDay) {
        this.isDay = isDay;
        setViewIsDay(ALL_ISDAY.get(isDay));
    }

    public String getPayDatetime() {
        return payDatetime;
    }

    public void setPayDatetime(String payDatetime) {
        this.payDatetime = payDatetime == null ? null : payDatetime.trim();
    }

    public String getBorrowContent() {
        return borrowContent;
    }

    public void setBorrowContent(String borrowContent) {
        this.borrowContent = borrowContent == null ? null : borrowContent.trim();
    }

	public Integer getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}
	
	public Integer getBidRank() {
		return bidRank;
	}

	public void setBidRank(Integer bidRank) {
		this.bidRank = bidRank;
	}

	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	public Date getAllowTenderTime() {
		return allowTenderTime;
	}

	public void setAllowTenderTime(Date allowTenderTime) {
		this.allowTenderTime = allowTenderTime;
	}

	public String getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(String viewStatus) {
		this.viewStatus = viewStatus;
	}

	public String getViewIsDay() {
		return viewIsDay;
	}

	public void setViewIsDay(String viewIsDay) {
		this.viewIsDay = viewIsDay;
	}

	public String getViewRepaymentStyle() {
		return viewRepaymentStyle;
	}

	public void setViewRepaymentStyle(String viewRepaymentStyle) {
		this.viewRepaymentStyle = viewRepaymentStyle;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getMonthlyInterestRate() {
		return monthlyInterestRate;
	}

	public void setMonthlyInterestRate(BigDecimal monthlyInterestRate) {
		this.monthlyInterestRate = monthlyInterestRate;
	}

	public BigDecimal getMinWaitRepossess() {
		return minWaitRepossess;
	}

	public void setMinWaitRepossess(BigDecimal minWaitRepossess) {
		this.minWaitRepossess = minWaitRepossess;
	}

	public User getTrialUser() {
		return trialUser;
	}

	public void setTrialUser(User trialUser) {
		this.trialUser = trialUser;
	}

	public User getReviewUser() {
		return reviewUser;
	}

	public void setReviewUser(User reviewUser) {
		this.reviewUser = reviewUser;
	}

	public User getLastRepaymentUser() {
		return lastRepaymentUser;
	}

	public void setLastRepaymentUser(User lastRepaymentUser) {
		this.lastRepaymentUser = lastRepaymentUser;
	}
	public String getBorrowNo() {
		return borrowNo;
	}

	public void setBorrowNo(String borrowNo) {
		this.borrowNo = borrowNo;
	}

	public String getViewBorrowUse() {
		return viewBorrowUse;
	}

	public void setViewBorrowUse(String viewBorrowUse) {
		this.viewBorrowUse = viewBorrowUse;
	}

	public BigDecimal getAmountPerCopies() {
		return amountPerCopies;
	}

	public void setAmountPerCopies(BigDecimal amountPerCopies) {
		this.amountPerCopies = amountPerCopies;
	}

	public BorrowType getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(BorrowType borrowType) {
		this.borrowType = borrowType;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getBorrowTypeName() {
		return borrowTypeName;
	}

	public void setBorrowTypeName(String borrowTypeName) {
		this.borrowTypeName = borrowTypeName;
	}

	public BigDecimal getTenderProgressRate() {
		return tenderProgressRate;
	}

	public void setTenderProgressRate(BigDecimal tenderProgressRate) {
		this.tenderProgressRate = tenderProgressRate;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public User getVouchUsr() {
		return vouchUsr;
	}

	public void setVouchUsr(User vouchUsr) {
		this.vouchUsr = vouchUsr;
	}

	public String getAgreementPath() {
		return agreementPath;
	}

	public void setAgreementPath(String agreementPath) {
		this.agreementPath = agreementPath;
	}

	public String getBorrowTypeCode() {
		return borrowTypeCode;
	}

	public void setBorrowTypeCode(String borrowTypeCode) {
		this.borrowTypeCode = borrowTypeCode;
	}
	public String getBorrowPicture() {
		return borrowPicture;
	}

	public void setBorrowPicture(String borrowPicture) {
		this.borrowPicture = borrowPicture;
	}
    
}