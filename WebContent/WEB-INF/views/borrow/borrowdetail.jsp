<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();

%>   

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>详细信息</title>
	</head>
	<body>
		<div class="pageContent">
			<form id="frm" method="post" action=""  
				onsubmit="return validateCallback(this, dialogAjaxDone);" class="pageForm required-validate">
				<input type="hidden" name="right_id" value="${right_id}" />
				<input type="hidden" name="id" id="borrowId" value="${borrow.id}">
				
				<div class="pageFormContent" layoutH="60">
				<fieldset>
						<legend>借款信息</legend>
				   <dl>
						<dt>用户名:</dt>
						<dd class="unit">
						  <input type="text" readonly="readonly" size="30"  value="${borrow.user.userAccount}" />
						</dd>
					</dl>
					<dl>
						<dt>标种:</dt>
						<dd class="unit">
						 <input type="text" readonly="readonly" size="30"  value="${borrow.borrowType.name}" />
						</dd>
					</dl>
					 <dl>
						<dt>借款标题:</dt>
						<dd class="unit">
						  <input type="text" name="borrowTitle" readonly="readonly" size="30" value="${borrow.borrowTitle}" />
						</dd>
					</dl>
					<dl>
						<dt>借款金额:</dt>
						<dd class="unit">
						 <input type="text" name="borrowSum" readonly="readonly" size="30"  value="${borrow.borrowSum}" />
						</dd>
					</dl>
					<dl>
						<dt>利率:</dt>
						<dd class="unit">
						  <input type="text" name="annualInterestRate" readonly="readonly" size="30"  value="${borrow.annualInterestRate}" />
						</dd>
					</dl>
					<dl>
						<dt>用途:</dt>
						<dd class="unit">
						<input type="text" name="borrowUse" readonly="readonly" size="30"  value="${BORROW_ALL_BORROW_USE[borrow.borrowUse]}" />
						</dd>
					</dl>
					<dl>
						<dt>借款期限:</dt>
						<dd class="unit">
						  <c:if test="${borrow.isDay eq 1 }">
							<input type="text" name="borrowTimeLimit" readonly="readonly" size="30"  value="${borrow.borrowTimeLimit}天" />
						 </c:if>
						 <c:if test="${borrow.isDay eq 2 }">
							<input type="text" name="borrowTimeLimit" readonly="readonly" size="30"  value="${borrow.borrowTimeLimit}月" />
						 </c:if>
						</dd>
					</dl>
					  <dl>
						<dt>还款方式:</dt>
						<dd class="unit">
						  <input type="text"   readonly="readonly" size="30"  value="${BORROW_ALL_REPAYMENT_STYLE[borrow.repaymentStyle]}" />
						</dd>
					</dl>
					<dl>
						<dt>添加时间:</dt>
						<dd class="unit">
						  <input type="text"  readonly="readonly" size="30"  value="<fmt:formatDate value="${borrow.borrowAddtime}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
						</dd>
					</dl>
					
					<dl>
						<dt>添加IP:</dt>
						<dd class="unit">
						  <input type="text" name="vouchTimes" readonly="readonly" size="30"  value="${borrow.borrowAddip}" />
						</dd>
					</dl>
					<dl>
						<dt>发布时间:</dt>
						<dd class="unit">
					    	<input type="text"  readonly="readonly" size="30"  value="<fmt:formatDate value="${borrow.publishDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
						</dd>
					</dl>
				
				</fieldset>
				<%-- <fieldset>
						<legend>担保信息</legend>
					<dl>
						<dt>担保人:</dt>
						<dd class="unit">
						<input type="text" name="borrowUsr" readonly="readonly" size="30"  value="${borrow.vouchUsr.userAccount}" />
						</dd>
					</dl>
					<dl>
						<dt>担保金额:</dt>
						<dd class="unit">
						  <input type="text" name="vouchAmount" readonly="readonly" size="30"  value="${borrow.vouchAmount}" />
						</dd>
					</dl>
					<dl>
						<dt>担保奖励:</dt>
						<dd class="unit">
							<input type="text" name="vouchAward" readonly="readonly" size="30"  value="${borrow.vouchAward}" />
						</dd>
					</dl>
					<dl>
						<dt>担保时间:</dt>
						<dd class="unit">
						  <input type="text" name="vouchTimes" readonly="readonly" size="30"  value="${borrow.vouchTimes}" />
						</dd>
					</dl>
				</fieldset> --%>
					
				<fieldset>
						<legend>审核信息</legend>
					<dl>
						<dt>初审核人:</dt>
						<dd class="unit">
					    	<input type="text"   readonly="readonly" size="30"  value="${borrow.trialUser.userAccount}" />
						</dd>
					</dl>
					<dl>
						<dt>初审时间:</dt>
						<dd class="unit">
						  <input type="text"   readonly="readonly" size="30"  value="<fmt:formatDate value="${borrow.verifyTrialTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>初审备注:</dt>
						<dd class="unit">
					    	<textarea class="textInput valid" rows="5" cols="45" readonly="readonly">${borrow.verifyTrialRemark}</textarea>
						</dd>
					</dl>
					
					<dl>
						<dt>复审核人:</dt>
						<dd class="unit">
						<input type="text"   readonly="readonly" size="30"  value="${borrow.reviewUser.userAccount}" />
						</dd>
					</dl>
					<dl>
						<dt>复审时间:</dt>
						<dd class="unit">
					    	<input type="text"   readonly="readonly" size="30"  value="<fmt:formatDate value="${borrow.verifyReviewTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" />
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>复审备注:</dt>
						<dd class="unit">
						<textarea class="textInput valid" rows="5" cols="45" readonly="readonly">${borrow.verifyReviewRemark}</textarea>
						</dd>
					</dl>
				</fieldset>
	<%-- 			<fieldset>
						<legend>还款信息</legend>
					<dl>
						<dt>最后还款人:</dt>
						<dd class="unit">
					    	<input type="text"readonly="readonly" size="30"  value="${borrow.lastRepaymentUser.userAccount}" />
						</dd>
					</dl>
					<dl>
						<dt>还款总额:</dt>
						<dd class="unit">
						<input type="text" readonly="readonly" size="30"  value="${borrow.repaymentAmount}" />
						</dd>
					</dl>
					<dl>
						<dt>每月还款额:</dt>
						<dd class="unit">
						<input type="text" readonly="readonly" size="30"  value="${borrow.monthlyRepayment}" />
						</dd>
					</dl>
				  <dl>
						<dt>已付本金:</dt>
						<dd class="unit">
						<input type="text" readonly="readonly" size="30"  value="${borrow.paidAmount}" />
						</dd>
					</dl>
					<dl>
						<dt>已付利息:</dt>
						<dd class="unit">
						<input type="text" readonly="readonly" size="30"  value="${borrow.paidInterest}" />
						</dd>
					</dl>
					 
				  </fieldset>  --%>
				  <dl class="nowrap">
						<dt>借款详细说明:</dt>
						<dd class="unit">
						<textarea class="editor" rows="16" cols="75" readonly="readonly">${borrow.borrowContent}</textarea>
						</dd>
					</dl>
				</div>
				<div class="formBar">
					<ul>
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
