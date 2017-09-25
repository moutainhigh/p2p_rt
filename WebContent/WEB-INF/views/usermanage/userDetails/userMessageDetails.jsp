<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="userDetail/${PRE_PATH_VIEW }getListUserMessage?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名称:
							<input type="text" name="userAccount" value="${searchParams.userAccount }"/>
						</td>
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'userDetail/v_getUserMessage')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={userId}&right_id=${right_id}"
									target="navTab"  width="800" height="630" rel="jbsxBox"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'userDetail/v_toExcel')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?supportID={userId}&right_id=${right_id}&userAccount=${searchParams.userAccount}"
								  width="800" height="630" rel="jbsxBox"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
					</c:forEach>
				</ul>
				
			</div>
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th  align="center">序号</th>
						<th  align="center">用户名称</th>
						<th  align="center">真实姓名</th>
						<th align="center">房产资料</th>
						<th align="center">单位资料</th>
						<th align="center">私营业主资料</th>
						<th align="center">财务状况</th>
						<th align="center">联系方式</th>
						<th align="center">配偶资料</th>
						<th align="center">教育背景</th>
						<th  align="center">其他</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${pm.list }" >
						<tr target="userId" rel="${user.id }">
							<th  align="center">
							    ${user.id }
							</th>
							<th  align="center">
								${user.userAccount }
							</th>
							<th  align="center">
							    ${user.userRealName }
							</th>
							<th  align="center">
								${user.hMsg }
							</th>
							<th  align="center">
								${user.unMsg }
							</th>
							<th  align="center">
								${user.pbMsg }
							</th>
							<th  align="center">
								${user.fMsg }
							</th>
							<th  align="center">
								${user.rMsg }
							</th>
							<th  align="center">
								${user.sMsg }
							</th>
							<th  align="center">
								${user.eMsg }
							</th>
							<th  align="center">
								${user.otherMsg }
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