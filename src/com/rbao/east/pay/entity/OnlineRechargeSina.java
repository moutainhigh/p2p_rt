package com.rbao.east.pay.entity;

import java.io.Serializable;

public class OnlineRechargeSina implements Serializable{

	private static final long serialVersionUID = -3795694208058562353L;
	private String gatewayUrl="";
	private String inputCharset = "1"; // // 字符集
	private String bgUrl = "";//商户上传的服务器异步通知页面路径
	private String version="v2.3";
	private String language="1";
	private String signType="1";
	
	private String merchantAcctId="";//人民币账号
	
	private String orderId="";
	private String orderAmount="";//分单位
	private String orderTime="";//20071117020101
	private String productName="";//可空
	private String pid="";//合作伙伴在新浪支付的用户编号 数字串。商户的memberId。
	private String signStr="";
	private String signMsg="";//以上所有非空参数及其值与密钥组合，经加密后生成。MD5转化为小写的32位字符串；DSA和RSA加密后是直接证书加密后的密文
	public String getGatewayUrl() {
		return gatewayUrl;
	}
	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}
	public String getInputCharset() {
		return inputCharset;
	}
	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}
	public String getBgUrl() {
		return bgUrl;
	}
	public void setBgUrl(String bgUrl) {
		this.bgUrl = bgUrl;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getLanguage() {
		return language;
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
	public String getMerchantAcctId() {
		return merchantAcctId;
	}
	public void setMerchantAcctId(String merchantAcctId) {
		this.merchantAcctId = merchantAcctId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getSignMsg() {
		return signMsg;
	}
	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}
	public String getSignStr() {
		return "inputCharset="+inputCharset+
				"&bgUrl="+bgUrl+"&version="+version+
				"&language="+language+"&signType="+signType+
				"&merchantAcctId="+merchantAcctId+
				"&orderId="+orderId+"&orderAmount="+orderAmount+
				"&orderTime="+orderTime+"&pid="+pid+"";
	}
	public void setSignStr(String signStr) {
		this.signStr = signStr;
	}
	
	

}
