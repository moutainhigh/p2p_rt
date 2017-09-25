<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="accountLog/${PRE_PATH_VIEW }getLogStatisticsPageList?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
				
					<tr>
						<td>
							用户名：
							<input type="text" name="userAccount" id="userAccount" value="${searchParams.userAccount}"/>
						</td>
						<td>
							真实姓名：
							<input type="text" name="userRealname" id="userRealname" value="${searchParams.userRealname}"/>
						</td>
						<td>
							手机号：
							<input type="text" name="userPhone" id="userPhone" value="${searchParams.userPhone}"/>
						</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										查询
									</button>
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
				<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'accountLog/v_getAccountLogListByUserAccount')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="navTab"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
						<c:if
						test="${fn:contains(rightsub.moduleUrl,'accountLog/toExcel')}">
						<li><a class="add"
							href="${rightsub.moduleUrl}?right_id=${searchParams.right_id}&&userAccount=${searchParams.userAccount}"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
					</c:if>
					</c:forEach>
			</ul>
			</div>
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th width="6%" align="center">用户名称</th>
						<th width="7%" align="center">真实姓名</th>
						<th width="7%" align="center">手机号</th>
						<th width="7%" align="center">资金总额</th>
						<th width="7%" align="center">可用金额</th>
						<th width="6%" align="center">投标奖励</th>
						<th width="6%" align="center">续投奖励</th>
						<th width="6%" align="center">借款手续费</th>
						<th width="7%" align="center">利息管理费</th>
						<th width="7%" align="center">滞纳金管理费</th>
						<th width="7%" align="center">推广邀请奖励</th>
						<th width="6%" align="center">提现成功</th>
						<th width="6%" align="center">充值</th>
						<th width="7%" align="center">线下充值奖励</th>
						<th width="8%" align="center">用户费用扣除</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="account" items="${pm.list}">
						<tr  target="sid_support" rel="${account.userId}">
							<th  align="center">
							    ${account.userAccount}
							</th>
							<th  align="center">
							    ${account.userRealname}
							</th>
							<th  align="center">
							    ${account.userPhone}
							</th>
							<th  align="center">
							    <fmt:formatNumber value="${account.allMoney}" pattern="#.##"/>
							</th>
							<th  align="center">
								<fmt:formatNumber value="${account.availableMoney}" pattern="#.##"/>
							</th>
							<th  align="center">
							<c:choose>
								<c:when test="${account['log_4'] == null }">
									0.00
								</c:when>
								<c:otherwise>
									<fmt:formatNumber value="${account['log_4']}" pattern="#.##"/>
								</c:otherwise>
							</c:choose>
							</th>
							<th  align="center">
							<c:choose>
								<c:when test="${account['log_5'] == null }">
									0.00
								</c:when>
								<c:otherwise>
									<fmt:formatNumber value="${account['log_5']}" pattern="#.##"/>
								</c:otherwise>
							</c:choose>
							</th>
							<th  align="center">
							<c:choose>
								<c:when test="${account['log_6'] == null }">
									0.00
								</c:when>
								<c:otherwise>
									<fmt:formatNumber value="${account['log_6']}" pattern="#.##"/>
								</c:otherwise>
							</c:choose>
							</th>
							<th  align="center">
							<c:choose>
								<c:when test="${account['log_8'] == null }">
									0.00
								</c:when>
								<c:otherwise>
									<fmt:formatNumber value="${account['log_8']}" pattern="#.##"/>
								</c:otherwise>
							</c:choose>
							</th>
							<th  align="center">
							<c:choose>
								<c:when test="${account['log_9'] == null }">
									0.00
								</c:when>
								<c:otherwise>
									<fmt:formatNumber value="${account['log_9']}" pattern="#.##"/>
								</c:otherwise>
							</c:choose>
							</th>
							<th  align="center">
							<c:choose>
								<c:when test="${account['log_10'] == null }">
									0.00
								</c:when>
								<c:otherwise>
									<fmt:formatNumber value="${account['log_10']}" pattern="#.##"/>
								</c:otherwise>
							</c:choose>
							</th>
							<th  align="center">
							<c:choose>
								<c:when test="${account['log_11'] == null }">
									0.00
								</c:when>
								<c:otherwise>
									<fmt:formatNumber value="${account['log_11']}" pattern="#.##"/>
								</c:otherwise>
							</c:choose>
							</th>
							<th  align="center">
							<c:choose>
								<c:when test="${account['log_13'] == null }">
									0.00
								</c:when>
								<c:otherwise>
									<fmt:formatNumber value="${account['log_13']}" pattern="#.##"/>
								</c:otherwise>
							</c:choose>
							</th>
							<th  align="center">
							<c:choose>
								<c:when test="${account['log_14'] == null }">
									0.00
								</c:when>
								<c:otherwise>
									<fmt:formatNumber value="${account['log_14']}" pattern="#.##"/>
								</c:otherwise>
							</c:choose>
							</th>
							<th  align="center">
							<c:choose>
								<c:when test="${account['log_15'] == null }">
									0.00
								</c:when>
								<c:otherwise>
									<fmt:formatNumber value="${account['log_15']}" pattern="#.##"/>
								</c:otherwise>
							</c:choose>
							</th>
						</tr>
						</c:forEach>
				</tbody>
			</table>
			<!-- 分页 -->
			<c:set var="page" value="${pm }"></c:set>
			<%@ include file="../page.jsp" %>
		</div>
</form>