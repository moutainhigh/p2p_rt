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
		<title>解绑银行卡申请</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="userBank/${PRE_PATH_EDIT }saveOrUpdateRelaseBank"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id"  value="${right_id}" />
				<input type="hidden" name="id"  value="${relaseCard.id }">
				<div class="pageFormContent" layoutH="56">
					 <p><label>用户名称：</label>${relaseCard.bankUser.userAccount }</p>
					 <div class="divider"></div>
					 <p><label>真实姓名：</label>${relaseCard.bankUser.userRealname }</p>
					 <div class="divider"></div>
					 <p><label>申诉人：</label>${relaseCard.operateUser.userAccount }&nbsp;(${relaseCard.operateUser.userRealname })</p>
					 <div class="divider"></div>
					 <p><label>申述理由：</label>${relaseCard.reReason }</p>
					 <div class="divider"></div>
					 <p style="height: 186px;"><label>身份证：</label>
						<img src="<%=path %>${relaseCard.cardPath }" style="width: 320px;height: 182px;">
					 </p>
					<div class="divider"></div>
					<p><label>状态：</label>
						<c:if test="${relaseCard.result=='-1'||relaseCard.result=='1'}">
							<input value="2" name="result"  type="radio">成功&nbsp;&nbsp;&nbsp;&nbsp;
							<input value="1" checked="checked" name="result" type="radio">失败
						</c:if>
						<c:if test="${relaseCard.result=='2'}">申请通过</c:if>
					<div class="divider"></div>
					 </p>
					<div class="divider"></div>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<c:if test="${relaseCard.result=='-1'}">
								<div class="buttonActive">
									<div class="buttonContent">
										<button type="submit">保存</button>
									</div>
								</div>
							</c:if>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">取消</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>
	</body>
</html>