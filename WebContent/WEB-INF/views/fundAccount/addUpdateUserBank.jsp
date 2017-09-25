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
		<title>修改/添加银行账号</title>
	</head>
	<body>
		<div class="pageContent">
			<form method="post" action="userBank/${PRE_PATH_EDIT }saveOrUpdate"  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id"  value="${right_id}" />
				<input type="hidden" name="id"  value="${userBank.id }">
				<div class="pageFormContent" layoutH="56">
					 <p><label>用户名：</label><input name="userAccount" class="required"  type="text" alt="请输入用户名" size="20"  value="${userAccount }" /></p>
					<div class="divider"></div>
					 <p><label>银&nbsp;&nbsp;&nbsp;&nbsp;行：</label>
						<select id="bankId" name="bankId"  class="required">
						<option value="">--请选择银行--</option>
						   <c:forEach var="bank" items="${allBankList }">
							 <option value="${bank.id }" <c:if test="${bank.id eq userBank.bankId}">selected="selected"</c:if>>${bank.bankName }</option>
							</c:forEach>
						</select>
					 </p>
					<div class="divider"></div>
					<p><label>银行账号：</label><input name="bankAccount" class="digits required"  type="text" alt="请输入银行账号" size="20" value="${userBank.bankAccount}" /></p>
					<div class="divider"></div>
					<p><label>开户城市：</label>
					   <input name="bankCity" class="required" value="${userBank.bankCity}" />
				    </p>
				    <div class="divider"></div>
					<p><label>银行支行：</label>
					    <input name="bankBranch" class="required"  value="${userBank.bankBranch }" />
					 </p> 
					<div class="divider"></div>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
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
