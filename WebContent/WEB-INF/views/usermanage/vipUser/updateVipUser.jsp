<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath()+"";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改vip用户</title>
</head>
<body>
	<div class="pageContent">
			<form id="frm" method="post" action="vip/o_doUpdateVipUser" onsubmit="return validateCallback(this,dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" id="id" value="${vipUser.id }"> 
				<input type="hidden" name="userId" id="id" value="${vipUser.userId }"> 
				<div class="pageFormContent" layoutH="56">
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">用户名称:</label><input type="text" readonly="readonly"  value="${vipUser.user.userAccount }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">真实姓名:</label><input type="text" readonly="readonly" value="${vipUser.user.userRealname }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">客服:</label><input type="text" readonly="readonly"  value="${vipUser.userCustomer.userAccount }" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">添加时间:</label><input type="text" readonly="readonly"  value="<fmt:formatDate value="${vipUser.vipAddDatetime }" pattern="yyyy-MM-dd HH:mm:ss"/>" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">开始时间:</label><input type="text" readonly="readonly"  value="<fmt:formatDate value="${vipUser.vipStartDate }" pattern="yyyy-MM-dd HH:mm:ss"/>" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">结束时间:</label><input type="text" name="vipEndDate" class="date textInput readonly" datefmt="yyyy-MM-dd HH:mm:ss"  value="<fmt:formatDate value="${vipUser.vipEndDate }" pattern="yyyy-MM-dd HH:mm:ss"/>" /></p>
					<div class="divider"></div>
					
					<p><label style="float:left; width:130px; padding:0 5px; line-height:21px;">状态:</label>
						<select name="vipStatus" id="vipStatus" >
								<c:forEach var="name" items="${VIP_STATUS }">
								
									<!-- 申请中 ,未通过，vip用户-->
									<c:if test="${name.key eq  0 && name.key eq 5 }">
										<option disabled="disabled" value="${name.key }">${name.value }</option>
									</c:if>
									<c:if test="${name.key ne 0 && name.key ne 5 }">
										<c:if test="${name.key eq vipUser.vipStatus }">
											<option value="${name.key }" selected="selected">${name.value }</option>
										</c:if>
										<c:if test="${name.key ne vipUser.vipStatus }">
											<option value="${name.key }">${name.value }</option>
										</c:if>
									</c:if>
								</c:forEach>
							</select>
					<div class="divider"></div>
					
				</div>
					<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit" >
										保存
									</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">
										取消
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