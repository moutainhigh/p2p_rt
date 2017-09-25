package com.rbao.east.pay.entity;

import java.io.Serializable;

/**
 * 丰付支付的实体类 属性包含，丰付的跳转传值信息 gyq20150113
 * 
 * @author user
 * 
 */
public class OnlineRechargeFengFu implements Serializable {

	private static final long serialVersionUID = 1L;

	private String requestId = ""; // 请求流水编号--（这应该是一个动态的流水号，那么这个编号是怎样生成的）
	private String tradeProcess=""; // 外部系统标识--外部系统在支付系统开户后生产的商户编号代码。
	private String totalBizType=""; // 业务类型--支付系统支持的业务类型,如：在线订购：BIZ00800
	private String totalPrice=""; // 交易总价格--本次支付中所有产品实际需支付的金额，单位元，精确到分，不可以为负值，必须符合金额标准。

	private String backurl=""; // 成功跳转的URL地址--支付成功后跳转回的页面链接
	private String returnurl=""; // 不执行支付返回的URL地址 --用户不进行支付，返回商户系统时跳转的页面链接
	private String noticeurl=""; // 支付成功后后台通知地址--后台通知商户结果地址，通知方式为httpclient。如果不传此参数，则不会后台通知。通知时以http响应码为准，当收到200后不再重复发送通知。
	private String description=""; // 辅助信息--选输
	private String mersignature=""; // 数字签名--商户在支付系统开户后，支付系统会下发一个密钥给商户，商户上送数据时，需要把订单信息根据密钥进行加密后生产此签名，支付系统根据此签名校验信息是否合法。
	private String goodsDesc=""; // 商品描述--选输
	private String allowRePay=""; // 是否可以重新支付--选输，默认为1;0：不允许；1：允许;
	private String rePayTimeOut=""; // 重新支付有效期--选输,有效期，以天为单位，默认为1天
	private String userIdIdentity=""; // 商户用户唯一标识--选输,商户用户的唯一标识，在快捷支付中，如果商户期望支付系统保存商户用户的支付
									// 信息，则可传递该参数，支付系统把该唯一标识与用户输入的支付信息进行保存，用户只需在第一次支付时输入支付信息，方便用户进行支付
	private String bankCardType=""; // 网银支付借贷分离标记--选输,不输入或0：不区分借贷,1：借记卡,2：贷记卡;
	private String payTag=""; // 支付方式标签显示--选输
							// 不输入或0：收银台显示商户配置的所有支付方式
							// 1：收银台只显示个人网银支付
							// 2：收银台只显示快捷贷记卡支付
							// 3：收银台只显示企业网银支付
							// 4: 收银台只显示银联支付
							// 5: 收银台只显示快捷借记卡支付

	private String gatewayUrl=""; // 支付跳转的url
	private String md5key="";
	private String md5Sign = "";// md5签名
	private String md5info = "";// md5内容
	// 封装的产品信息，必填项
	private String productId="";
	private String productName="";
	private String fund="";
	private String merAcct="";
	private String bizType="";
	private String productNumber="";

	public String getGatewayUrl() {
		return gatewayUrl;
	}

	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getTradeProcess() {
		return tradeProcess;
	}

	public void setTradeProcess(String tradeProcess) {
		this.tradeProcess = tradeProcess;
	}

	public String getTotalBizType() {
		return totalBizType;
	}

	public void setTotalBizType(String totalBizType) {
		this.totalBizType = totalBizType;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getBackurl() {
		return backurl;
	}

	public void setBackurl(String backurl) {
		this.backurl = backurl;
	}

	public String getReturnurl() {
		return returnurl;
	}

	public void setReturnurl(String returnurl) {
		this.returnurl = returnurl;
	}

	public String getNoticeurl() {
		return noticeurl;
	}

	public void setNoticeurl(String noticeurl) {
		this.noticeurl = noticeurl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMersignature() {
		return mersignature;
	}

	public void setMersignature(String mersignature) {
		this.mersignature = mersignature;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public String getAllowRePay() {
		return allowRePay;
	}

	public void setAllowRePay(String allowRePay) {
		this.allowRePay = allowRePay;
	}

	public String getRePayTimeOut() {
		return rePayTimeOut;
	}

	public void setRePayTimeOut(String rePayTimeOut) {
		this.rePayTimeOut = rePayTimeOut;
	}

	public String getUserIdIdentity() {
		return userIdIdentity;
	}

	public void setUserIdIdentity(String userIdIdentity) {
		this.userIdIdentity = userIdIdentity;
	}

	public String getBankCardType() {
		return bankCardType;
	}

	public void setBankCardType(String bankCardType) {
		this.bankCardType = bankCardType;
	}

	public String getPayTag() {
		return payTag;
	}

	public void setPayTag(String payTag) {
		this.payTag = payTag;
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

	public String getMd5Sign() {
		return requestId + tradeProcess + totalBizType + totalPrice + backurl
				+ returnurl + noticeurl + description;
	}

	public void setMd5Sign(String md5Sign) {
		this.md5Sign = md5Sign;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getFund() {
		return fund;
	}

	public void setFund(String fund) {
		this.fund = fund;
	}

	public String getMerAcct() {
		return merAcct;
	}

	public void setMerAcct(String merAcct) {
		this.merAcct = merAcct;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	@Override
	public String toString() {
		return "OnlineRechargeFF [requestId=" + requestId + ", tradeProcess="
				+ tradeProcess + ", totalBizType=" + totalBizType
				+ ", totalPrice=" + totalPrice + ", backurl=" + backurl
				+ ", returnurl=" + returnurl + ", noticeurl=" + noticeurl
				+ ", description=" + description + ", mersignature="
				+ mersignature + ", goodsDesc=" + goodsDesc + ", allowRePay="
				+ allowRePay + ", rePayTimeOut=" + rePayTimeOut
				+ ", userIdIdentity=" + userIdIdentity + ", bankCardType="
				+ bankCardType + ", payTag=" + payTag + ", gatewayUrl="
				+ gatewayUrl + ", md5key=" + md5key + ", md5Sign=" + md5Sign
				+ ", md5info=" + md5info + ", productId=" + productId
				+ ", productName=" + productName + ", fund=" + fund
				+ ", merAcct=" + merAcct + ", bizType=" + bizType
				+ ", productNumber=" + productNumber + "]";
	}

}
