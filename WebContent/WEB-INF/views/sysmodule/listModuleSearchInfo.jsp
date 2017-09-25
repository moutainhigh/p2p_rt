<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:if test="${code==1}">

	<form id="pagerForm" onsubmit="return divSearch(this, 'moduleBox')"
		action="module/v_getModuleListInfo?right_id=${right_id}&moduleId=${moduleId}"
		method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							权限名称:
							<input type="text" name="module_name" value="${module_name}" />
							
						</td>
						<td>(支持模糊查询)</td>
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'module/o_saveSysModule')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}&type=toJsp"  target="navTab"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'module/o_updateSysModule')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}&type=toJsp"
									target="navTab" rel="jbsxBox"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'module/o_deleteSysModule')}">
							<li>
								<a class="delete"
									href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="ajaxTodo" rel="jbsxBox"  title="确认要删除该权限吗？"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<table class="list" width="100%" layoutH="105">
				<thead>
					<tr>
						<th width="10%"  align="center">序号</th>
						<th width="10%" align="center">菜单名称</th>
						<th width="20%" align="center">菜单路径</th>
						<th width="20%" align="center">菜单描述</th>
						<th width="20%" align="center">排序</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pm.list }" var="rightModule" varStatus="st">
						<tr target="sid_support" rel="${rightModule.id}">
							<th  align="center">
							   ${pm.pageSize * (pm.currentPage-1) + st.index + 1}
							</th>
							<th  align="center">
							   	${rightModule.moduleName }
							</th>
							<th  align="center">
							   	${rightModule.moduleUrl }
							</th>
							<th  align="center">
							   	${rightModule.moduleDescribe }
							</th>
							<th  align="center">
							   	${rightModule.moduleSequence }
							</th>
						</tr>
						</c:forEach>
				</tbody>
			</table>
			<!-- 分页 -->
			<c:set var="page" value="${pm }"></c:set>
			<c:set var="pagedRel" value="moduleBox"></c:set>
			<%@ include file="../page.jsp" %>
		</div>
	</form>
</c:if>
<c:if test="${code==0}">
	${message}
</c:if>