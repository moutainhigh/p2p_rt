<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="dis/configs" method="post">
	
	<div class="pageContent">
	
		<div class="panelBar">
			<ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'dis/config/edit')}">
						<li>
							<a class="edit" href="${rightsub.moduleUrl}?configId={configId}&right_id=${searchParams.right_id}" target="dialog"
							rel="jbsxBox-dis" width="420" height="290">
								<span>${rightsub.moduleName}</span>
							</a>
						</li>
						<li class="line">line</li>
						</c:if>
						
					</c:forEach>
			</ul>
		</div>
		
		<table class="list" width="100%" layoutH="90">
			<thead>
				<tr>
					<th width="3%" align="center">序号</th>
					<th width="7%" align="center">key</th>
					<th width="10%" align="center">描述</th>
					<th width="7%" align="center">是否需要审核</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="config" items="${pm.list }">
					<tr target="configId" rel="${config.id }">
						<th align="center">${config.id }</th>
						<th align="center">${config.disKey }</th>
						<th align="center">${config.description }</th>
						<th align="center">
							<c:if test="${config.disValue eq 'YES' }">需要审核</c:if>
							<c:if test="${config.disValue eq 'NO' }">不需要审核</c:if>
						</th>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- 分页 -->
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>