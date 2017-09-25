package com.rbao.east.pay.entity;

import java.io.Serializable;

public class OnlineRechargeXinSheng implements Serializable {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	
	private String version=""; // 版本2.6
	private String serialID=""; // 请求流水号
	private String submitTime=""; // 订单提交时间
	private String failureTime=""; //订单失效时间
	private String customerIP=""; // 客户下单域名及IP
	private String orderDetails=""; //订单明细信息
	private String totalAmount=""; //订单总金额
	private String type=""; //交易类型
	private String buyerMarked=""; //付款方新生账户号
	private String payType=""; //付款方式
	private String orgCode=""; //目标资金机构代码
	private String currencyCode=""; //交易币种
	private String directFlag=""; //是否直连
	private String borrowingMarked=""; //资金来源借贷标识
	private String couponFlag=""; //优惠券标识
	private String platformID=""; //平台商ID
	private String returnUrl=""; //商户回调地址
	private String noticeUrl=""; //商户通知地址
	private String partnerID=""; //商户ID
	private String remark=""; //扩展字段
	private String charset=""; //编码方式
	private String signType=""; //签名类型
	private String signMsg=""; //签名字符串
	private String userId=""; //签名字符串
	//封装的产品信息，必填项	
	private String orders=""; //订单详细信息
	private String orderID=""; //订单号
	private String orderAmount=""; //订单明细金额
	private String displayName=""; //下单商户显示名
	private String goodsName=""; //商品名称
	private String goodsCount=""; //商品数量
	
	private String gatewayUrl="";  //支付跳转的url

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSerialID() {
		return serialID;
	}

	public void setSerialID(String serialID) {
		this.serialID = serialID;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getFailureTime() {
		return failureTime;
	}

	public void setFailureTime(String failureTime) {
		this.failureTime = failureTime;
	}

	public String getCustomerIP() {
		return customerIP;
	}

	public void setCustomerIP(String customerIP) {
		this.customerIP = customerIP;
	}

	public String getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(String orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBuyerMarked() {
		return buyerMarked;
	}

	public void setBuyerMarked(String buyerMarked) {
		this.buyerMarked = buyerMarked;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getDirectFlag() {
		return directFlag;
	}

	public void setDirectFlag(String directFlag) {
		this.directFlag = directFlag;
	}

	public String getBorrowingMarked() {
		return borrowingMarked;
	}

	public void setBorrowingMarked(String borrowingMarked) {
		this.borrowingMarked = borrowingMarked;
	}

	public String getCouponFlag() {
		return couponFlag;
	}

	public void setCouponFlag(String couponFlag) {
		this.couponFlag = couponFlag;
	}

	public String getPlatformID() {
		return platformID;
	}

	public void setPlatformID(String platformID) {
		this.platformID = platformID;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public String getPartnerID() {
		return partnerID;
	}

	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignMsg() {
		return "version="+version
				 +"&serialID="+serialID
				 +"&submitTime="+submitTime
				 +"&failureTime="+failureTime
				 +"&customerIP="+customerIP
				 +"&orderDetails="+orderDetails
				 +"&totalAmount="+totalAmount
				 +"&type="+type
				 +"&buyerMarked="+buyerMarked
				 +"&payType="+payType
				 +"&orgCode="+orgCode
				 +"&currencyCode="+currencyCode
				 +"&directFlag="+directFlag
				 +"&borrowingMarked="+borrowingMarked
				 +"&couponFlag="+couponFlag
				 +"&platformID="+platformID
				 +"&returnUrl="+returnUrl
				 +"&noticeUrl="+noticeUrl
				 +"&partnerID="+partnerID
				 +"&remark="+remark
				 +"&charset="+charset
				 +"&signType="+signType;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsCount() {
		return goodsCount;
	}

	public void setGoodsCount(String goodsCount) {
		this.goodsCount = goodsCount;
	}

	public String getGatewayUrl() {
		return gatewayUrl;
	}

	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}

	@Override
	public String toString() {
		return "OnlineRechargeXinSheng [version=" + version + ", serialID="
				+ serialID + ", submitTime=" + submitTime + ", failureTime="
				+ failureTime + ", customerIP=" + customerIP
				+ ", orderDetails=" + orderDetails + ", totalAmount="
				+ totalAmount + ", type=" + type + ", buyerMarked="
				+ buyerMarked + ", payType=" + payType + ", orgCode=" + orgCode
				+ ", currencyCode=" + currencyCode + ", directFlag="
				+ directFlag + ", borrowingMarked=" + borrowingMarked
				+ ", couponFlag=" + couponFlag + ", platformID=" + platformID
				+ ", returnUrl=" + returnUrl + ", noticeUrl=" + noticeUrl
				+ ", partnerID=" + partnerID + ", remark=" + remark
				+ ", charset=" + charset + ", signType=" + signType
				+ ", signMsg=" + signMsg + ", orders=" + orders + ", orderID="
				+ orderID + ", orderAmount=" + orderAmount + ", displayName="
				+ displayName + ", goodsName=" + goodsName + ", goodsCount="
				+ goodsCount + ", gatewayUrl=" + gatewayUrl + ", userId=" + userId +"]";
	}

}
