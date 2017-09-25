<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body >
<div class="pageContent">
	<c:if test="${code==1}">
			<form id="frm" method="post" enctype="multipart/form-data" action="accountCash/o_updateAccountCash" onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate"> 
				<div class="pageFormContent" layoutH="56">
					<input type="hidden" value="update" name="type">
					<input type="hidden" value="${right_id }" name="right_id" />
					<input type="hidden" value="0" name="cashStatus" />
					<input type="hidden" value="${accountCash.id}" name="id" />  
					<%-- <input type="hidden" value="${accountCash.noOrder}" name="noOrder" /> --%>  
					<input type="hidden" value="${accountCash.userId}" name="userId" />  
					<input type="hidden" value="${accountCash.cashType}" name="cashType" />  
					<p><label>用户名称:</label><input value="${accountCash.userAccount }" readonly="readonly" size="20"  type="text" name="userAccount" /></p>
					<div class="divider"></div>
					<p><label>真实姓名:</label><input value="${accountCash.userRealname }" readonly="readonly" size="20" type="text" name="userRealname"/></p>
					<div class="divider"></div>
					<p><label>订单号:</label><input value="${accountCash.noOrder }" readonly="readonly" size="50" type="text" name="noOrder"/></p>
					<div class="divider"></div>
					<p><label>提现账号:</label><input value="${accountCash.cashBankAccount }" readonly="readonly"  size="50" type="text" name="cashBankAccount"/></p>
					<div class="divider"></div>
					<p><label>提现银行:</label><input value="${accountCash.cashBank }" readonly="readonly"  size="20" type="text" name="cashBank" /></p>
					<div class="divider"></div>
					<p><label>提现支行:</label><input value="${accountCash.cashBranch }" readonly="readonly"  size="20" type="text" name="cashBranch"/></p>
					<div class="divider"></div>
					<p><label>申请总额:</label><input value="${accountCash.cashTotal }" readonly="readonly"  size="20" type="text"  name="cashTotal"/></p>
					<div class="divider"></div>
					<p><label>到账金额:</label><input value="${accountCash.cashAccount }"  size="20" type="text" name="cashAccount" /></p>
					<div class="divider"></div>
					<p><label>手续费:</label><input value="${accountCash.cashFee }"  size="20" type="text" /></p>
					<div class="divider"></div>
					<p><label>申请时间:</label><input value="<fmt:formatDate value="${accountCash.cashAddtime }" pattern="yyyy-MM-dd  HH:mm:ss" />" readonly="readonly"  size="20" type="text" /></p>
					<div class="divider"></div>
					<p><label>申请IP:</label><input value="${accountCash.cashAddip }" readonly="readonly"  size="20" type="text" /></p>
					<div class="divider"></div>
					<label>状态:</label>
					<c:if test="${accountCash.cashStatus ==0||accountCash.cashStatus ==4||accountCash.cashStatus ==5}"><input value="1" name="cashStatuss"  type="radio">成功&nbsp;&nbsp;&nbsp;&nbsp;<input value="2" checked="checked" name="cashStatuss" type="radio">失败</c:if>
					<c:if test="${accountCash.cashStatus ==1}">成功</c:if>
					<c:if test="${accountCash.cashStatus ==2}">失败</c:if>
					<div class="divider"></div>
					<label>申请备注:</label><textarea class="textInput valid" name="verifyRemark" rows="5" cols="45"></textarea>
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
			</c:if>	
		<c:if test="${code==0}">
			${message}
		</c:if>
		</div>
</body>
</html>