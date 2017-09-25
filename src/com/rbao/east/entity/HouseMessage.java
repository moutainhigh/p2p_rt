package com.rbao.east.entity;

import java.util.Date;

public class HouseMessage extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7822472811809288649L;

	private Integer id;

    private Integer userId;

    private String address;

    private String mianji;

    private Date year;

    private String money;

    private String ownership1;

    private String share1;

    private String ownership2;

    private String share2;

    private String loanYear;

    private String monthMoney;

    private String debtMoney;

    private String bank;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getMianji() {
        return mianji;
    }

    public void setMianji(String mianji) {
        this.mianji = mianji == null ? null : mianji.trim();
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money == null ? null : money.trim();
    }

    public String getOwnership1() {
        return ownership1;
    }

    public void setOwnership1(String ownership1) {
        this.ownership1 = ownership1 == null ? null : ownership1.trim();
    }

    public String getShare1() {
        return share1;
    }

    public void setShare1(String share1) {
        this.share1 = share1 == null ? null : share1.trim();
    }

    public String getOwnership2() {
        return ownership2;
    }

    public void setOwnership2(String ownership2) {
        this.ownership2 = ownership2 == null ? null : ownership2.trim();
    }

    public String getShare2() {
        return share2;
    }

    public void setShare2(String share2) {
        this.share2 = share2 == null ? null : share2.trim();
    }

    public String getLoanYear() {
        return loanYear;
    }

    public void setLoanYear(String loanYear) {
        this.loanYear = loanYear == null ? null : loanYear.trim();
    }

    public String getMonthMoney() {
        return monthMoney;
    }

    public void setMonthMoney(String monthMoney) {
        this.monthMoney = monthMoney == null ? null : monthMoney.trim();
    }

    public String getDebtMoney() {
        return debtMoney;
    }

    public void setDebtMoney(String debtMoney) {
        this.debtMoney = debtMoney == null ? null : debtMoney.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }
}