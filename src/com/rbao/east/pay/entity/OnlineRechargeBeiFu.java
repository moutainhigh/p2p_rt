package com.rbao.east.pay.entity;

import java.io.Serializable;

public class OnlineRechargeBeiFu implements Serializable{

	private static final long serialVersionUID = -3795694208058562353L;
	private String gatewayUrl="";
	private String signType = ""; // 签名算法HTTP://MERCHANT.WEB.SITE/MERCHANT_CGI?
	private String sign = "";
	private String service = "";//服务名称：即时交易
	private String partner= "";// 合作者商户ID
	private String inputCharset = ""; // // 字符集
	private String notifyUrl = "";//商户上传的服务器异步通知页面路径
	private String returnUrl = "";// 页面跳转同步通知页面路径
	private String errorNotifyUrl = "";// 请求出错时的通知页面路径，可空
	
	private String outTradeNo = ""; //订单号
	private String subject = "";// 商品名称
	private String exterInvokeIp = "";//订单IP
	private String antiPhishingKey = "";// 通过时间戳查询接口（见AskForTimestampDemo），获取的加密系统时间戳，有效时间：30秒
	private String paymentType ; //支付类型，默认值为：1（商品购买）
	private String sellerId ;// 卖家易八通用户ID
	//private String price = "";// 商品单价
	private String totalFee ="";// 交易金额
	private String buyerId="";
    private String extraCommonParam="";//额外信息
	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}


	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getExterInvokeIp() {
		return exterInvokeIp;
	}

	public void setExterInvokeIp(String exterInvokeIp) {
		this.exterInvokeIp = exterInvokeIp;
	}

	public String getAntiPhishingKey() {
		return antiPhishingKey;
	}

	public void setAntiPhishingKey(String antiPhishingKey) {
		this.antiPhishingKey = antiPhishingKey;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getGatewayUrl() {
		return gatewayUrl;
	}

	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getErrorNotifyUrl() {
		return errorNotifyUrl;
	}

	public void setErrorNotifyUrl(String errorNotifyUrl) {
		this.errorNotifyUrl = errorNotifyUrl;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getExtraCommonParam() {
		return extraCommonParam;
	}

	public void setExtraCommonParam(String extraCommonParam) {
		this.extraCommonParam = extraCommonParam;
	}
	

}
