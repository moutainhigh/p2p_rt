package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;

public class BorrowTransferAuction extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4848415937633251208L;
	
	
	public static final Integer STATUS_VALID=1;//有效
	public static final Integer STATUS_NULLITY=2;//无效
	

	private Integer id;

    private Integer transferId;

    private Integer userId;

    private Integer status;

    private BigDecimal auctionMoney;

    private Date createTime;

    private BigDecimal curInterestRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getAuctionMoney() {
        return auctionMoney;
    }

    public void setAuctionMoney(BigDecimal auctionMoney) {
        this.auctionMoney = auctionMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getCurInterestRate() {
        return curInterestRate;
    }

    public void setCurInterestRate(BigDecimal curInterestRate) {
        this.curInterestRate = curInterestRate;
    }
}