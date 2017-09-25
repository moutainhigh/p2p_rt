package com.rbao.east.entity;

import java.math.BigDecimal;
/**
 * 用户支付
 * */
public class AccountPayment extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3721674275466180611L;
	
	public static final Integer PAYSTATUS_ADD = 1;//增加
	public static final Integer PAYSTATUS_SUBTRACT = 2;//减掉

	private Integer id;

    private String payTitle;

    private Integer payStatus;//支付状态

    private BigDecimal payFee;//支付费用

    private Integer payType;//支付类型

    private BigDecimal maxMoney;//最大金额

    private BigDecimal maxFee;//最大费用

    private String payDescription;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayTitle() {
        return payTitle;
    }

    public void setPayTitle(String payTitle) {
        this.payTitle = payTitle == null ? null : payTitle.trim();
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public BigDecimal getPayFee() {
        return payFee;
    }

    public void setPayFee(BigDecimal payFee) {
        this.payFee = payFee;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public BigDecimal getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(BigDecimal maxMoney) {
        this.maxMoney = maxMoney;
    }

    public BigDecimal getMaxFee() {
        return maxFee;
    }

    public void setMaxFee(BigDecimal maxFee) {
        this.maxFee = maxFee;
    }

    public String getPayDescription() {
        return payDescription;
    }

    public void setPayDescription(String payDescription) {
        this.payDescription = payDescription == null ? null : payDescription.trim();
    }
}