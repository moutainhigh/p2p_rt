package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.rbao.east.utils.StringUtil;

public class AutotenderRules extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2878666969661241165L;

	private Integer id;

    private Integer userId;

    private Integer rulesStatus;

    private BigDecimal tenderMoney;

    private Integer repayType;

    private Integer periodType;

    private Integer periodBegin;

    private Integer periodEnd;

    private Integer aprStatus;

    private BigDecimal aprMin;

    private BigDecimal aprMax;

    private Integer rewardType;

    private BigDecimal reward;

    private String borrowType;

    private Date updateTime;

    private Date queueTime;

    private Integer tenderTimes;

    private Date lastTenderTime;

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

    public Integer getRulesStatus() {
        return rulesStatus;
    }

    public void setRulesStatus(Integer rulesStatus) {
        this.rulesStatus = rulesStatus;
    }

    public BigDecimal getTenderMoney() {
        return tenderMoney;
    }

    public void setTenderMoney(BigDecimal tenderMoney) {
        this.tenderMoney = tenderMoney;
    }

    public Integer getRepayType() {
        return repayType;
    }

    public void setRepayType(Integer repayType) {
        this.repayType = repayType;
    }

    public Integer getPeriodType() {
        return periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public Integer getPeriodBegin() {
        return periodBegin;
    }

    public void setPeriodBegin(Integer periodBegin) {
        this.periodBegin = periodBegin;
    }

    public Integer getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(Integer periodEnd) {
        this.periodEnd = periodEnd;
    }

    public Integer getAprStatus() {
        return aprStatus;
    }

    public void setAprStatus(Integer aprStatus) {
        this.aprStatus = aprStatus;
    }

    public BigDecimal getAprMin() {
        return aprMin;
    }

    public void setAprMin(BigDecimal aprMin) {
        this.aprMin = aprMin;
    }

    public BigDecimal getAprMax() {
        return aprMax;
    }

    public void setAprMax(BigDecimal aprMax) {
        this.aprMax = aprMax;
    }

    public Integer getRewardType() {
        return rewardType;
    }

    public void setRewardType(Integer rewardType) {
        this.rewardType = rewardType;
    }

    public BigDecimal getReward() {
        return reward;
    }

    public void setReward(BigDecimal reward) {
        this.reward = reward;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
    	 if(!StringUtils.isEmpty(borrowType)){
         	borrowType = StringUtil.removeLastStr(borrowType.trim(), ",");
         }
        this.borrowType = borrowType;
       
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getQueueTime() {
        return queueTime;
    }

    public void setQueueTime(Date queueTime) {
        this.queueTime = queueTime;
    }

    public Integer getTenderTimes() {
        return tenderTimes;
    }

    public void setTenderTimes(Integer tenderTimes) {
        this.tenderTimes = tenderTimes;
    }

    public Date getLastTenderTime() {
        return lastTenderTime;
    }

    public void setLastTenderTime(Date lastTenderTime) {
        this.lastTenderTime = lastTenderTime;
    }
}