package com.rbao.east.pay.entity;

import java.io.Serializable;
/**
 * 铂金pos平台支付
 */
public class OnlineRechargePOSPT implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String gatewayUrl="";
	//必选
	private String version="";//版本号,目前固定为10
	
	private String reqType="";//请求类型,"OrderPay"
	
	private String merId="";//商户号,由支付系统分配的10位数字代码，商户的唯一标识
	
	private String reqDate="";//请求日期,格式:yyyyMMdd
	
	private String reqTime="";//请求时间,格式:HHmmss
	
	private String ordId="";//订单号,10-20位,
	
	private String ordAmt="";//订单金额,4-12位
	
	private String retUrl="";//返回url
	
	private String bgRetUrl="";//订单支付时，商户后台应答地址
	
	private String chkValue="";//签名,各接口所列有的请求参数和返回参数如无个别说明，都需要参与签名，参与签名的数据体为：按照每个接口中包含的参数值（不包含参数名）的顺序（按接口表格中从左到右，从上到下的顺序）进行字符串相加并加上约定的十位加密串采用MD5算法得出的32位密文。如果参数为可选项并且为空，则该参数值不参与签名。
	
	private String md5Str="";
	private String signStr="";
	
	//可选参数
	private String gateId="";//银行ID,支付的银行号。tenpay – 财付通,c_tenpay – 财付通代付充值
	
	private String merPriv="";//商户私有域

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getReqDate() {
		return reqDate;
	}

	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getOrdId() {
		return ordId;
	}

	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}

	public String getOrdAmt() {
		return ordAmt;
	}

	public void setOrdAmt(String ordAmt) {
		this.ordAmt = ordAmt;
	}

	public String getRetUrl() {
		return retUrl;
	}

	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}

	public String getBgRetUrl() {
		return bgRetUrl;
	}

	public void setBgRetUrl(String bgRetUrl) {
		this.bgRetUrl = bgRetUrl;
	}

	public String getChkValue() {
		return chkValue;
	}

	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}

	public String getGateId() {
		return gateId;
	}

	public void setGateId(String gateId) {
		this.gateId = gateId;
	}

	public String getMerPriv() {
		return merPriv;
	}

	public void setMerPriv(String merPriv) {
		this.merPriv = merPriv;
	}

	public String getGatewayUrl() {
		return gatewayUrl;
	}

	public void setGatewayUrl(String gatewayUrl) {
		this.gatewayUrl = gatewayUrl;
	}

	public String getSignStr() {
		return signStr=version + reqType + merId + reqDate + reqTime + ordId + ordAmt
				 +  gateId + merPriv + retUrl +  bgRetUrl +  md5Str;
	}

	public void setSignStr(String signStr) {
		this.signStr = signStr;
	}

	public String getMd5Str() {
		return md5Str;
	}

	public void setMd5Str(String md5Str) {
		this.md5Str = md5Str;
	}
	
	
	
}
