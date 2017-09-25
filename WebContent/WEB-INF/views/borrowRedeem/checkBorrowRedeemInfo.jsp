<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	java.util.Date currentTime = new java.util.Date();
	String date = formatter.format(currentTime);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="pageContent">
		<form id="frm" method="post" enctype="multipart/form-data"
			action="borrowRedeem/o_checkBorrowRedeem"
			onsubmit="return validateCallback(this, navTabAjaxDone);"
			class="pageForm required-validate">
			<input type="hidden" id="id" name="id" value="${borrowRedeem.id }" />
			<input type="hidden" name="right_id" value="${right_id}" /> <input
				type="hidden" name="type" value="update" />
			<div class="pageFormContent" layoutH="55">
				<fieldset>
					<legend>基础信息</legend>
					<dl class="nowrap">
						<dt>借款标题:</dt>
						<dd>
							<span style="color: red;">${borrowRedeem.borrowTitle }</span>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>用户名称:</dt>
						<dd>
							<span style="color: red;">${borrowRedeem.userAccount }</span>
						</dd>
					</dl>
					<dl class="wrap">
						<dt>赎回金额:</dt>
						<dd>
							<span style="color: red;">￥${borrowRedeem.redeemMoney }</span>
						</dd>
					</dl>
					<dl class="wrap">
						<dt>手续费:</dt>
						<dd>
							<span style="color: red;">￥${borrowRedeem.redeemFee }</span>
						</dd>
					</dl>
					<dl class="nowrap">
						<dt>到账金额:</dt>
						<dd>
							<span style="color: red;">￥${borrowRedeem.redeemBackmoney }</span>
						</dd>
					</dl>
					<dl class="wrap">
						<dt>申请时间:</dt>
						<dd>
							<span style="color: red;"><fmt:formatDate
									value="${borrowRedeem.createTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></span>
						</dd>
					</dl>
					<dl class="wrap">
						<dt>申请ip:</dt>
						<dd>
							<span style="color: red;">${borrowRedeem.createIp}</span>
						</dd>
					</dl>
				</fieldset>
				<fieldset>
					<legend>待收款详细</legend>
						<div class="pageContent">
							<table class="list" width="100%" >
								<thead>
									<tr>
										<th width="5%" align="center">序号</th>
										<th width="10%" align="center">借款标题</th>
										<th width="10%" align="center">当前期数</th>
										<th width="10%" align="center">总期数</th>
										<th width="10%" align="center">估计还款时间</th>
										<th width="10%" align="center">待收总额</th>
										<th width="10%" align="center">实还金额</th>
										<th width="10%" align="center">待收利息</th>
										<th width="10%" align="center">待收本金</th>
										<th width="10%" align="center">滞纳金</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="borrowRepossessed" items="${pm }"
										varStatus="st">
										<tr target="sid_support" rel="${borrowRepossessed.id }">
											<th align="center">${borrowRepossessed.id }
											</th>
											<th align="center">${borrowRepossessed.borrow_title}</th>
											<th align="center">${borrowRepossessed.current_period}</th>
											<th align="center">${borrowRepossessed.total_period}</th>
											<th align="center">${borrowRepossessed.prepare_datetime}</th>
											<th align="center">${borrowRepossessed.prepare_amount}</th>
											<th align="center">${borrowRepossessed.paid_amount}</th>
											<th align="center">${borrowRepossessed.repossessed_interest}</th>
											<th align="center">${borrowRepossessed.repossessed_capital}</th>
											<th align="center">${borrowRepossessed.late_interest}</th>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
				</fieldset>
				<c:if test="${borrowRedeem.redeemStatus ==1}">
					<fieldset>
						<legend>审核</legend>
						<dl class="nowrap">
							<dt>审核状态:</dt>
							<dd>
								<input name="redeemStatus" id="redeemStatus" value="2"
									type="radio" />通过 &nbsp;&nbsp;&nbsp;&nbsp;<input
									name="redeemStatus" id="redeemStatus" checked="checked"
									value="3" type="radio" />未通过
							</dd>
						</dl>
						<dl class="nowrap">
							<dt>审核备注:</dt>
							<dd>
								<textarea class="textInput valid" name="redeemRemark"
									rows="5" cols="45"></textarea>
							</dd>
						</dl>
					</fieldset>
				</c:if>
				<c:if test="${borrowRedeem.redeemStatus !=1}">
					<fieldset>
						<legend>审核</legend>
						<dl class="nowrap">
							<dt>审核状态:</dt>
							<dd>
								<span style="color: red;">${BorrowRedeem_ALL_STATUS[borrowRedeem.redeemStatus] }</span>
							</dd>
						</dl>
						<dl class="nowrap">
							<dt>审核备注:</dt>
							<dd>
								<textarea class="textInput valid" disabled="disabled"
									readonly="readonly" rows="5" cols="45">${borrowRedeem.redeemRemark }</textarea>
							</dd>
						</dl>
					</fieldset>
				</c:if>
			</div>
			<div class="formBar">
				<ul>
					<c:if test="${borrowRedeem.redeemStatus ==1}">
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">保存</button>
								</div>
							</div>
						</li>
					</c:if>
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