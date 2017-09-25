<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="log/${PRE_PATH_VIEW }getFrontLogList" method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>用户名称: <input type="text" name="userAccount" value="${userAccount}" />
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
		<table class="list" width="100%" layoutH="60">
			<thead>
				<tr>
					<th width="5%" align="center">序号</th>
					<th width="7%" align="center">操作名称</th>
					<th width="7%" align="center">操作结果</th>
					<th width="7%" align="center">时间</th>
					<th width="7%" align="center">IP</th>
					<th width="7%" align="center">操作人</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="operator" items="${pm.list }">
					<tr target="usrId" rel="${operator.id }">
						<th align="center">${operator.id }</th>
						<th align="center">${operator.operatorTitle }</th>
						<th align="center">${operator.operatorReturn }</th>
						<th align="center"><fmt:formatDate
								value="${operator.createTime }" pattern="yyyy-MM-dd  HH:mm:ss" />
						</th>
						<th align="center">${operator.operatorIp }</th>
						<th align="center">${operator.logUserid}</th>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- 分页 -->
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>