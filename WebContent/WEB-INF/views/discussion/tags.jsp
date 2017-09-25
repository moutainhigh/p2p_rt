<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="dis/tags" method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<input type="hidden" name="right_id" value="${right_id}" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						标签名称: <input type="text" name="tagName" value="${searchParams.tagName}" />
					</td>
					<td>
						标签编码: <input type="text" name="tagCode" value="${searchParams.tagCode}" />
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
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'dis/tag/add')}">
						<li>
							<a class="add" href="${rightsub.moduleUrl}?right_id=${searchParams.right_id}" target="dialog"
							rel="jbsxBox-dis" width="420" height="290">
								<span>${rightsub.moduleName}</span>
							</a>
						</li>
						<li class="line">line</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'dis/tag/edit')}">
						<li>
							<a class="edit" href="${rightsub.moduleUrl}?tagId={tagId}&right_id=${searchParams.right_id}" target="dialog"
							rel="jbsxBox-dis" width="420" height="290">
								<span>${rightsub.moduleName}</span>
							</a>
						</li>
						<li class="line">line</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'dis/tag/delete')}">
						<li>
							<a class="delete" href="${rightsub.moduleUrl}?tagId={tagId}&right_id=${searchParams.right_id}" target="ajaxTodo" rel="jbsxBox-dic" title="确认要删除此标签？">
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
					<th width="5%" align="center">序号</th>
					<th width="7%" align="center">标签名称</th>
					<th width="7%" align="center">标签编码</th>
					<th width="5%" align="center">排序</th>
					<th width="7%" align="center">是否显示</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="tag" items="${pm.list }">
					<tr target="tagId" rel="${tag.id }">
						<th align="center">${tag.id }</th>
						<th align="center">${tag.tagName }</th>
						<th align="center">${tag.tagCode }</th>
						<th align="center">${tag.tagSequence }</th>
						<th align="center">
							<c:if test="${tag.tagIshidden eq 1 or empty tag.tagIshidden}">隐藏</c:if>
							<c:if test="${tag.tagIshidden eq 0}">显示</c:if>
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