<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<form id="pagerForm" onsubmit="return divSearch(this, 'jbsxBox-area');"
	action="area/getSonList?right_id=${right_id}" method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0> 
		<input type="hidden" name="right_id" value="${right_id}" /> 
		<input type="hidden" name="parentId" value="${parentId }" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>地区名称: <input type="text" name="areaName" id="areaName"
						value="${searchParams.areaName }" />
					</td>
					<td>
						地区代码：
							<input type="text" name="areaCode" id="areaCode" value="${searchParams.areaCode}"/>
					</td>
					<%-- <td>
						<select name="parentId">
							<option value="">全部</option>
							<option value="${parentId }">当前目录</option>
						</select>
					</td>	 --%>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查 询</button>
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
					<c:if test="${fn:contains(rightsub.moduleUrl,'area/add')}">
						<li><a class="add"
							href="${rightsub.moduleUrl}?right_id=${right_id}" target="dialog"
							rel="jbsxBox-area" width="410" height="360"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
					</c:if>
					
					<c:if test="${fn:contains(rightsub.moduleUrl,'area/edit')}">
						<li><a class="edit"
							href="${rightsub.moduleUrl}?areaId={areaId}&right_id=${right_id}&pid=${parentId}"
							target="dialog" rel="jbsxBox-area" width="410" height="360"><span>${rightsub.moduleName}</span>
						</a></li>
						<li class="line">line</li>
					</c:if>

					<c:if test="${fn:contains(rightsub.moduleUrl,'area/delete')}">
						<li><a class="delete"
							href="${rightsub.moduleUrl}?areaId={areaId}&right_id=${right_id}"
							target="ajaxTodo" rel="jbsxBox-area" title="确认要删除地区？"><span>${rightsub.moduleName}</span>
						</a></li>
						<li class="line">line</li>
					</c:if>
				</c:forEach>

			</ul>
		</div>
		<table class="list" width="100%" layoutH="105" fetchSize="200">
			<thead>
				<tr>
					<th width="10%" align="center">序&nbsp;&nbsp;号</th>
					<th width="10%" align="center">地区名称</th>
					<th width="10%" align="center">地区代码</th>
					<th width="10%" align="center">域名</th>
					<th width="10%" align="center">排序</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="area" items="${pm.list }">
					<tr target="areaId" rel="${area.id }">
						<th align="center">${area.id }</th>
						<th align="center">${area.areaName }</th>
						<th align="center">${area.areaCode }</th>
						<th align="center">${area.areaDomain }</th>
						<th align="center">${area.areaSequence }</th>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- 分页 -->
		<c:set var="page" value="${pm }"></c:set>
		<c:set var="pagedRel" value="jbsxBox-area"></c:set>
		<%@ include file="../../page.jsp"%>
	</div>
</form>
