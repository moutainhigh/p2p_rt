package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 统计报告
 * @author daicheng
 *
 */
public class Report extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date reportDate;

	private Integer registerCount;

	private Integer vipCount;

	private Integer attestationRealnameCount;

	private Integer attestationTelCount;

	private BigDecimal borrowFee;

	private BigDecimal interestFee;

	private BigDecimal cashFee;

	private BigDecimal vipFee;

	private BigDecimal rechargeFee;

	private Integer overdueRepayedCount;

	private BigDecimal overdueRepayedAmount;

	private Integer newIncreasedCount;

	private BigDecimal newIncreasedAmount;

	private BigDecimal rechargeOnline;

	private BigDecimal rechargeOffline;

	private BigDecimal cash;

	private BigDecimal getIn;

	private BigDecimal interest;

	private BigDecimal tenderReward;

	private BigDecimal rechargeOfflineReward;

	private BigDecimal continueReward;

	private BigDecimal recommendReward;

	private BigDecimal registerReward;

	private Integer publishCount;

	private BigDecimal publishAmount;

	private Integer repayedCount;

	private BigDecimal repayedAmount;

	private Integer tenderCount;

	private BigDecimal tenderAmount;

	private Integer effeTenderCount;

	private BigDecimal effeTenderAmount;
	//提现笔数
	private Integer cashCount;
	//充值笔数
	private Integer rechargeCount;
	
	public Integer getCashCount() {
		return cashCount;
	}

	public void setCashCount(Integer cashCount) {
		this.cashCount = cashCount;
	}

	public Integer getRechargeCount() {
		return rechargeCount;
	}

	public void setRechargeCount(Integer rechargeCount) {
		this.rechargeCount = rechargeCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Integer getRegisterCount() {
		return registerCount;
	}

	public void setRegisterCount(Integer registerCount) {
		this.registerCount = registerCount;
	}

	public Integer getVipCount() {
		return vipCount;
	}

	public void setVipCount(Integer vipCount) {
		this.vipCount = vipCount;
	}

	public Integer getAttestationRealnameCount() {
		return attestationRealnameCount;
	}

	public void setAttestationRealnameCount(Integer attestationRealnameCount) {
		this.attestationRealnameCount = attestationRealnameCount;
	}

	public Integer getAttestationTelCount() {
		return attestationTelCount;
	}

	public void setAttestationTelCount(Integer attestationTelCount) {
		this.attestationTelCount = attestationTelCount;
	}

	public BigDecimal getBorrowFee() {
		return borrowFee;
	}

	public void setBorrowFee(BigDecimal borrowFee) {
		this.borrowFee = borrowFee;
	}

	public BigDecimal getInterestFee() {
		return interestFee;
	}

	public void setInterestFee(BigDecimal interestFee) {
		this.interestFee = interestFee;
	}

	public BigDecimal getCashFee() {
		return cashFee;
	}

	public void setCashFee(BigDecimal cashFee) {
		this.cashFee = cashFee;
	}

	public BigDecimal getVipFee() {
		return vipFee;
	}

	public void setVipFee(BigDecimal vipFee) {
		this.vipFee = vipFee;
	}

	public BigDecimal getRechargeFee() {
		return rechargeFee;
	}

	public void setRechargeFee(BigDecimal rechargeFee) {
		this.rechargeFee = rechargeFee;
	}

	public Integer getOverdueRepayedCount() {
		return overdueRepayedCount;
	}

	public void setOverdueRepayedCount(Integer overdueRepayedCount) {
		this.overdueRepayedCount = overdueRepayedCount;
	}

	public BigDecimal getOverdueRepayedAmount() {
		return overdueRepayedAmount;
	}

	public void setOverdueRepayedAmount(BigDecimal overdueRepayedAmount) {
		this.overdueRepayedAmount = overdueRepayedAmount;
	}

	public Integer getNewIncreasedCount() {
		return newIncreasedCount;
	}

	public void setNewIncreasedCount(Integer newIncreasedCount) {
		this.newIncreasedCount = newIncreasedCount;
	}

	public BigDecimal getNewIncreasedAmount() {
		return newIncreasedAmount;
	}

	public void setNewIncreasedAmount(BigDecimal newIncreasedAmount) {
		this.newIncreasedAmount = newIncreasedAmount;
	}

	public BigDecimal getRechargeOnline() {
		return rechargeOnline;
	}

	public void setRechargeOnline(BigDecimal rechargeOnline) {
		this.rechargeOnline = rechargeOnline;
	}

	public BigDecimal getRechargeOffline() {
		return rechargeOffline;
	}

	public void setRechargeOffline(BigDecimal rechargeOffline) {
		this.rechargeOffline = rechargeOffline;
	}

	public BigDecimal getCash() {
		return cash;
	}

	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}

	public BigDecimal getGetIn() {
		return getIn;
	}

	public void setGetIn(BigDecimal getIn) {
		this.getIn = getIn;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getTenderReward() {
		return tenderReward;
	}

	public void setTenderReward(BigDecimal tenderReward) {
		this.tenderReward = tenderReward;
	}

	public BigDecimal getRechargeOfflineReward() {
		return rechargeOfflineReward;
	}

	public void setRechargeOfflineReward(BigDecimal rechargeOfflineReward) {
		this.rechargeOfflineReward = rechargeOfflineReward;
	}

	public BigDecimal getContinueReward() {
		return continueReward;
	}

	public void setContinueReward(BigDecimal continueReward) {
		this.continueReward = continueReward;
	}

	public BigDecimal getRecommendReward() {
		return recommendReward;
	}

	public void setRecommendReward(BigDecimal recommendReward) {
		this.recommendReward = recommendReward;
	}

	public BigDecimal getRegisterReward() {
		return registerReward;
	}

	public void setRegisterReward(BigDecimal registerReward) {
		this.registerReward = registerReward;
	}

	public Integer getPublishCount() {
		return publishCount;
	}

	public void setPublishCount(Integer publishCount) {
		this.publishCount = publishCount;
	}

	public BigDecimal getPublishAmount() {
		return publishAmount;
	}

	public void setPublishAmount(BigDecimal publishAmount) {
		this.publishAmount = publishAmount;
	}

	public Integer getRepayedCount() {
		return repayedCount;
	}

	public void setRepayedCount(Integer repayedCount) {
		this.repayedCount = repayedCount;
	}

	public BigDecimal getRepayedAmount() {
		return repayedAmount;
	}

	public void setRepayedAmount(BigDecimal repayedAmount) {
		this.repayedAmount = repayedAmount;
	}

	public Integer getTenderCount() {
		return tenderCount;
	}

	public void setTenderCount(Integer tenderCount) {
		this.tenderCount = tenderCount;
	}

	public BigDecimal getTenderAmount() {
		return tenderAmount;
	}

	public void setTenderAmount(BigDecimal tenderAmount) {
		this.tenderAmount = tenderAmount;
	}

	public Integer getEffeTenderCount() {
		return effeTenderCount;
	}

	public void setEffeTenderCount(Integer effeTenderCount) {
		this.effeTenderCount = effeTenderCount;
	}

	public BigDecimal getEffeTenderAmount() {
		return effeTenderAmount;
	}

	public void setEffeTenderAmount(BigDecimal effeTenderAmount) {
		this.effeTenderAmount = effeTenderAmount;
	}
}