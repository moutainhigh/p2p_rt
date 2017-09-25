package com.rbao.east.pay.entity;

import java.io.Serializable;

public class OnlineRechargeHFB implements Serializable{

	private static final long serialVersionUID = -3795694208058562353L;
	private String gatewayUrl="";
	private String version;//版本号1
	private String payType;//支付类型
	private String payCode;//支付类型编码
	private String agentId;//商户编号
	private String agentBillId;//商户系统内部的定单号（要保证唯一
	private String payAmt;//订单总金额
	private String notifyUrl;//支付后返回的商户处理页面，URL参数是以http://或https://开头的完整URL地址(后台处理)
	private String returnUrl;//支付后返回的商户显示页面
	private String userIp;//用户所在客户端的真实ip
	private String agentBillTime;//提交单据的时间yyyyMMddHHmmss
	private String goodsName;//商品名称,
	private String remark;//商户自定义原样返回,
	private String isTest;//是否测试，1=测试
	private String sign;
	@SuppressWarnings("unused")
	private String signStr;//加密文
	public String getGatewayUrl() {
		return gatewayUrl;
	}
	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAgentBillId() {
		return agentBillId;
	}
	public void setAgentBillId(String agentBillId) {
		this.agentBillId = agentBillId;
	}
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
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
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getAgentBillTime() {
		return agentBillTime;
	}
	public void setAgentBillTime(String agentBillTime) {
		this.agentBillTime = agentBillTime;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsTest() {
		return isTest;
	}
	public void setIsTest(String isTest) {
		this.isTest = isTest;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSignStr() {
		return "version="+version+"&agent_id="+agentId+"&agent_bill_id="+agentBillId+
				"&agent_bill_time="+agentBillTime+"&pay_type="+payType+"&pay_amt="+payAmt+
				"&notify_url="+notifyUrl+"&return_url="+returnUrl+"&user_ip="+userIp+"&key=";
	}
	public void setSignStr(String signStr) {
		this.signStr = signStr;
	}
	

}
