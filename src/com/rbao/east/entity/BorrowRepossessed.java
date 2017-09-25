package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BorrowRepossessed extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7841333732874862078L;

	public static final Integer STATUS_REPOSSESSING = 1;//收款中
	public static final Integer STATUS_REPOSSESSED_SUCC = 2;//已还款
	public static final Integer STATUS_OVERDUE = 3;//逾期
	public static final Integer STATUS_RETIRED_BILL=4;//已赎回
	public static final Integer STATUS_TRANSFER=5;//已转让
	public static final Map<Integer,String> ALL_STATUS = new HashMap<Integer,String>();
	static {
		ALL_STATUS.put(STATUS_REPOSSESSING, "收款中");
		ALL_STATUS.put(STATUS_REPOSSESSED_SUCC, "已还款");
		ALL_STATUS.put(STATUS_OVERDUE, "逾期");
		ALL_STATUS.put(STATUS_RETIRED_BILL, "已赎回");
		ALL_STATUS.put(STATUS_TRANSFER, "已转让");
		}
	private Integer id;

    private Integer repossessedStatus;

    private Integer repossessedSequence;

    private Integer tenderId;

    private Date prepareDatetime;

    private Date paidDatetime;
    
    private Integer currentPeriod;
    
    private Integer totalPeriod;

    private BigDecimal prepareAmount;

    private BigDecimal paidAmount;

    private BigDecimal repossessedInterest;

    private BigDecimal repossessedCapital;

    private Integer lateDays;

    private BigDecimal lateInterest;

    private Date repossessedAddtime;

    private String repossessedAddip;
    
    private Integer repaymentId;
    
    private Integer userId;
    
    private BigDecimal deductInterestFee;
    
    private BigDecimal deductLateInterestFee;
  
    
    
    

	public BigDecimal getDeductInterestFee() {
		return deductInterestFee;
	}

	public void setDeductInterestFee(BigDecimal deductInterestFee) {
		this.deductInterestFee = deductInterestFee;
	}

	public BigDecimal getDeductLateInterestFee() {
		return deductLateInterestFee;
	}

	public void setDeductLateInterestFee(BigDecimal deductLateInterestFee) {
		this.deductLateInterestFee = deductLateInterestFee;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRepossessedStatus() {
        return repossessedStatus;
    }

    public void setRepossessedStatus(Integer repossessedStatus) {
        this.repossessedStatus = repossessedStatus;
    }

    public Integer getRepossessedSequence() {
        return repossessedSequence;
    }

    public void setRepossessedSequence(Integer repossessedSequence) {
        this.repossessedSequence = repossessedSequence;
    }

    public Integer getTenderId() {
        return tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }

    public Date getPrepareDatetime() {
        return prepareDatetime;
    }

    public void setPrepareDatetime(Date prepareDatetime) {
        this.prepareDatetime = prepareDatetime;
    }

    public Date getPaidDatetime() {
        return paidDatetime;
    }

    public void setPaidDatetime(Date paidDatetime) {
        this.paidDatetime = paidDatetime;
    }

    public BigDecimal getPrepareAmount() {
        return prepareAmount;
    }

    public void setPrepareAmount(BigDecimal prepareAmount) {
        this.prepareAmount = prepareAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getRepossessedInterest() {
        return repossessedInterest;
    }

    public void setRepossessedInterest(BigDecimal repossessedInterest) {
        this.repossessedInterest = repossessedInterest;
    }

    public BigDecimal getRepossessedCapital() {
        return repossessedCapital;
    }

    public void setRepossessedCapital(BigDecimal repossessedCapital) {
        this.repossessedCapital = repossessedCapital;
    }

    public Integer getLateDays() {
        return lateDays;
    }

    public void setLateDays(Integer lateDays) {
        this.lateDays = lateDays;
    }

    public BigDecimal getLateInterest() {
        return lateInterest;
    }

    public void setLateInterest(BigDecimal lateInterest) {
        this.lateInterest = lateInterest ;
    }

    public Date getRepossessedAddtime() {
        return repossessedAddtime;
    }

    public void setRepossessedAddtime(Date repossessedAddtime) {
        this.repossessedAddtime = repossessedAddtime;
    }

    public String getRepossessedAddip() {
        return repossessedAddip;
    }

    public void setRepossessedAddip(String repossessedAddip) {
        this.repossessedAddip = repossessedAddip == null ? null : repossessedAddip.trim();
    }

	public Integer getRepaymentId() {
		return repaymentId;
	}

	public void setRepaymentId(Integer repaymentId) {
		this.repaymentId = repaymentId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCurrentPeriod() {
		return currentPeriod;
	}

	public void setCurrentPeriod(Integer currentPeriod) {
		this.currentPeriod = currentPeriod;
	}

	public Integer getTotalPeriod() {
		return totalPeriod;
	}

	public void setTotalPeriod(Integer totalPeriod) {
		this.totalPeriod = totalPeriod;
	}
}