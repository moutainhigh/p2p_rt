package com.rbao.east.pay.entity;

import java.io.Serializable;

public class OnlineRechargeBF implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String  memberID="";//商户号
	private String  tradeDate; //交易时间
	private String  transID;//商户流水号
	private String  orderMoney;//订单金额
	private String  username;//支付用户名
	private String  additionalInfo;//订单附加消息
	private String  gatewayUrl;
	private String  pageUrl;//页面跳转地址
	private String  returnUrl;//底层访问地址
	private String  noticeType;//通知方式
	private String  payID="";//支付方式
	private String  terminalID="";//终端号
	private String  md5key;
	private String  md5Sign="";//md5签名
	private String  md5info="";//md5内容
	private String  payUrl="";//借贷混合
	private String  interfaceVersion;//接口版本号
	private String  keyType;//加密方式
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public String getTransID() {
		return transID;
	}
	public void setTransID(String transID) {
		this.transID = transID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getPayID() {
		return payID;
	}
	public void setPayID(String payID) {
		this.payID = payID;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	
	public String getMd5Sign() {
		return  memberID+"|"+payID+"|"+tradeDate+"|"+
				transID+"|"+orderMoney+"|"+pageUrl+"|"+returnUrl+"|"
				+noticeType+"|"+md5key;
	}
	public void setMd5Sign(String md5Sign) {
		this.md5Sign = md5Sign;
	}
	public String getMd5info() {
		return md5info;
	}
	public void setMd5info(String md5info) {
		this.md5info = md5info;
	}
	public String getGatewayUrl() {
		return gatewayUrl;
	}
	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	public String getInterfaceVersion() {
		return interfaceVersion;
	}
	public void setInterfaceVersion(String interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}
	public String getKeyType() {
		return keyType;
	}
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
	public String getTerminalID() {
		return terminalID;
	}
	public void setTerminalID(String terminalID) {
		this.terminalID = terminalID;
	}
	public String getMd5key() {
		return md5key;
	}
	public void setMd5key(String md5key) {
		this.md5key = md5key;
	}
	public String getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}
	

}
