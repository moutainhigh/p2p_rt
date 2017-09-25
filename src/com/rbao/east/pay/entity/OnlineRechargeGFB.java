package com.rbao.east.pay.entity;

import java.io.Serializable;

public class OnlineRechargeGFB implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3795694208058562353L;
	
	private String merchantID = ""; //商户代码
	private String virCardNoIn = "";//国付宝转入账户
	private String gatewayUrl = "";
	private String VerficationCode= "";
		
	private String merOrderNum = ""; //订单号
	private String tranAmt = "";//交易金额
	private String feeAmt = "";//平台收的佣金，可空
	private String tranDateTime = "";//交易时间
	private String tranIP = ""; //客户端IP
	private String gopayServerTime = "";//服务器时间
	private String goodsName = "";//商品名称
	private String goodsDetail = "";//商品详情	
	private String buyerName ; //买家姓名
	private String buyerContact ;//联系方式
	private String orderId = "";//国付宝内部订单号
	
	private String respCode ="";//返回码
	
	
	private String orgSignValue ;//加密明文	
	private String signValue ;//密文	

	private String frontMerUrl = "";//前台通知地址
	private String backgroundMerUrl = ""; //后台通知地址
	
	private String bankCode = "";
	private String userType = "";//用户类型	
    private String gopayOutOrderId="";
	//如下付款时不变
	private String version = "2.1" ; // 网关版本号，必须2.1	
	private String charset = "2"; //编码2UTF-8
	private String language = "1" ;//网关语言 1 中文 2 英文
	private String signType = "1"; //报文加密方式 1 MD5 2 SHA ，默认1，可空
	private String tranCode = "8888";//交易代码
	private String currencyType = "156";//币种 
	private String isRepeatSubmit = "0";//是否允许重复提交 0 不允许重复 1 允许重复
	private String merRemark1 = "";//备用1
	private String merRemark2 = "";

	public String getMerchantID() {
		return merchantID;
	}
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}
	public String getVirCardNoIn() {
		return virCardNoIn;
	}
	public void setVirCardNoIn(String virCardNoIn) {
		this.virCardNoIn = virCardNoIn;
	}
	public String getMerOrderNum() {
		return merOrderNum;
	}
	public void setMerOrderNum(String merOrderNum) {
		this.merOrderNum = merOrderNum;
	}
	public String getTranAmt() {
		return tranAmt;
	}
	public void setTranAmt(String tranAmt) {
		this.tranAmt = tranAmt;
	}
	public String getVerficationCode() {
		return VerficationCode;
	}
	public void setVerficationCode(String verficationCode) {
		VerficationCode = verficationCode;
	}
	public String getFeeAmt() {
		return feeAmt;
	}
	public void setFeeAmt(String feeAmt) {
		this.feeAmt = feeAmt;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrgSignValue() { //构造字符串用于加密
		return   "version=[" + version + 
				"]tranCode=[" + tranCode + 
				"]merchantID=[" + merchantID + 
				"]merOrderNum=[" + merOrderNum + 
				"]tranAmt=[" + tranAmt + 
				"]feeAmt=[" + feeAmt+ 
				"]tranDateTime=[" + tranDateTime + 
				"]frontMerUrl=[" + frontMerUrl + 
				"]backgroundMerUrl=[" + backgroundMerUrl + 
				"]orderId=["+orderId+"]gopayOutOrderId=["+gopayOutOrderId+"]tranIP=[" + tranIP + 
				"]respCode=["+respCode+"]gopayServerTime=[" + gopayServerTime + 
				"]VerficationCode=[" + VerficationCode + "]";
	}
	public void setOrgSignValue(String orgSignValue) {
		this.orgSignValue = orgSignValue;
	}
	public String getTranDateTime() {
		return tranDateTime;
	}
	public void setTranDateTime(String tranDateTime) {
		this.tranDateTime = tranDateTime;
	}
	public String getTranIP() {
		return tranIP;
	}
	public void setTranIP(String tranIP) {
		this.tranIP = tranIP;
	}
	public String getGopayServerTime() {
		return gopayServerTime;
	}
	public void setGopayServerTime(String gopayServerTime) {
		this.gopayServerTime = gopayServerTime;
	}

	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsDetail() {
		return goodsDetail;
	}
	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}
	public String getBuyerContact() {
		return buyerContact;
	}
	public void setBuyerContact(String buyerContact) {
		this.buyerContact = buyerContact;
	}
	public String getSignValue() {
		return signValue;
	}
	public void setSignValue(String signValue) {
		this.signValue = signValue;
	}
	public String getFrontMerUrl() {
		return frontMerUrl;
	}
	public void setFrontMerUrl(String frontMerUrl) {
		this.frontMerUrl = frontMerUrl;
	}
	public String getBackgroundMerUrl() {
		return backgroundMerUrl;
	}
	public void setBackgroundMerUrl(String backgroundMerUrl) {
		this.backgroundMerUrl = backgroundMerUrl;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getLanguage() {
		return language;
	}

	public String getGatewayUrl() {
		return gatewayUrl;
	}
	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getIsRepeatSubmit() {
		return isRepeatSubmit;
	}
	public void setIsRepeatSubmit(String isRepeatSubmit) {
		this.isRepeatSubmit = isRepeatSubmit;
	}
	public String getMerRemark1() {
		return merRemark1;
	}
	public void setMerRemark1(String merRemark1) {
		this.merRemark1 = merRemark1;
	}
	public String getMerRemark2() {
		return merRemark2;
	}
	public void setMerRemark2(String merRemark2) {
		this.merRemark2 = merRemark2;
	}
	public String getGopayOutOrderId() {
		return gopayOutOrderId;
	}
	public void setGopayOutOrderId(String gopayOutOrderId) {
		this.gopayOutOrderId = gopayOutOrderId;
	}

}
