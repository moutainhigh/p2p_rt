package com.rbao.east.pay.entity;



public class OnlineRechargeHC{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String  merNo="";//商户号
	private String  billNo; //订单号
	private String  amount;//订单金额
	private String  gatewayUrl;//请求接口地址
	private String  adviceURL;//服务器异步通知路径 
	private String  signInfo;//签名信息
	private String  orderTime;//请求时间
	private String  returnURL;//页面跳转同步通知页面 
	private String  md5src;
	
	private String  remark;
	private String  defaultBankNumber;//银行编码
	public String getMerNo() {
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
	public String getGatewayUrl() {
		return gatewayUrl;
	}
	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}
	public String getAdviceURL() {
		return adviceURL;
	}
	public void setAdviceURL(String adviceURL) {
		this.adviceURL = adviceURL;
	}
	public String getSignInfo() {
		return signInfo;
	}
	public void setSignInfo(String signInfo) {
		this.signInfo = signInfo;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getReturnURL() {
		return returnURL;
	}
	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}
	public String getMd5src() {
		 return merNo +"&"+ billNo +"&"+ amount +"&"+ returnURL;
	}
	public void setMd5src(String md5src) {
		this.md5src = md5src;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDefaultBankNumber() {
		return defaultBankNumber;
	}
	public void setDefaultBankNumber(String defaultBankNumber) {
		this.defaultBankNumber = defaultBankNumber;
	}
	

}
