<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>添加 线下充值</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="accountRecharge/${PRE_PATH_EDIT }save"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id"  value="${right_id}" />
				<div class="pageFormContent" layoutH="56">
					 <p><label>用户名：</label><input name="userAccount" class="required"  type="text" alt="请输入用户名" size="20"  value="" /></p>
					<div class="divider"></div>
					 <p><label>类型：</label><input name="rechargeType" readonly="readonly" value="后台充值" style="border: none;"/> </p>
					<div class="divider"></div>
					<p><label>金&nbsp;&nbsp;&nbsp;&nbsp;额：</label><input name="rechargeMoney" class="number required"  type="text"  size="16" value="" />元</p>
					<div class="divider"></div>
					<p style="height: 80px;"><label>备&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
					   <textarea rows="4" cols="30" name="rechargeRemark" style="max-height: 70px;"></textarea>
				    </p>
				   <%--  <div class="divider"></div>
				      <table>
				       <tr><td><label >充值方式：</label></td><td></td></tr>
					       <c:forEach var="paymentConfig" items="${paymentConfigList }" varStatus="idxStatus">
						      <tr>
						      	 <td> </td>
						         <td>
							     <c:choose>

								   <c:when test="${'0' eq idxStatus.index }">    
								      <input name="rechargePayment"  type="radio" value="${paymentConfig.paymentDescription }"  checked="checked"/>${paymentConfig.paymentDescription }
								   </c:when>
								   
								   <c:otherwise>  
								       <input name="rechargePayment"  type="radio" value="${paymentConfig.paymentDescription }"  />${paymentConfig.paymentDescription }
								   </c:otherwise>
								  
								</c:choose>
							     </td>
							  </tr>
						   </c:forEach>
					   
				       </table>  --%> 
					<div class="divider"></div>
					<p><label>验证码:</label>
						<input maxlength="4" style="width:60px; margin-bottom: 10px;" name="captcha" type="text" id="captcha" class="input" >
						<img style="margin-top: -5px;" src="<%=path %>/captcha.svl" onclick="this.src='<%=request.getContextPath() %>/captcha.svl?d='+new Date()*1" valign="bottom" alt="点击更新" title="点击更新" />
					</p>
					<div class="divider"></div>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
									   确定充值
									</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取&nbsp;&nbsp;消
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>
	</body>
</html>
