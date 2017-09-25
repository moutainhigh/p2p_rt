package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 提现实体
 * */
public class AccountCash extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Integer cashApply=0;
	public static final Integer cashSuccess=1;
	public static final Integer cashFail=2;
	public static final Integer cashBack=3;
	public static final Integer cashing=4;
	
	public static final Integer cashTypeFee=3;//1 ,按比例收费，2，按区间收费， 3,按每笔收费

	private Integer id;

    private Integer userId;

    private Integer cashStatus;//状态

    private String cashBank;//银行

    private String cashBranch;//支行

    private BigDecimal cashTotal;//总额

    private BigDecimal cashAccount;//账号

    private BigDecimal cashFee;//费用

    private Integer verifyUserid;

    private Date verifyTime;

    private String verifyRemark;

    private String cashAddip;

    private Date cashAddtime;
    
    private String userAccount;
    
    private String userRealname;
    
    private String cashBankAccount;//账号
    
    private String verifyName;
    private String userPhone;
    private String bankCity;
    public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	//提现单号
    private String noOrder;
    //提现类型 0：线下 1：h5线上 2：App
    private Integer cashType;
    
    
    
    
    


	public Integer getCashType() {
		return cashType;
	}

	public void setCashType(Integer cashType) {
		this.cashType = cashType;
	}

	public String getNoOrder() {
		return noOrder;
	}

	public void setNoOrder(String noOrder) {
		this.noOrder = noOrder;
	}

	public String getVerifyName() {
		return verifyName;
	}

	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}

	public String getCashBankAccount() {
		return cashBankAccount;
	}

	public void setCashBankAccount(String cashBankAccount) {
		this.cashBankAccount = cashBankAccount;
	}

	public String getUserRealname() {
		return userRealname;
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCashStatus() {
        return cashStatus;
    }

    public void setCashStatus(Integer cashStatus) {
        this.cashStatus = cashStatus;
    }

    public String getCashBank() {
        return cashBank;
    }

    public void setCashBank(String cashBank) {
        this.cashBank = cashBank == null ? null : cashBank.trim();
    }

    public String getCashBranch() {
        return cashBranch;
    }

    public void setCashBranch(String cashBranch) {
        this.cashBranch = cashBranch == null ? null : cashBranch.trim();
    }

    public BigDecimal getCashTotal() {
        return cashTotal;
    }

    public void setCashTotal(BigDecimal cashTotal) {
        this.cashTotal = cashTotal;
    }

    public BigDecimal getCashAccount() {
        return cashAccount;
    }

    public void setCashAccount(BigDecimal cashAccount) {
        this.cashAccount = cashAccount;
    }

    public BigDecimal getCashFee() {
        return cashFee;
    }

    public void setCashFee(BigDecimal cashFee) {
        this.cashFee = cashFee;
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

    public String getCashAddip() {
        return cashAddip;
    }

    public void setCashAddip(String cashAddip) {
        this.cashAddip = cashAddip == null ? null : cashAddip.trim();
    }

    public Date getCashAddtime() {
        return cashAddtime;
    }

    public void setCashAddtime(Date cashAddtime) {
        this.cashAddtime = cashAddtime;
    }
}