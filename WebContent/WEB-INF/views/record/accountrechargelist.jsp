<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="accountRecharge/${PRE_PATH_VIEW }getAccountRechargeList?right_id=${right_id}"
	method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>用户名： <input type="text" name="userAccount"
						id="ua" value="${searchParams.userAccount}" />
					</td>
					 <td>手机号： <input type="text" name="userPhone"
						id="userPhone" value="${searchParams.userPhone}" />
					</td> 
					<td>状态： <select name="rechargeStatus" id="rs">
							<option value="">全部</option>
							<c:forEach var="item"
								items="${ACCOUNTRECHARGE_ALL_RECHAREGE_STATUS}">
								<option value="${item.key}">${item.value}</option>
							</c:forEach>
					</select>
					</td>
					<td>类型： <select name="rechargeType" id="rt">
							<option value="">全部</option>
							<c:forEach var="item"
								items="${ACCOUNTRECHARGE_ALL_RECHAREGE_TYPE}">
								<option value="${item.key}">${item.value}</option>
							</c:forEach>
					</select>
					</td>
					<td>充值时间： <input type="text" name="beginTime" id="beginTime"
						value="${searchParams.beginTime}" class="date textInput readonly"
						datefmt="yyyy-MM-dd" readonly="readonly" /> 到<input type="text"
						name="endTime" id="endTime" value="${searchParams.endTime}"
						class="date textInput readonly" datefmt="yyyy-MM-dd"
						readonly="readonly" />
					</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
			<script type="text/javascript">
				var queryStatus = "${searchParams.rechargeStatus}";
				if(queryStatus){
					$("select[name='rechargeStatus']").val(queryStatus);
				}
				var queryType = "${searchParams.rechargeType}";
				if(queryType){
					$("select[name='rechargeType']").val(queryType);
				}
				</script>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<c:forEach items="${righSubtList}" var="rightsub">
					<c:if
						test="${fn:contains(rightsub.moduleUrl,'accountRecharge/v_getAccountRechargeById')}">
						<li><a class="edit"
							href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
							target="dialog" width="750" height="500"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
					</c:if>
					<c:if
						test="${fn:contains(rightsub.moduleUrl,'accountRecharge/toExcel')}">
						<li>
							<a class="add" href="${rightsub.moduleUrl}?right_id=${searchParams.right_id}&&beginTime=${searchParams.beginTime}&&endTime=${searchParams.endTime}&&userAccount=${searchParams.userAccount}&&userRealname=${searchParams.userRealname}&&rechargeStatus=${searchParams.rechargeStatus}&&rechargeType=${searchParams.rechargeType}"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
					</c:if>
				</c:forEach>

			</ul>
		</div>
	
		<table class="list" width="100%" layoutH="90">
			<thead>
				<tr>
					<th width="5%" align="center">序号</th>
					<th width="9%" align="center">订单号</th>
					<th width="7%" align="center">用户名称</th>
					<th width="7%" align="center">真实姓名</th>
					<th width="7%" align="center">手机号</th>
					<th width="9%" align="center">类型</th>
					<th width="7%" align="center">所属银行</th>
					<th width="7%" align="center">充值金额</th>
					<th width="7%" align="center">费用</th>
					<th width="7%" align="center">到账金额</th>
					<th width="11%" align="center">充值时间</th>
					<th width="7%" align="center">状态</th>
					<th width="7%" align="center">银行返回</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="recharge" items="${pm.list }">
					<tr target="sid_support" rel="${recharge.id }">
						<th align="center">${recharge.id}</th>
						<th align="center">${recharge.rechargeTradeNo}</th>
						<th align="center">${recharge.userAccount}</th>
						<th align="center">${recharge.userRealname}</th>
						<th align="center">${recharge.userPhone}</th>
						<th align="center">
							${ACCOUNTRECHARGE_ALL_RECHAREGE_TYPE[recharge.rechargeType]}</th>
						<th align="center">${recharge.rechargePayment}</th>
						<th align="center">${recharge.rechargeMoney}</th>
						<th align="center">${recharge.rechargeFee}</th>
						<th align="center">${recharge.rechargeMoney - recharge.rechargeFee}
						</th>
						<th align="center"><fmt:formatDate
								value="${recharge.rechargeAddtime}"
								pattern="yyyy-MM-dd HH:mm:ss" /></th>
						<th align="center">
							${ACCOUNTRECHARGE_ALL_RECHAREGE_STATUS[recharge.rechargeStatus]}
						</th>
						<th align="center">${recharge.rechargeReturn}</th>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- 分页 -->
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>