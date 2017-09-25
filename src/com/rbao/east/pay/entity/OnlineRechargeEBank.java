package com.rbao.east.pay.entity;

import java.io.Serializable;

public class OnlineRechargeEBank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mid="";//商户编号
	private String oid="";//订单编号
	private String amount="";//订单总金额
	private String moneytype="";//币种
	private String url="";//URL地址
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	private String md5info="";//MD5校验码
	
	private String orgSignValue ;//加密明文
	private String gatewayUrl = "";//接口地址
	
	private String remark1 = "";
	private String remark2 = "";
	private String key;
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMoneytype() {
		return moneytype;
	}
	public void setMoneytype(String moneytype) {
		this.moneytype = moneytype;
	}
	public String getUrl() {
		return url;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMd5info() {
		return md5info;
	}
	public void setMd5info(String md5info) {
		this.md5info = md5info;
	}
	public String getOrgSignValue() {
		return amount+moneytype+oid+mid+url+key;
	}
	public void setOrgSignValue(String orgSignValue) {
		this.orgSignValue = orgSignValue;
	}
	public String getGatewayUrl() {
		return gatewayUrl;
	}
	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}


	


}
