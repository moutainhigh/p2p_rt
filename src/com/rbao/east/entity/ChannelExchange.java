package com.rbao.east.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 渠道数据交互
 * @author hjy
 * 2016-01-19 10:01:56
 *
 */
public class ChannelExchange extends BaseEntity{
	private String userTel;		//用户手机号
	private Date userAddtime;	//用户注册时间
	private BigDecimal tenderAmount;	//投资总额
	private Date tenderAddtime;		//投资时间
	
	public String getUserTel() {
		return userTel;
	}
	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	public Date getUserAddtime() {
		return userAddtime;
	}
	public void setUserAddtime(Date userAddtime) {
		this.userAddtime = userAddtime;
	}
	public BigDecimal getTenderAmount() {
		return tenderAmount;
	}
	public void setTenderAmount(BigDecimal tenderAmount) {
		this.tenderAmount = tenderAmount;
	}
	public Date getTenderAddtime() {
		return tenderAddtime;
	}
	public void setTenderAddtime(Date tenderAddtime) {
		this.tenderAddtime = tenderAddtime;
	}
	
}
