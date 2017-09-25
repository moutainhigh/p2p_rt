package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BorrowRepayment extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5177373371028503571L;
	
	public static final Integer REPAYMENT_STATUS_REPAYING=1;
	public static final Integer REPAYMENT_STATUS_FAILD=4;
	public static final Integer REPAYMENT_STATUS_SUCCESS=2;
	public static final Integer REPAYMENT_STATUS_OVERDUE=3;
	public static final Integer REPAYMENT_STATUS_REDEEMED=5;

	public static final Map<Integer, String> ALL_REPAYMENT_STATUS = new HashMap<Integer, String>();
	
	static{
		ALL_REPAYMENT_STATUS.put(REPAYMENT_STATUS_REPAYING, "还款中");
		ALL_REPAYMENT_STATUS.put(REPAYMENT_STATUS_SUCCESS, "还款成功");
		ALL_REPAYMENT_STATUS.put(REPAYMENT_STATUS_OVERDUE, "逾期");
		ALL_REPAYMENT_STATUS.put(REPAYMENT_STATUS_REDEEMED, "已赎回");
	}

	private Integer id;

    private Integer repaymentStatus;

    private Integer webStatus;

    private Integer repaymentSequence;

    private Integer borrowId;

    private Date repaymentTime;

    private Date repaymentPaidtime;

    private BigDecimal repaymentAmount;

    private BigDecimal repaymentRealamount;

    private Integer lateDays;
    
    private Integer currentPeriod;
    
    private Integer totalPeriod;

    private BigDecimal lateInterest;

    private BigDecimal repaymentInterest;

    private BigDecimal repaymentPrincipal;

    private BigDecimal overdueFee;

    private BigDecimal reminderFee;

    private Integer repaymentAddtime;

    private String repaymentAddip;
    
    private Integer repayedUserId ;
    
    private String remark ;
    
    private String viewRepaymentStatus;

    private List<BorrowRepossessed> borrowRepossessedlist;
    
private List<BorrowRepossessed> borrowOverdueRepossessedlist;
    
    
    public List<BorrowRepossessed> getBorrowOverdueRepossessedlist() {
		return borrowOverdueRepossessedlist;
	}

	public void setBorrowOverdueRepossessedlist(
			List<BorrowRepossessed> borrowOverdueRepossessedlist) {
		this.borrowOverdueRepossessedlist = borrowOverdueRepossessedlist;
	}
    
    
    
    public List<BorrowRepossessed> getBorrowRepossessedlist() {
		return borrowRepossessedlist;
	}

	public void setBorrowRepossessedlist(
			List<BorrowRepossessed> borrowRepossessedlist) {
		this.borrowRepossessedlist = borrowRepossessedlist;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRepaymentStatus() {
        return repaymentStatus;
    }

    public void setRepaymentStatus(Integer repaymentStatus) {
        this.repaymentStatus = repaymentStatus;
        setViewRepaymentStatus(ALL_REPAYMENT_STATUS.get(repaymentStatus));
    }

    public Integer getWebStatus() {
        return webStatus;
    }

    public void setWebStatus(Integer webStatus) {
        this.webStatus = webStatus;
    }

    public Integer getRepaymentSequence() {
        return repaymentSequence;
    }

    public void setRepaymentSequence(Integer repaymentSequence) {
        this.repaymentSequence = repaymentSequence;
    }

    public Integer getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Integer borrowId) {
        this.borrowId = borrowId;
    }

    public Date getRepaymentTime() {
        return repaymentTime;
    }

    public void setRepaymentTime(Date repaymentTime) {
        this.repaymentTime = repaymentTime;
    }

    public Date getRepaymentPaidtime() {
        return repaymentPaidtime;
    }

    public void setRepaymentPaidtime(Date repaymentPaidtime) {
        this.repaymentPaidtime = repaymentPaidtime;
    }

    public BigDecimal getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(BigDecimal repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public BigDecimal getRepaymentRealamount() {
        return repaymentRealamount;
    }

    public void setRepaymentRealamount(BigDecimal repaymentRealamount) {
        this.repaymentRealamount = repaymentRealamount;
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
        this.lateInterest = lateInterest;
    }

    public BigDecimal getRepaymentInterest() {
        return repaymentInterest;
    }

    public void setRepaymentInterest(BigDecimal repaymentInterest) {
        this.repaymentInterest = repaymentInterest;
    }

    public BigDecimal getRepaymentPrincipal() {
        return repaymentPrincipal;
    }

    public void setRepaymentPrincipal(BigDecimal repaymentPrincipal) {
        this.repaymentPrincipal = repaymentPrincipal;
    }

    public BigDecimal getOverdueFee() {
        return overdueFee;
    }

    public void setOverdueFee(BigDecimal overdueFee) {
        this.overdueFee = overdueFee;
    }

    public BigDecimal getReminderFee() {
        return reminderFee;
    }

    public void setReminderFee(BigDecimal reminderFee) {
        this.reminderFee = reminderFee;
    }

    public Integer getRepaymentAddtime() {
        return repaymentAddtime;
    }

    public void setRepaymentAddtime(Integer repaymentAddtime) {
        this.repaymentAddtime = repaymentAddtime;
    }

    public String getRepaymentAddip() {
        return repaymentAddip;
    }

    public void setRepaymentAddip(String repaymentAddip) {
        this.repaymentAddip = repaymentAddip == null ? null : repaymentAddip.trim();
    }

	public String getViewRepaymentStatus() {
		return viewRepaymentStatus;
	}

	public void setViewRepaymentStatus(String viewRepaymentStatus) {
		this.viewRepaymentStatus = viewRepaymentStatus;
	}

	public Integer getRepayedUserId() {
		return repayedUserId;
	}

	public void setRepayedUserId(Integer repayedUserId) {
		this.repayedUserId = repayedUserId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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