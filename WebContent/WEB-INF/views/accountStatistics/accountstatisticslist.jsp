<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="accountStatistics/${PRE_PATH_VIEW }getAccountStatisticsList?right_id=${right_id}"
	method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>用户名： <input type="text" name="userName" id="userName"
						value="${searchParams.userName}" />
					</td>
					<td>真实姓名： <input type="text" name="userRealname"
						id="userRealname" value="${searchParams.userRealname}" />
					</td>
					<td>起始时间: <input type="text" name="beginTime" id="beginTime"
						value="${searchParams.beginTime}" class="date textInput readonly"
						datefmt="yyyy-MM-dd" readonly="readonly" maxDate="{%y}-%M-{%d}" />
					</td>
					<td>截止时间: <input type="text" name="endTime" id="endTime"
						value="${searchParams.endTime}" class="date textInput readonly"
						datefmt="yyyy-MM-dd" readonly="readonly" maxDate="{%y}-%M-{%d}" />
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查询</button>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="pageContent">

		<div class="panelBar">
			<ul class="toolBar">
				<c:forEach items="${rightSubList}" var="rightsub">
					<c:if
						test="${fn:contains(rightsub.moduleUrl,'accountStatistics/toExcel')}">
						<li><a class="add"
							href="${rightsub.moduleUrl}?right_id=${searchParams.right_id}&&beginTime=${searchParams.beginTime}&&endTime= ${searchParams.endTime}&&userName=${searchParams.userName}&&userRealname=${searchParams.userRealname}"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>

		<table class="list" width="1330px" layoutH="90">
			<thead>
				<tr>
					<th width="2%" align="center">序号</th>
					<th width="3%" align="center">统计时间</th>
					<th width="3%" align="center">用户名</th>
					<th width="4%" align="center">真实姓名</th>
					<th width="4%" align="center">总资金</th>
					<th width="4%" align="center">可用资金</th>
					<th width="4%" align="center">冻结资金</th>
					<th width="4%" align="center">待收资金</th>
					<th width="4%" align="center">待还金额</th>
					<th width="4%" align="center">待收利息</th>
					<th width="4%" align="center">待还利息</th>
					<th width="4%" align="center">总支出奖励</th>
					<th width="4%" align="center">总支出利息</th>
					<th width="4%" align="center">总获得奖励</th>
					<th width="4%" align="center">总获得利息</th>
					<th width="4%" align="center">净总额</th>
					<th width="4%" align="center">投标总额</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="statistics" items="${pm.list }">
					<tr target="sid_support" rel="${statistics.id }">
						<th align="center">${statistics.id}</th>
						<th align="center"><fmt:formatDate
								value="${statistics.statisticsAddtime}" pattern="yyyy-MM-dd " />
						</th>
						<th align="center">${statistics.userName}</th>
						<th align="center">${statistics.userRealname}</th>
						<th align="center"><fmt:formatNumber
								value="${statistics.allMoney}" pattern="#.##" /></th>
						<th align="center"><fmt:formatNumber
								value="${statistics.availableMoney}" pattern="#.##" /></th>
						<th align="center"><fmt:formatNumber
								value="${statistics.unavailableMoney}" pattern="#.##" /></th>
						<th align="center"><fmt:formatNumber
								value="${statistics.waitRepossessedCapital}" pattern="#.##" /></th>
						<th align="center"><fmt:formatNumber
								value="${statistics.waitRepayCapital}" pattern="#.##" /></th>
						<th align="center"><fmt:formatNumber
								value="${statistics.waitRepossessedInterest}" pattern="#.##" />
						</th>
						<th align="center"><fmt:formatNumber
								value="${statistics.waitRepayInterest}" pattern="#.##" /></th>
						<th align="center"><fmt:formatNumber
								value="${statistics.payReward}" pattern="#.##" /></th>
						<th align="center"><fmt:formatNumber
								value="${statistics.payInterest}" pattern="#.##" /></th>
						<th align="center"><fmt:formatNumber
								value="${statistics.getReward}" pattern="#.##" /></th>
						<th align="center"><fmt:formatNumber
								value="${statistics.getInterest}" pattern="#.##" /></th>
						<th align="center"><fmt:formatNumber
								value="${statistics.worthMoney}" pattern="#.##" /></th>
						<th align="center"><fmt:formatNumber
								value="${statistics.tenderAmount}" pattern="#.##" /></th>

					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- 分页 -->
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>