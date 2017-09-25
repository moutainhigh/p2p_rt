<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jumping...</title>
</head>
<body onLoad="javascript:document.form.submit()"> 
<br><br><br><br><br>
  <center>
    <font style="font-family:宋体,Verdana, Arial, Helvetica, sans-serif;color: #3265c3;font-size:14px;font-weight:bold">
					正在跳转到支付界面...</font>
 </center>
  <!-- 国付宝 -->
  <form name="form" id="form" action="${params.gatewayUrl }" method="POST" >
   <!--宝付支付  -->
  <c:if test="${payType=='122'}">
  		<input type="hidden" name="version" value="${params.version }"/>
                <input type="hidden" name="oid_partner" value="${params.oid_partner }"/>
                <input type="hidden" name="user_id" value="${params.user_id }"/>
                <input type="hidden" name="sign_type" value="${params.sign_type }"/>
                <input type="hidden" name="sign" value="${params.sign }"/>
                <input type="hidden" name="busi_partner" value="${params.busi_partner}"/>
                <input type="hidden" name="no_order" value="${params.no_order }"/>
                <input type="hidden" name="dt_order" value="${params.dt_order }"/>
                <input type="hidden" name="name_goods" value="${params.name_goods }"/>
                <input type="hidden" name="info_order" value="${params.info_order }"/>
                <input type="hidden" name="money_order" value="${params.money_order }"/>
                <input type="hidden" name="notify_url" value="${params.notify_url }"/>
                <input type="hidden" name="url_return" value="${params.url_return }"/>
                <input type="hidden" name="userreq_ip" value="${params.userreq_ip }"/>
                <input type="hidden" name="url_order" value="${params.url_order }"/>
                <input type="hidden" name="valid_order" value="${params.valid_order }"/>
                <input type="hidden" name="risk_item" value='${params.risk_item }'/>
                 <input type="hidden" name="timestamp" value="${params.timestamp }"/>
                  <input type="hidden" name="no_agree" value="${params.no_agree }"/>
                 <input type="hidden" name="id_type" value="${params.id_type }"/>
                  <input type="hidden" name="id_no" value="${params.id_no }"/>
                  <input type="hidden" name="acct_name" value="${params.acct_name }"/>
                   <input type="hidden" name="flag_modify" value="${params.flag_modify }"/>
                   <input type="hidden" name="card_no" value="${params.card_no }"/>
                  <input type="hidden" name="back_url" value="${params.back_url }"/>
  </c:if>
  <c:if test="${payType=='3'}">
   
	    <input type="hidden" name="merchantID"  	value="${params.merchantID}"/>
	    <input type="hidden" name="virCardNoIn" 	value="${params.virCardNoIn}"/>
	  
	    <input type="hidden" name="merOrderNum" 	value="${params.merOrderNum}"/>
	    <input type="hidden" name="tranAmt" 		value="${params.tranAmt}"/>
	    <input type="hidden" name="feeAmt" 			value="${params.feeAmt}"/>
	    <input type="hidden" name="tranDateTime" 	value="${params.tranDateTime}"/>
	    <input type="hidden" name="tranIP" 			value="${params.tranIP}"/>
	    <input type="hidden" name="gopayServerTime" value="${params.gopayServerTime}"/>
	    <input type="hidden" name="goodsName" 		value="${params.goodsName}"/>
    <input type="hidden" name="goodsDetail" 		value="${params.goodsDetail}"/>
	    
	    <input type="hidden" name="buyerName" 		value="${params.buyerName}"/>
	    <input type="hidden" name="buyerContact" 	value="${params.buyerContact}"/>
	    <input type="hidden" name="signValue" 		value="${params.signValue}"/>
	    <input type="hidden" name="frontMerUrl"		 value="${params.frontMerUrl}"/>
	    <input type="hidden" name="backgroundMerUrl" value="${params.backgroundMerUrl}"/>
	    <input type="hidden" name="userType"    	value="${params.userType}"/>
	    <input type="hidden" name="version" 		value="${params.version}"/>
	    <input type="hidden" name="charset" 		value="${params.charset}"/>
	    <input type="hidden" name="language" 		value="${params.language}"/>
	    <input type="hidden" name="signType"	 	value="${params.signType}"/>
	    <input type="hidden" name="tranCode" 		value="${params.tranCode}"/>
	    <input type="hidden" name="currencyType" 	value="${params.currencyType}"/>
	    <input type="hidden" name="isRepeatSubmit"  value="${params.isRepeatSubmit}"/>
	    <input type="hidden" name="merRemark1" 		value="${params.merRemark1}"/>
  </c:if>
  <!-- 网银在线支付 -->
  <c:if test="${payType=='1'}">
		<input type="hidden" name="v_mid"        value="${params.mid}">
		<input type="hidden" name="v_oid"        value="${params.oid}">
		<input type="hidden" name="v_amount"     value="${params.amount}">
		<input type="hidden" name="v_moneytype"  value="${params.moneytype}">
		<input type="hidden" name="v_url"        value="${params.url}"> 
		<input type="hidden" name="v_md5info"    value="${params.md5info}">
		<input type="hidden" name="remark1"      value="${params.remark1}">
		<input type="hidden" name="remark2" value="${params.remark2}">
  </c:if>
  <!--双乾支付  -->
  <c:if test="${payType=='4'}">
  		<input type="hidden" name="MerNo" 		 value="${params.merNo}" />
		<input type="hidden" name="BillNo"		 value="${params.billNo}" /> 
		<input type="hidden" name="Amount" 		 value="${params.amount}" /> 
		<input type="hidden" name="PaymentType"  value="${params.paymentType}" /> 
		<input type="hidden" name="PayType"		 value="${params.payType}"/>
	    <input type="hidden" name="ReturnURL" 	 value="${params.returnURL}" /> 
	    <input type="hidden" name="NotifyURL"	 value="${params.notifyURL}" /> <!-- 防止掉单 -->
	    <input type="hidden" name="MD5info" 	 value="${params.md5info}" /> 
	    <input type="hidden" name="MerRemark"    value="${params.merRemark}"/>

		
  </c:if>
   <!--宝付支付  -->
  <c:if test="${payType=='2'}">
  		<input name='MemberID' type='hidden' value= "${params.memberID}"/>
		<input name='TerminalID' type='hidden' value= "${params.terminalID}"/>
		<input name='InterfaceVersion' type='hidden' value= "${params.interfaceVersion}"/>
		<input name='KeyType' type='hidden' value= "${params.keyType}"/>
		<input name='PayID' type='hidden' value= "${params.payID}"/>		
		<input name='TradeDate' type='hidden' value= "${params.tradeDate}" />
		<input name='TransID' type='hidden' value= "${params.transID}" />
		<input name='OrderMoney' type='hidden' value= "${params.orderMoney}"/>
		<input name='Username' type='hidden' value= "${params.username}"/>
		<input name='AdditionalInfo' type='hidden' value= "${params.additionalInfo}"/>
		<input name='PageUrl' type='hidden' value= "${params.pageUrl}"/>
		<input name='ReturnUrl' type='hidden' value= "${params.returnUrl}"/>	
		<input name='Signature' type='hidden' value="${params.md5info}"/>
		<input name='NoticeType' type='hidden' value= "${params.noticeType}"/>
  </c:if>
   <!-- 银联在线支付 -->
  <c:if test="${payType=='5'}">
		<input type="hidden" name="MerId"         value="${params.merId}">
		<input type="hidden" name="OrdId"        value="${params.ordId }">
		<input type="hidden" name="TransAmt"        value="${params.transAmt}">
		<input type="hidden" name="CuryId"     value="${params.curyId}">
		<input type="hidden" name="TransDate"  	  value="${params.transDate}">
		<input type="hidden" name="TransType"      value="${params.transType}"> 
		<input type="hidden" name="Version"        value="${params.version}">
		<input type="hidden" name="BgRetUrl"     value="${params.bgRetUrl}">
		<input type="hidden" name="PageRetUrl"        value="${params.pageRetUrl}">
		<input type="hidden" name="GateId"      value="${params.gateId}"> 
		<input type="hidden" name="Priv1"        value="${params.priv1}">
		<input type="hidden" name="ChkValue"     value="${params.chkValue}">
		<input type="hidden" name="ExtFlag"     value="${params.extFlag}">
  </c:if>
   <!-- 汇潮在线支付 -->
  <c:if test="${payType=='6'}">
		<input type="hidden" name="MerNo"         value="${params.merNo}">
		<input type="hidden" name="BillNo"        value="${params.billNo}">
		<input type="hidden" name="Amount"        value="${params.amount}">
		<input type="hidden" name="AdviceURL"     value="${params.adviceURL}">
		<input type="hidden" name="ReturnURL"  	  value="${params.returnURL}">
		<input type="hidden" name="SignInfo"      value="${params.signInfo}"> 
		<input type="hidden" name="Md5src"        value="${params.md5src}">
		<input type="hidden" name="OrderTime"     value="${params.orderTime}">
		<input type="hidden" name="Remark"        value="${params.remark}">
  </c:if>
  <!--   贝付 -->
   <c:if test="${payType=='7'}">
      <input type="hidden" name="buyer_id" 		value="${params.buyerId}"/>
      <input type="hidden" name="subject" 		value="${params.subject}"/>
      <input type="hidden" name="sign_type" 	value="${params.signType}"/>
      <input type="hidden" name="input_charset" 			value="${params.inputCharset}"/>
      <input type="hidden" name="notify_url" 	value="${params.notifyUrl}"/>
      <input type="hidden" name="out_trade_no" value="${params.outTradeNo}"/>
      <input type="hidden" name="return_url" 			value="${params.returnUrl}"/>
      <input type="hidden" name="exter_invoke_ip" 		value="${params.exterInvokeIp}"/>
      <input type="hidden" name="total_fee" 		value="${params.totalFee}"/>
      <input type="hidden" name="service" 		value="${params.service}"/>
      <input type="hidden" name="partner"  	value="${params.partner}"/>
      <input type="hidden" name="seller_id" 		value="${params.sellerId}"/>
      <input type="hidden" name="anti_phishing_key" 		value="${params.antiPhishingKey}"/>
      <input type="hidden" name="payment_type" value="${params.paymentType}"/>
      <input type="hidden" name="sign" 	value="${params.sign}"/> 
      <input type="hidden" name="extra_common_param" value="${params.extraCommonParam }"/>
    </c:if>
      <!--   汇付宝 -->
   <c:if test="${payType=='8'}">
      <input type="hidden" name="version" 		value="${params.version}"/>
      <input type="hidden" name="pay_type" 		value="${params.payType}"/>
      <input type="hidden" name="pay_code" 	value="${params.payCode}"/>
      <input type="hidden" name="agent_id" 			value="${params.agentId}"/>
      <input type="hidden" name="agent_bill_id" 	value="${params.agentBillId}"/>
      <input type="hidden" name="pay_amt" value="${params.payAmt}"/>
      <input type="hidden" name="notify_url" 			value="${params.notifyUrl}"/>
      <input type="hidden" name="return_url" 		value="${params.returnUrl}"/>
      <input type="hidden" name="user_ip" 		value="${params.userIp}"/>
      <input type="hidden" name="agent_bill_time" 		value="${params.agentBillTime}"/>
      <input type="hidden" name="goods_name"  	value="${params.goodsName}"/>
      <input type="hidden" name="remark" 		value="${params.remark}"/>
    <%--   <input type="hidden" name="is_test" 		value="${params.isTest}"/> --%>
      <input type="hidden" name="sign" value="${params.sign}"/>
    </c:if>
     <!-- 财付通---铂金 -->
    <c:if test="${payType=='9' }">
      	<input type=hidden name="version" value="${params.version }">
		<input type=hidden name="reqType" value="${params.reqType }">
		<input type=hidden name="merId" value="${params.merId }">
		<input type=hidden name="reqDate" value="${params.reqDate }">
		<input type=hidden name="reqTime" value="${params.reqTime }">
		<input type=hidden name="ordId" value="${params.ordId }">
		<input type=hidden name="ordAmt" value="${params.ordAmt }">
		<input type=hidden name="gateId" value="${params.gateId }"> 
		<input type=hidden name="merPriv" value="${params.merPriv }">
		<input type=hidden name="retUrl" value="${params.retUrl }">
		<input type=hidden name="bgRetUrl" value="${params.bgRetUrl }">
		<input type=hidden name="md5Str" value="${params.md5Str }">
		<input type=hidden name="chkValue" value="${params.chkValue }">
    </c:if>
     <!--   新浪 -->
   <c:if test="${payType=='11'}">
      <input type="hidden" name="inputCharset" 		value="${params.inputCharset}"/>
      <input type="hidden" name="bgUrl" 		value="${params.bgUrl}"/>
      <input type="hidden" name="version" 	value="${params.version}"/>
      <input type="hidden" name="language" 			value="${params.language}"/>
      <input type="hidden" name="signType" 	value="${params.signType}"/>
      <input type="hidden" name="merchantAcctId" value="${params.merchantAcctId}"/>
      <input type="hidden" name="orderId" 			value="${params.orderId}"/>
      <input type="hidden" name="orderAmount" 		value="${params.orderAmount}"/>
      <input type="hidden" name="orderTime" 		value="${params.orderTime}"/>
      <input type="hidden" name="pid" 		value="${params.pid}"/>
      <input type="hidden" name="signMsg"  	value="${params.signMsg}"/>
       <!-- <input type="hidden" name="bankId"  	value="TESTBANK"/> --><!-- 测试用 -->
    </c:if>
    <!-- 易宝支付 -->
    <c:if test="${payType=='12'}">
    	<input type="hidden" name="p0_Cmd"   value="${params.p0_Cmd}">
		<input type="hidden" name="p1_MerId" value="${params.p1_MerId}">
		<input type="hidden" name="p2_Order" value="${params.p2_Order}">
		<input type="hidden" name="p3_Amt"   value="${params.p3_Amt}">
		<input type="hidden" name="p4_Cur"   value="${params.p4_Cur}">
		<input type="hidden" name="p5_Pid"   value="${params.p5_Pid}">
		<input type="hidden" name="p6_Pcat"  value="${params.p6_Pcat}">
		<input type="hidden" name="p7_Pdesc" value="${params.p7_Pdesc}">
		<input type="hidden" name="p8_Url"   value="${params.p8_Url}">
		<input type="hidden" name="p9_SAF"   value="${params.p9_SAF}">
		<input type="hidden" name="pa_MP"    value="${params.pa_MP}">
		<input type="hidden" name="pd_FrpId" value="${params.pd_FrpId}">
		<input type="hidden" name="pr_NeedResponse"  value="${params.pr_NeedResponse}">
		<input type="hidden" name="hmac"     value="${params.hmac}">
    </c:if>
    
     <!-- 丰付支付 -->
  <c:if test="${payType=='17'}">
		<input type="hidden" name="requestId"         value="${params.requestId}">
		<input type="hidden" name="tradeProcess"        value="${params.tradeProcess}">
		<input type="hidden" name="totalBizType"        value="${params.totalBizType}">
		<input type="hidden" name="totalPrice"     value="${params.totalPrice}">
		<input type="hidden" name="backurl"      value="${params.backurl}"> 
		<input type="hidden" name="returnurl"        value="${params.returnurl}">
		<input type="hidden" name="noticeurl"     value="${params.noticeurl}">
		<input type="hidden" name="description"        value="${params.description}">
		<input type="hidden" name="mersignature"         value="${params.mersignature}">
		<input type="hidden" name="goodsDesc"        value="${params.goodsDesc}">
		<input type="hidden" name="allowRePay"        value="${params.allowRePay}">
		<input type="hidden" name="rePayTimeOut"     value="${params.rePayTimeOut}">
		<input type="hidden" name="userIdIdentity"  	  value="${params.userIdIdentity}">
		<input type="hidden" name="bankCardType"      value="${params.bankCardType}"> 
		<input type="hidden" name="payTag"        value="${params.payTag}">
		<input type="hidden" name="productId"        value="${params.productId}">
		<input type="hidden" name="productName"        value="${params.productName}">
		<input type="hidden" name="fund"     value="${params.fund}">
		<input type="hidden" name="merAcct"  	  value="${params.merAcct}">
		<input type="hidden" name="bizType"      value="${params.bizType}"> 
		<input type="hidden" name="productNumber"        value="${params.productNumber}">
  </c:if>
  
  <!-- 新生支付 -->
  <c:if test="${payType=='19'}">
		<input type="hidden" name="version"         value="${params.version}">
		<input type="hidden" name="serialID"        value="${params.serialID}">
		<input type="hidden" name="submitTime"        value="${params.submitTime}">
		<input type="hidden" name="failureTime"     value="${params.failureTime}">
		<input type="hidden" name="customerIP"      value="${params.customerIP}"> 
		<input type="hidden" name="orderDetails"        value="${params.orderDetails}">
		<input type="hidden" name="totalAmount"     value="${params.totalAmount}">
		<input type="hidden" name="type"        value="${params.type}">
		<input type="hidden" name="buyerMarked"         value="${params.buyerMarked}">
		<input type="hidden" name="payType"        value="${params.payType}">
		<input type="hidden" name="orgCode"        value="${params.orgCode}">
		<input type="hidden" name="currencyCode"     value="${params.currencyCode}">
		<input type="hidden" name="directFlag"  	  value="${params.directFlag}">
		<input type="hidden" name="borrowingMarked"      value="${params.borrowingMarked}"> 
		<input type="hidden" name="couponFlag"        value="${params.couponFlag}">
		<input type="hidden" name="platformID"        value="${params.platformID}">
		<input type="hidden" name="returnUrl"        value="${params.returnUrl}">
		<input type="hidden" name="noticeUrl"     value="${params.noticeUrl}">
		<input type="hidden" name="partnerID"  	  value="${params.partnerID}">
		<input type="hidden" name="remark"      value="${params.remark}"> 
		<input type="hidden" name="charset"        value="${params.charset}">
		<input type="hidden" name="signType"        value="${params.signType}">
		<input type="hidden" name="signMsg"        value="${params.signMsg}">

  </c:if>
    
  </form>
</body>
</html>