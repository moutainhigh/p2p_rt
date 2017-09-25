<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="dic/nation/getNationList?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							民族名称：
							<input type="text" name="nationName" id="nationName" value="${searchParams.nationName}"/>
						</td>
						<td>
						民族代码：
							<input type="text" name="nationCode" id="nationCode" value="${searchParams.nationCode}"/>
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
					<c:if test="${fn:contains(rightsub.moduleUrl,'dic/nation/add')}">
						<li><a class="add"
							href="${rightsub.moduleUrl}?right_id=${right_id}" target="dialog"
							rel="jbsxBox-area" width="420" height="200"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
					</c:if>
					
					<c:if test="${fn:contains(rightsub.moduleUrl,'dic/nation/edit')}">
						<li><a class="edit"
							href="${rightsub.moduleUrl}?nationId={nationId}&right_id=${right_id}"
							target="dialog" rel="jbsxBox-area" width="420" height="200"><span>${rightsub.moduleName}</span>
						</a></li>
						<li class="line">line</li>
					</c:if>

					<c:if test="${fn:contains(rightsub.moduleUrl,'dic/nation/delete')}">
						<li><a class="delete"
							href="${rightsub.moduleUrl}?nationId={nationId}&right_id=${right_id}"
							target="ajaxTodo" rel="jbsxBox-area" title="确认要删除该民族？"><span>${rightsub.moduleName}</span>
						</a></li>
						<li class="line">line</li>
					</c:if>
				</c:forEach>

			</ul>
		</div>
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th width="5%"  align="center">序号</th>
						<th width="7%" align="center">民族名称</th>
						<th width="10%" align="center">民族代码</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="row" items="${pm.list }">
						<tr  target="nationId" rel="${row.id }">
							<th  align="center">
							    ${row.id}
							</th>
							<th  align="center">
							    ${row.nationName}
							</th>
							<th  align="center">
							    ${row.nationCode}
							</th>
						</tr>
						</c:forEach>
				</tbody>
			</table>
			<!-- 分页 -->
			<c:set var="page" value="${pm }"></c:set>
			<%@ include file="../../page.jsp" %>
		</div>
</form>