<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
   String path = request.getContextPath();
%>
<div class="iamount r3">
	<c:if test="${empty userAccount}">
		<b>您尚未登录</b>
		<a href="<%=path %>/login" class="icons">添加金额</a>
	</c:if>
	<c:if test="${not empty userAccount}">
		<b>可用余额：${userAccount.availableMoney }</b>
		<a href="<%=path %>/account/recharge.html" class="icons">添加金额</a>
	</c:if>
</div>