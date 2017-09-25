package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;

public class RechargeCash extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6057135544117707074L;

	private Integer id;

    private Integer userId;

    private Integer rechargeId;

    private Integer cashId;

    private BigDecimal cashFeeAmount;

    private Date createTime;

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

    public Integer getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(Integer rechargeId) {
        this.rechargeId = rechargeId;
    }

    public Integer getCashId() {
        return cashId;
    }

    public void setCashId(Integer cashId) {
        this.cashId = cashId;
    }

    public BigDecimal getCashFeeAmount() {
        return cashFeeAmount;
    }

    public void setCashFeeAmount(BigDecimal cashFeeAmount) {
        this.cashFeeAmount = cashFeeAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}