package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BorrowTender extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2398022994355094430L;
	
	public static final Integer STATUS_NEW = 1; //新建
	public static final Integer STATUS_TENDER_FAILD = 2;//复审未通过、投标失败
	public static final Integer STATUS_REPAYING = 3;//还款中
	public static final Integer STATUS_REPAYED = 4;//还款成功
	public static final Integer STATUS_OVERDUE = 5;//逾期
	public static final Integer STATUS_DELETED = 6;//删除
	public static final Integer STATUS_TRANSFER=7;//已转让
	public static final Integer STATUS_REDEEM=8;//已赎回
	
	public static final Integer CAL_INTEREST_TYPE_BACK = 1;//利息返还
	public static final Integer CAL_INTEREST_TYPE_INVERST = 2;//利息复投
	
	public static final Integer TENDER_TYPE_SELF = 1;//手动投标
	public static final Integer TENDER_TYPE_AUTO = 2;//自动投标
	public static final Integer TENDER_TYPE_TRANSFER = 3;//债券转让 认购
	
	public static final Map<Integer,String> ALL_STATUS = new HashMap<Integer,String>();
	
	public static final Map<Integer, String> ALL_TENDER_TYPE = new HashMap<Integer, String>();
	
	static{
		ALL_STATUS.put(STATUS_REDEEM, "已赎回");
		ALL_STATUS.put(STATUS_NEW, "待审核");
		ALL_STATUS.put(STATUS_TENDER_FAILD, "投标失败");
		ALL_STATUS.put(STATUS_REPAYING, "还款中");
		ALL_STATUS.put(STATUS_REPAYED, "还款成功");
		ALL_STATUS.put(STATUS_OVERDUE, "已逾期");
		ALL_STATUS.put(STATUS_DELETED, "删除");
		ALL_STATUS.put(STATUS_TRANSFER, "已转让");
		
		//投标类型
		ALL_TENDER_TYPE.put(TENDER_TYPE_SELF, "手动投标");
		ALL_TENDER_TYPE.put(TENDER_TYPE_AUTO, "自动投标");
		ALL_TENDER_TYPE.put(TENDER_TYPE_TRANSFER, "债券转让");
	}

	private Integer id;

    private Integer userId;

    private Integer tenderStatus;

    private Integer borrowId;

    private BigDecimal tenderAmount;

    private BigDecimal effectiveAmount;

    private BigDecimal repaymentAmount;

    private BigDecimal interestAmount;//利息

    private BigDecimal paidAmount;

    private BigDecimal stayingAmount; //本金

    private BigDecimal stayingInterest;

    private BigDecimal interestPaid;

    private Date tenderAddtime;

    private String tenderAddip;
    
    private String tenderFrom ;
    
    private Integer calInterestType ;

    private Integer transferCount;
    
    private Integer tenderType;
    
    private String agreementPath;
    
    private Date agreementTime ;

    private BigDecimal deductionMoney ;
    
	public Date getAgreementTime() {
		return agreementTime;
	}

	public void setAgreementTime(Date agreementTime) {
		this.agreementTime = agreementTime;
	}

	private Integer tenderCopies;
    
    
    
    
    
    public String getAgreementPath() {
		return agreementPath;
	}

	public void setAgreementPath(String agreementPath) {
		this.agreementPath = agreementPath;
	}

	public Integer getTenderType() {
		return tenderType;
	}

	public void setTenderType(Integer tenderType) {
		this.tenderType = tenderType;
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

    public Integer getTenderStatus() {
        return tenderStatus;
    }

    public void setTenderStatus(Integer tenderStatus) {
        this.tenderStatus = tenderStatus;
    }

    public Integer getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Integer borrowId) {
        this.borrowId = borrowId;
    }

    public BigDecimal getTenderAmount() {
        return tenderAmount;
    }

    public void setTenderAmount(BigDecimal tenderAmount) {
        this.tenderAmount = tenderAmount;
    }

    public BigDecimal getEffectiveAmount() {
        return effectiveAmount;
    }

    public void setEffectiveAmount(BigDecimal effectiveAmount) {
        this.effectiveAmount = effectiveAmount;
    }

    public BigDecimal getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(BigDecimal repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public BigDecimal getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(BigDecimal interestAmount) {
        this.interestAmount = interestAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getStayingAmount() {
        return stayingAmount;
    }

    public void setStayingAmount(BigDecimal stayingAmount) {
        this.stayingAmount = stayingAmount;
    }

    public BigDecimal getStayingInterest() {
        return stayingInterest;
    }

    public void setStayingInterest(BigDecimal stayingInterest) {
        this.stayingInterest = stayingInterest;
    }

    public BigDecimal getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(BigDecimal interestPaid) {
        this.interestPaid = interestPaid;
    }

    public Date getTenderAddtime() {
        return tenderAddtime;
    }

    public void setTenderAddtime(Date tenderAddtime) {
        this.tenderAddtime = tenderAddtime;
    }

    public String getTenderAddip() {
        return tenderAddip;
    }

    public void setTenderAddip(String tenderAddip) {
        this.tenderAddip = tenderAddip == null ? null : tenderAddip.trim();
    }

	public String getTenderFrom() {
		return tenderFrom;
	}

	public void setTenderFrom(String tenderFrom) {
		this.tenderFrom = tenderFrom;
	}

	public Integer getCalInterestType() {
		return calInterestType;
	}

	public void setCalInterestType(Integer calInterestType) {
		this.calInterestType = calInterestType;
	}

	public Integer getTransferCount() {
		return transferCount;
	}

	public void setTransferCount(Integer transferCount) {
		this.transferCount = transferCount;
	}
	public Integer getTenderCopies() {
		return tenderCopies;
	}

	public void setTenderCopies(Integer tenderCopies) {
		this.tenderCopies = tenderCopies;
	}

	public BigDecimal getDeductionMoney() {
		return deductionMoney;
	}

	public void setDeductionMoney(BigDecimal deductionMoney) {
		this.deductionMoney = deductionMoney;
	}
	
}