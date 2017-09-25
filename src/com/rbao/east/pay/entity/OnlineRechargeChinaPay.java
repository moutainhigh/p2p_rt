package com.rbao.east.pay.entity;

import java.io.Serializable;

public class OnlineRechargeChinaPay implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5875885993457672540L;
	private String merId;//商户号
	private String ordId;//订单号
	private String transAmt;//交易金额   数字串"000000001234"表示12.34元。
	private String curyId;//货币代码   人民币，取值为"156"
	private String transDate;//交易日期  YYYYMMDD
	private String transType;//交易类型   "0001"消费交易和"0002"货交易
	private String orderStatus;//交易状态 
	private String checkValue; //  长度为256
	private String bgRetUrl;//后台应答接收URL
	private String pageRetUrl;//页面应答接收URL
	private String gateId;//支付网关号
	private String priv1;//商户私有域
	private String version;//版本号
	private String chkValue;
	private String gatewayUrl;
	
	
	private String ClientIP;
	
	private String transTime;
	private String countryId;
	private String timeZone;
	private String dSTFlag;
	private String extFlag;	
	private String priv2;
	
	public String getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	public String getCuryId() {
		return curyId;
	}
	public void setCuryId(String curyId) {
		this.curyId = curyId;
	}
	
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCheckValue() {
		return checkValue;
	}
	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}
	public String getBgRetUrl() {
		return bgRetUrl;
	}
	public void setBgRetUrl(String bgRetUrl) {
		this.bgRetUrl = bgRetUrl;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPageRetUrl() {
		return pageRetUrl;
	}
	public void setPageRetUrl(String pageRetUrl) {
		this.pageRetUrl = pageRetUrl;
	}
	public String getOrdId() {
		return ordId;
	}
	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}
	public String getMerId() {
		return merId;
	}
	public void setMerId(String merId) {
		this.merId = merId;
	}
	public String getGatewayUrl() {
		return gatewayUrl;
	}
	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}
	public String getChkValue() {
		return chkValue;
	}
	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}
	public String getPriv1() {
		return priv1;
	}
	public void setPriv1(String priv1) {
		this.priv1 = priv1;
	}
	public String getTransDate() {
		return transDate;
	}
	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}
	public String getGateId() {
		return gateId;
	}
	public void setGateId(String gateId) {
		this.gateId = gateId;
	}
	public String getClientIP() {
		return ClientIP;
	}
	public void setClientIP(String clientIP) {
		ClientIP = clientIP;
	}
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getTimeZone() {
		return timeZone;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getdSTFlag() {
		return dSTFlag;
	}
	public void setdSTFlag(String dSTFlag) {
		this.dSTFlag = dSTFlag;
	}
	public String getExtFlag() {
		return extFlag;
	}
	public void setExtFlag(String extFlag) {
		this.extFlag = extFlag;
	}
	public String getPriv2() {
		return priv2;
	}
	public void setPriv2(String priv2) {
		this.priv2 = priv2;
	}
	

}
