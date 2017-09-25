package com.rbao.east.entity;

import java.math.BigDecimal;

public class InvestContinue extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1518507030626927404L;

	private Integer id;

    private Integer userId;

    private BigDecimal userInvestRepayment;

    private BigDecimal userRecharge;

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

    public BigDecimal getUserInvestRepayment() {
        return userInvestRepayment;
    }

    public void setUserInvestRepayment(BigDecimal userInvestRepayment) {
        this.userInvestRepayment = userInvestRepayment;
    }

    public BigDecimal getUserRecharge() {
        return userRecharge;
    }

    public void setUserRecharge(BigDecimal userRecharge) {
        this.userRecharge = userRecharge;
    }
}