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
  <form name="form" id="form" action="https://yintong.com.cn/payment/bankgateway.htm" method="POST" >
   <!--宝付支付  -->
  <c:if test="${payType=='122'}">
  				<input type="hidden" name="version" value="${params.version }"/>
                <input type="hidden" name="dt_order" value="${params.dt_order }"/>
                <input type="hidden" name="busi_partner" value="${params.busi_partner}"/>
                <input type="hidden" name="money_order" value="${params.money_order }"/>
                <input type="hidden" name="name_goods" value="${params.name_goods }"/>
                <input type="hidden" name="no_order" value="${params.no_order }"/>
                <input type="hidden" name="notify_url" value="${params.notify_url }"/>
                <input type="hidden" name="oid_partner" value="${params.oid_partner }"/>
                <input type="hidden" name="risk_item" value='${params.risk_item }'/>
                <input type="hidden" name="sign_type" value="${params.sign_type }"/>
                 <input type="hidden" name="timestamp" value="${params.timestamp }"/>
                <input type="hidden" name="url_return" value="${params.url_return }"/>
                <input type="hidden" name="user_id" value="${params.user_id }"/>
                <input type="hidden" name="userreq_ip" value="${params.userreq_ip }"/>
                <input type="hidden" name="valid_order" value="${params.valid_order }"/>
  
  
  
  
                <input type="hidden" name="sign" value="${params.sign }"/>
                <input type="hidden" name="info_order" value="${params.info_order }"/>
                <input type="hidden" name="url_order" value="${params.url_order }"/>
                   <input type="hidden" name="no_agree" value="${params.no_agree }"/>
                 <input type="hidden" name="id_type" value="${params.id_type }"/>
                  <input type="hidden" name="id_no" value="${params.id_no }"/>
                  <input type="hidden" name="acct_name" value="${params.acct_name }"/>
                   <input type="hidden" name="flag_modify" value="${params.flag_modify }"/>
                   <input type="hidden" name="card_no" value="${params.card_no }"/>
                  <input type="hidden" name="back_url" value="${params.back_url }"/> 
  </c:if>
    
  </form>
</body>
</html>