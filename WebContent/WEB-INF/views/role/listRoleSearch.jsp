<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:if test="${code==1}">
	<div class="pageContent" style="">
	<div class="tabs">	
		<div class="tabsContent">
			<div>
				<div layoutH="14" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">      				       
				    <ul class='tree treeFolder collapse'>
				    <li>
				    	<a href="role/v_getRoleListInfo?right_id=15&roleId=${roleId}" target="ajax" rel="roleBox" >根目录</a>
				    	 ${requestScope.roleList }
				     </li>
				     </ul>
				     
				</div>
				<div layoutH="14" id="roleBox" class="unitBox" style="margin-left:246px;background:#fff;">
					<!--#include virtual="list1.html"-->
					
	<c:if test="${code==1}">
		<form id="pagerForm" onsubmit="return divSearch(this, 'roleBox')"
		action="role/v_getRoleListInfo?right_id=${right_id}&roleId=${roleId}"
		method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							角色名称:
							<input type="text" name="role_name" value="${role_name }" />
							
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'role/o_saveRole')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}&type=toJsp"  target="navTab"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'role/o_updateRole')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}&type=toJsp"
									target="navTab" rel="jbsxBox"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'role/o_deleteRole')}">
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'role/v_showRoleModuleList')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}&type=toJsp"
									target="navTab"  rel="jbsxBox"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'role/v_showUserListByRoleId')}">
							<li>
								<a class="edit"
									href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="dialog" rel="jbsxBox" width="280" height="470"><span>${rightsub.moduleName}</span>
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
						<th width="10%" align="center">角色名称</th>
						<th width="20%" align="center">角色描述</th>
						<th width="40%" align="center">注意事项</th>
						<th width="20%" align="center">添加时间</th>
						<th width="20%" align="center">IPAddress</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pm.list }" var="role" varStatus="st">
						<tr target="sid_support" rel="${role.id}">
							<th  align="center">
							   ${pm.pageSize * (pm.currentPage-1) + st.index + 1}
							</th>
							<th  align="center">
							   	${role.roleName }
							</th>
							<th  align="center">
							   	${role.roleSummary }
							</th>
							<th  align="center"  style="color: red;">
							   	${role.roleRemark }
							</th>
							<th  align="center">
							   <fmt:formatDate value="${role.roleAddtime  }" pattern="yyyy-MM-dd  HH:mm:ss" />
							</th>
							<th  align="center">
							   	${role.roleAddip }
							</th>
						</tr>
						</c:forEach>
				</tbody>
			</table>
			<!-- 分页 -->
			<c:set var="page" value="${pm }"></c:set>
			<c:set var="pagedRel" value="roleBox"></c:set>
			<%@ include file="../page.jsp" %>
		</div>
		</form>
	</c:if>
	<c:if test="${code==0}">
		${message}
	</c:if>
					
				</div>
				
			</div>
			
			
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
</div>

</c:if>
<c:if test="${code==0}">
	${message}
</c:if>