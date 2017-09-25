<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="log/${PRE_PATH_VIEW }getOperatorLogList" method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>用户名称: <input type="text" name="userAccount"
						value="${userAccount}" />
					</td>
					<td>日志类别: <select name="operatorCategory">
							<option value=""
								<c:if test="${operatorCategory==null}">selected="selected"</c:if>>全部</option>
							<option value="1"
								<c:if test="${operatorCategory==1}">selected="selected"</c:if>>登录退出</option>
							<option value="2"
								<c:if test="${operatorCategory==2}">selected="selected"</c:if>>借款管理</option>
							<option value="3"
								<c:if test="${operatorCategory==3}">selected="selected"</c:if>>扣费</option>
							<option value="7"
								<c:if test="${operatorCategory==7}">selected="selected"</c:if>>角色权限</option>
							<option value="8"
								<c:if test="${operatorCategory==8}">selected="selected"</c:if>>认证审核</option>
							<option value="9"
								<c:if test="${operatorCategory==9}">selected="selected"</c:if>>充值</option>
							<option value="10"
								<c:if test="${operatorCategory==10}">selected="selected"</c:if>>银行卡</option>
							<option value="11"
								<c:if test="${operatorCategory==11}">selected="selected"</c:if>>提现</option>
							<option value="12"
								<c:if test="${operatorCategory==12}">selected="selected"</c:if>>认证申请</option>
							<option value="13"
								<c:if test="${operatorCategory==13}">selected="selected"</c:if>>个人资料</option>
					</select>
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
					<th width="5%" align="center">状态码</th>
					<th width="7%" align="center">时间</th>
					<th width="7%" align="center">IP</th>
					<th width="7%" align="center">操作人</th>
					<th width="7%" align="center">操作参数</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach var="operator" items="${pm.list }">
					<tr target="usrId" rel="${operator.id }">
						<th align="center">${operator.id }</th>
						<th align="center">${operator.operatorTitle }</th>
						<th align="center">${operator.operatorReturn }</th>
						<th align="center">${operator.operatorStatus }</th>
						<th align="center"><fmt:formatDate
								value="${operator.createTime }" pattern="yyyy-MM-dd  HH:mm:ss" />
						</th>
						<th align="center">${operator.operatorIp }</th>
						<th align="center">${operator.logUserid}</th>
						<th align="center" title="${operator.operatorParams}"
							style="word-break: break-all; overflow: auto;"><c:choose>
								<c:when test="${fn:length(operator.operatorParams) > 50}">
									<c:out
										value="${fn:substring(operator.operatorParams, 0, 50)}...." />
								</c:when>
								<c:otherwise>
									<c:out value="${operator.operatorParams}" />
								</c:otherwise>
							</c:choose></th>

					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- 分页 -->
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>