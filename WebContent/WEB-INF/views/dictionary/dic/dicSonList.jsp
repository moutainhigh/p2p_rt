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

<form id="pagerForm" onsubmit="return divSearch(this, 'jbsxBox-dic');"
	action="dic/getSonList?right_id=${right_id}" method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0> 
		<input type="hidden" name="right_id" value="${right_id}" /> 
		<input type="hidden" name="parentId" value="${parentId }" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>字典名称: <input type="text" name="dicName" id="dicName"
						value="${searchParams.dicName }" />
					</td>
					<td>
							字典代码：
							<input type="text" name="dicCode" id="dicCode" value="${searchParams.dicCode}"/>
					</td>
					<td>&nbsp;&nbsp;&nbsp;</td>
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
					<c:if test="${fn:contains(rightsub.moduleUrl,'dic/add')}">
						<li><a class="add"
							href="${rightsub.moduleUrl}?right_id=${right_id}" target="dialog"
							rel="jbsxBox-dic" width="510" height="460"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
					</c:if>
					
					<c:if test="${fn:contains(rightsub.moduleUrl,'dic/edit')}">
						<li><a class="edit"
							href="${rightsub.moduleUrl}?dicId={dicId}&right_id=${right_id}&pid=${parentId}"
							target="dialog" rel="jbsxBox-dic" width="510" height="560"><span>${rightsub.moduleName}</span>
						</a></li>
						<li class="line">line</li>
					</c:if>

					<c:if test="${fn:contains(rightsub.moduleUrl,'dic/delete')}">
						<li><a class="delete"
							href="${rightsub.moduleUrl}?dicId={dicId}&right_id=${right_id}"
							target="ajaxTodo" rel="jbsxBox-dic" title="确认要删除网站字典？"><span>${rightsub.moduleName}</span>
						</a></li>
						<li class="line">line</li>
					</c:if>
				</c:forEach>

			</ul>
		</div>
		<table class="list" width="100%" layoutH="105" fetchSize="200">
			<thead>
				<tr>
					<th width="2%" align="center">序&nbsp;&nbsp;号</th>
					<th width="6%" align="center">字典名称</th>
					<th width="4%" align="center">字典代码</th>
					<th width="10%" align="center">链接</th>
					<th width="10%" align="center">附件地址</th>
					<th width="2%" align="center">排序</th>
					<th width="4%" align="center">是否显示</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="dic" items="${pm.list }">
					<tr target="dicId" rel="${dic.id }">
						<th align="center">${dic.id }</th>
						<th align="center">${dic.dicName }</th>
						<th align="center">${dic.dicCode }</th>
						<th align="center">${dic.dicUrl }</th>
						<th align="center">${dic.dicAttach }</th>
						<th align="center">${dic.dicSequence }</th>
						<th align="center">
							<c:if test="${dic.dicIshidden eq 0 }">
								不显示
							</c:if>
							<c:if test="${dic.dicIshidden eq 1 }">
								显示
							</c:if>
						</th>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- 分页 -->
		<c:set var="page" value="${pm }"></c:set>
		<c:set var="pagedRel" value="jbsxBox-dic"></c:set>
		<%@ include file="../../page.jsp"%>
	</div>
</form>
