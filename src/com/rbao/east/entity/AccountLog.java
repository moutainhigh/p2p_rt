package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 资金日志
 * */
public class AccountLog extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2157735432188426931L;

	public static final String TRADE_CODE_MONEY_UNAVAILABLE = "1"; //资金冻结
	public static final String TRADE_CODE_TENDER_SUCC = "2"; //投标
	public static final String TRADE_CODE_BORROW_SUCC = "3"; //借款
	public static final String TRADE_CODE_TENDER_REWARD = "4";//投标奖励 
	public static final String TRADE_CODE_CONTINUE_REWARD = "5";//续投奖励 
	public static final String TRADE_CODE_LOAN_FEE = "6";//借款手续费 
	public static final String TRADE_CODE_REPOSSESSED = "7";//回款
	public static final String TRADE_CODE_INTEREST_FEE = "8";//利息管理费 
	public static final String TRADE_CODE_PLATFORM_LATE_INTEREST_FEE = "9";//滞纳金管理费 
	public static final String TRADE_CODE_ACCOUNT_CASH = "11"; //提现审核成功 
	public static final String TRADE_CODE_RECOMMEND_AWARD_FEE = "10";//推广邀请奖励 
	public static final String TRADE_CODE_REPAY = "12";//还款 
	public static final String TRADE_CODE_RECHARGE_ONLINE = "13";//线上充值 
	public static final String TRADE_CODE_RECHARGE_OFF_AWARD_FEE = "14";//线下充值奖励 
	public static final String TRADE_CODE_Account_Deduct_FEE = "15";//用户费用扣除 
	public static final String TRADE_CODE_Account_Deduct_FEE_Admin = "16";//管理员费用添加
	public static final String TRADE_CODE_RETURN_MONEY="17";//用户回款
	public static final String TRADE_CODE_VIP="18";//vip申请费用
	public static final String TRADE_CODE_CANCEL="19";//撤销提现
	public static final String TRADE_CODE_TRANSFER_RETURN_MONEY="20";//债权转让回款
	public static final String TRADE_CODE_TRANSFER_FEE="21";//债权转让手续费
	public static final String TRADE_CODE_TRANSFER_SUBMONEY="22";//债权转让竞标扣款
	public static final String TRADE_CODE_REGISTER_FEE="23";//注册奖励金额
	public static final String TRADE_CODE_BORROW_REDEEM="24";//赎回
	public static final String TRADE_CODE_BORROW_REDEEM_FEE="25";//赎回手续费
	public static final String TRADE_CODE_MONEY_RETURN="26";//资金解冻
	public static final String TRADE_CODE_REALNAME="27";//实名认证费用
	public static final String TRADE_CODE_ACCOUNT_CASH_FEE = "28"; //提现手续费
	public static final String WITHDRAW_APPLY="29";//提现申请
	public static final String TRADE_CODE_RECHARGE_OFFLINE = "30";//线下充值 
	public static final String TRADE_CODE_RECHARGE_BACK = "31";//后台充值 
	public static final String WITHDRAW_APPLY_NO="32";//提现失败
	public static final String TRADE_CODE_OPEN_RED = "34";//打开红包
	
	public static final String TRADE_CODE_EXPERIENCE_GOLD = "33";//体验金
	
	public static final Map<String, String> ALL_TRADE_CODE = new HashMap<String, String>();
	
	static {
		ALL_TRADE_CODE.put(TRADE_CODE_BORROW_REDEEM, "赎回");
		ALL_TRADE_CODE.put(TRADE_CODE_BORROW_REDEEM_FEE, "赎回手续费");
		ALL_TRADE_CODE.put(TRADE_CODE_REGISTER_FEE, "注册奖励金额");
		ALL_TRADE_CODE.put(TRADE_CODE_MONEY_UNAVAILABLE, "资金冻结");
		ALL_TRADE_CODE.put(TRADE_CODE_TENDER_SUCC, "投标成功");
		ALL_TRADE_CODE.put(TRADE_CODE_BORROW_SUCC, "借款成功");
		ALL_TRADE_CODE.put(TRADE_CODE_TENDER_REWARD, "投标奖励");
		ALL_TRADE_CODE.put(TRADE_CODE_CONTINUE_REWARD, "续投奖励");
		ALL_TRADE_CODE.put(TRADE_CODE_LOAN_FEE, "借款手续费");
		ALL_TRADE_CODE.put(TRADE_CODE_REPOSSESSED, "回款成功");
		ALL_TRADE_CODE.put(TRADE_CODE_INTEREST_FEE, "利息管理费");
		ALL_TRADE_CODE.put(TRADE_CODE_PLATFORM_LATE_INTEREST_FEE, "滞纳金管理费");
		ALL_TRADE_CODE.put(TRADE_CODE_ACCOUNT_CASH, "提现成功");
		ALL_TRADE_CODE.put(TRADE_CODE_ACCOUNT_CASH_FEE, "提现手续费");
		ALL_TRADE_CODE.put(TRADE_CODE_RECOMMEND_AWARD_FEE, "推广邀请奖励");
		ALL_TRADE_CODE.put(TRADE_CODE_REPAY, "还款");
		ALL_TRADE_CODE.put(TRADE_CODE_RECHARGE_ONLINE, "线上充值");
		ALL_TRADE_CODE.put(TRADE_CODE_RECHARGE_OFFLINE, "线下充值");
		ALL_TRADE_CODE.put(TRADE_CODE_RECHARGE_BACK, "后台充值");
		ALL_TRADE_CODE.put(TRADE_CODE_RECHARGE_OFF_AWARD_FEE, "线下充值奖励");
		ALL_TRADE_CODE.put(TRADE_CODE_Account_Deduct_FEE, "用户费用扣除");
		ALL_TRADE_CODE.put(TRADE_CODE_Account_Deduct_FEE_Admin, "管理员费用添加");
		ALL_TRADE_CODE.put(TRADE_CODE_RETURN_MONEY, "用户回款");
		ALL_TRADE_CODE.put(TRADE_CODE_VIP, "VIP申请费用");
		ALL_TRADE_CODE.put(TRADE_CODE_CANCEL, "撤销提现");
		ALL_TRADE_CODE.put(TRADE_CODE_TRANSFER_RETURN_MONEY,"债权转让回款");
		ALL_TRADE_CODE.put(TRADE_CODE_TRANSFER_FEE,"债权转让手续费");
		ALL_TRADE_CODE.put(TRADE_CODE_TRANSFER_SUBMONEY,"债权转让竞标扣款");
		ALL_TRADE_CODE.put(TRADE_CODE_MONEY_RETURN,"资金解冻");
		ALL_TRADE_CODE.put(TRADE_CODE_REALNAME,"实名认证费用");
		ALL_TRADE_CODE.put(WITHDRAW_APPLY, "提现申请");
		ALL_TRADE_CODE.put(WITHDRAW_APPLY_NO, "提现失败");
		ALL_TRADE_CODE.put(TRADE_CODE_OPEN_RED, "打开红包");
		ALL_TRADE_CODE.put(TRADE_CODE_EXPERIENCE_GOLD, "体验金利息");
	}
	
	private Integer id;

    private Integer userId;
    
    private String userAccount;
    
    private String tradeCode;//操作类型

    private BigDecimal allMoney;//总金额

    private BigDecimal dealMoney;//处理金额

    private BigDecimal availableMoney;//可用金额

    private BigDecimal unavailableMoney;//冻结金额

    private BigDecimal repossessedMoney;//待收金额

    private Integer tradeUserId;//交易人

    private String logRemark;

    private Date logAddtime;

    private String logAddip;

    private String viewTradeCode;
        
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode == null ? null : tradeCode.trim();
        setViewTradeCode(ALL_TRADE_CODE.get(tradeCode));
    }

    public BigDecimal getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(BigDecimal allMoney) {
        this.allMoney = allMoney;
    }

    public BigDecimal getDealMoney() {
        return dealMoney;
    }

    public void setDealMoney(BigDecimal dealMoney) {
        this.dealMoney = dealMoney;
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

    public BigDecimal getRepossessedMoney() {
        return repossessedMoney;
    }

    public void setRepossessedMoney(BigDecimal repossessedMoney) {
        this.repossessedMoney = repossessedMoney;
    }

    public Integer getTradeUserId() {
        return tradeUserId;
    }

    public void setTradeUserId(Integer tradeUserId) {
        this.tradeUserId = tradeUserId;
    }

    public String getLogRemark() {
        return logRemark;
    }

    public void setLogRemark(String logRemark) {
        this.logRemark = logRemark == null ? null : logRemark.trim();
    }

    public Date getLogAddtime() {
        return logAddtime;
    }

    public void setLogAddtime(Date logAddtime) {
        this.logAddtime = logAddtime;
    }

    public String getLogAddip() {
        return logAddip;
    }

    public void setLogAddip(String logAddip) {
        this.logAddip = logAddip == null ? null : logAddip.trim();
    }

	public String getViewTradeCode() {
		return viewTradeCode;
	}

	public void setViewTradeCode(String viewTradeCode) {
		this.viewTradeCode = viewTradeCode;
	}
}