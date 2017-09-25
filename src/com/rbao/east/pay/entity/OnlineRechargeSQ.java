package com.rbao.east.pay.entity;

import java.io.Serializable;

public class OnlineRechargeSQ implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  String merNo="";//编码
	private  String billNo="";//订单编码
    private  String amount="";//订单金额
    private  String paymentType="";//银行代码
    private  String payType="";
    private  String returnURL="";//客户支付返回商户前台网站URL.
    private  String notifyURL="";//服务端后台支付状态通知. 
    private  String merRemark = ""; 
    private  String gatewayUrl;
    private  String md5key ;
	public String getMerRemark() {
		return merRemark;
	}
	public void setMerRemark(String merRemark) {
		this.merRemark = merRemark;
	}
	private  String md5info ="";
    private  String[] orgSignValue;//加密明文
	public   String getMerNo() {
		return merNo;
	}
	public void setMerNo(String merNo) {
		this.merNo = merNo;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getReturnURL() {
		return returnURL;
	}
	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}
	public String getNotifyURL() {
		return notifyURL;
	}
	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}
	
	public String getGatewayUrl() {
		return gatewayUrl;
	}
	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}
	public String getMd5key() {
		return md5key;
	}
	public void setMd5key(String md5key) {
		this.md5key = md5key;
	}
	public String getMd5info() {
		return md5info;
	}
	public void setMd5info(String md5info) {
		this.md5info = md5info;
	}
	public String[] getOrgSignValue() {
		return new String[]{merNo,billNo,amount,returnURL};
	}
	public void setOrgSignValue(String[] orgSignValue) {
		this.orgSignValue = orgSignValue;
	}
	
	
   
}