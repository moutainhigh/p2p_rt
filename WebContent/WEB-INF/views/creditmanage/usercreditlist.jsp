<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="credit/${PRE_PATH_VIEW }getListUserCredit?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名称:
							<input type="text" name="userAccount" value="${searchParams.userAccount}"/>
						</td>
						 <!-- <td>
							开始时间:
							<input type="text" name="startTime" class="date textInput readonly"  readonly="readonly"/>
						</td>
						<td>
							结束时间: 
							<input type="text" name="endTime" class="date textInput readonly"  readonly="readonly" />
						</td>  -->
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'credit/v_addCreditRank')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}"  target="dialog"><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						<c:if test="${fn:contains(rightsub.moduleUrl,'credit/v_getUserCredit')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}"
									target="dialog" ><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'credit/o_deleteCreditRank')}">
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
						
						
						
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'credit/v_toExcel')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}&userAccount=${searchParams.userAccount}" ><span>${rightsub.moduleName}</span></a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
					</c:forEach>
				</ul>
				<%-- <ul class="toolBar">
							<li>
								<a class="edit" href="credit/${PRE_PATH_VIEW }getUserCredit/{usrId}?rel=${rel }" target="dialog" rel="editCredit" >
								<span>修改</span>
								</a>
							</li>
				</ul> --%>
			</div>
			<table class="list" width="100%" layoutH="88">
				<thead>
					<tr>
						<th width="3%"  align="center">序号</th>
						<th width="7%" align="center">用户名称</th>
						<th width="7%" align="center">等级</th>
						<th width="7%" align="center">信用积分</th>
						<th width="7%" align="center">可用积分</th>
						<th width="7%" align="center">修改人</th>
						<th width="7%" align="center">添加IP</th>
						<th width="7%" align="center">添加时间</th>
						<th width="7%" align="center">修改IP</th>
						<th width="7%" align="center">修改时间</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="credit" items="${pm.list }">
						<tr target="sid_support" rel="${credit.id }">
							<th  align="center">
							    ${credit.id }
							</th>
							<th  align="center">
							    ${credit.uUserAccount }
							</th>
							<th  align="center">
							    <img src="<%=path %>/common/views/images/credit/${credit.rankIcon }"/>
							</th>
							<th  align="center">
							    ${credit.creditValue }
							</th>
							<th  align="center">
							    ${credit.creditValueUsable }
							</th>
							<th  align="center">
							   ${credit.operUserAccount }
							</th>
							<th  align="center">
							    ${credit.creditAddIp }
							</th>
							<th  align="center">
								${credit.creditAddTime }
							</th>
							<th  align="center">
								${credit.creditUpdateIp }
							</th>
							<th  align="center">
							   	${credit.creditUpdateTime }
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