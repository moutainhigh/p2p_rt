package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 资金统计
 * */
public class AccountStatistics extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Date statisticsAddtime;//统计时间

	private Integer userId;

	private String userName;

	private String userRealname;

	private BigDecimal allMoney;

	private BigDecimal availableMoney;

	private BigDecimal unavailableMoney;

	private BigDecimal waitRepossessedCapital;//待收本金

	private BigDecimal waitRepayCapital;//待还本金

	private BigDecimal waitRepossessedInterest;

	private BigDecimal waitRepayInterest;

	private BigDecimal payReward;//支出奖励

	private BigDecimal payInterest;//支出利息

	private BigDecimal getReward;//获得奖励

	private BigDecimal getInterest;//获得利息

	private BigDecimal worthMoney;//净资产

	private BigDecimal tenderAmount;//投标额

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStatisticsAddtime() {
		return statisticsAddtime;
	}

	public void setStatisticsAddtime(Date statisticsAddtime) {
		this.statisticsAddtime = statisticsAddtime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getUserRealname() {
		return userRealname;
	}

	public void setUserRealname(String userRealname) {
		this.userRealname = userRealname == null ? null : userRealname.trim();
	}

	public BigDecimal getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(BigDecimal allMoney) {
		this.allMoney = allMoney;
	}

	public BigDecimal getAvailableMoney() {
		return availableMoney;
	}

	public void setAvailableMoney(BigDecimal availableMoney) {
		this.availableMoney = availableMoney;
	}

	public BigDecimal getUnavailableMoney() {
		return unavailableMoney;
	}

	public void setUnavailableMoney(BigDecimal unavailableMoney) {
		this.unavailableMoney = unavailableMoney;
	}

	public BigDecimal getWaitRepossessedCapital() {
		return waitRepossessedCapital;
	}

	public void setWaitRepossessedCapital(BigDecimal waitRepossessedCapital) {
		this.waitRepossessedCapital = waitRepossessedCapital;
	}

	public BigDecimal getWaitRepayCapital() {
		return waitRepayCapital;
	}

	public void setWaitRepayCapital(BigDecimal waitRepayCapital) {
		this.waitRepayCapital = waitRepayCapital;
	}

	public BigDecimal getWaitRepossessedInterest() {
		return waitRepossessedInterest;
	}

	public void setWaitRepossessedInterest(BigDecimal waitRepossessedInterest) {
		this.waitRepossessedInterest = waitRepossessedInterest;
	}

	public BigDecimal getWaitRepayInterest() {
		return waitRepayInterest;
	}

	public void setWaitRepayInterest(BigDecimal waitRepayInterest) {
		this.waitRepayInterest = waitRepayInterest;
	}

	public BigDecimal getPayReward() {
		return payReward;
	}

	public void setPayReward(BigDecimal payReward) {
		this.payReward = payReward;
	}

	public BigDecimal getPayInterest() {
		return payInterest;
	}

	public void setPayInterest(BigDecimal payInterest) {
		this.payInterest = payInterest;
	}

	public BigDecimal getGetReward() {
		return getReward;
	}

	public void setGetReward(BigDecimal getReward) {
		this.getReward = getReward;
	}

	public BigDecimal getGetInterest() {
		return getInterest;
	}

	public void setGetInterest(BigDecimal getInterest) {
		this.getInterest = getInterest;
	}

	public BigDecimal getWorthMoney() {
		return worthMoney;
	}

	public void setWorthMoney(BigDecimal worthMoney) {
		this.worthMoney = worthMoney;
	}

	public BigDecimal getTenderAmount() {
		return tenderAmount;
	}

	public void setTenderAmount(BigDecimal tenderAmount) {
		this.tenderAmount = tenderAmount;
	}
}