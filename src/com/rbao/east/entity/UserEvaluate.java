package com.rbao.east.entity;

import java.math.BigDecimal;

public class UserEvaluate extends BaseEntity{
	
	private static final long serialVersionUID = 5658700765009362721L;
	
    private Integer id;

    private Integer userId;

    private BigDecimal credit;

    private BigDecimal creditAvailable;

    private BigDecimal creditUnavailable;

    private BigDecimal borrowVouch;

    private BigDecimal borrowVouchAvailable;

    private BigDecimal borrowVouchUnavailable;

    private BigDecimal tenderVouch;

    private BigDecimal tenderVouchAvailable;

    private BigDecimal tenderVouchUnavailable;

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

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getCreditAvailable() {
        return creditAvailable;
    }

    public void setCreditAvailable(BigDecimal creditAvailable) {
        this.creditAvailable = creditAvailable;
    }

    public BigDecimal getCreditUnavailable() {
        return creditUnavailable;
    }

    public void setCreditUnavailable(BigDecimal creditUnavailable) {
        this.creditUnavailable = creditUnavailable;
    }

    public BigDecimal getBorrowVouch() {
        return borrowVouch;
    }

    public void setBorrowVouch(BigDecimal borrowVouch) {
        this.borrowVouch = borrowVouch;
    }

    public BigDecimal getBorrowVouchAvailable() {
        return borrowVouchAvailable;
    }

    public void setBorrowVouchAvailable(BigDecimal borrowVouchAvailable) {
        this.borrowVouchAvailable = borrowVouchAvailable;
    }

    public BigDecimal getBorrowVouchUnavailable() {
        return borrowVouchUnavailable;
    }

    public void setBorrowVouchUnavailable(BigDecimal borrowVouchUnavailable) {
        this.borrowVouchUnavailable = borrowVouchUnavailable;
    }

    public BigDecimal getTenderVouch() {
        return tenderVouch;
    }

    public void setTenderVouch(BigDecimal tenderVouch) {
        this.tenderVouch = tenderVouch;
    }

    public BigDecimal getTenderVouchAvailable() {
        return tenderVouchAvailable;
    }

    public void setTenderVouchAvailable(BigDecimal tenderVouchAvailable) {
        this.tenderVouchAvailable = tenderVouchAvailable;
    }

    public BigDecimal getTenderVouchUnavailable() {
        return tenderVouchUnavailable;
    }

    public void setTenderVouchUnavailable(BigDecimal tenderVouchUnavailable) {
        this.tenderVouchUnavailable = tenderVouchUnavailable;
    }
}