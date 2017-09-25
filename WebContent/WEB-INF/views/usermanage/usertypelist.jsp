<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="credit/${PRE_PATH_VIEW }getListUserType?right_id=${right_id}" method="post">
		<div class="pageContent">
			<div class="panelBar">
			  <ul class="toolBar">
					<c:forEach items="${righSubtList}" var="rightsub">
						<c:if test="${fn:contains(rightsub.moduleUrl,'user/v_get1UserType')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}"  target="dialog"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'user/v_getUserType')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="dialog" ><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'user/o_deleteUserType')}">
							<li>
								<a class="delete"
									href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="ajaxTodo" rel="jbsxBox"  title="确认要删除该用户类型吗？"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			<table class="list" width="100%" layoutH="52">
				<thead>
					<tr>
						<th width="3%"  align="center">序号</th>
						<th width="3%"  align="center">类型</th>
						<th width="7%" align="center">类型名称</th>
						<th width="7%" align="center">状态</th>
						<th width="7%" align="center">排序</th>
						<th width="30%" align="center">简要说明</th>
						<th width="30%" align="center">备注</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="usertype" items="${pm.list }">
						<tr target="sid_support" rel="${usertype.id }">
							<th  align="center">
							    ${usertype.id }
							</th>
							<th  align="center">
							    <c:if test="${usertype.type==0 }">后台</c:if>
							    <c:if test="${usertype.type==1 }">前台</c:if>
							</th>
							<th  align="center">
							    ${usertype.name }
							</th>
							<th  align="center">
								<c:if test="${usertype.status==1 }">开启</c:if>
								<c:if test="${usertype.status==2 }">关闭</c:if>
							</th>
							<th  align="center">
							    ${usertype.sort }
							</th>
							<th  align="center">
								${usertype.briefly }
							</th>
							<th  align="center">
								${usertype.remark }
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