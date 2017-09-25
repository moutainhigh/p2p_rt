<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="credit/${PRE_PATH_VIEW }getListCreditLog?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名称:
							<input type="text" name="userAccount" value="${searchParams.userAccount }"/>
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
							<c:if test="${fn:contains(rightsub.moduleUrl,'credit/v_toExcelCreditLog')}">
								<li>
									<a class="add" href="${rightsub.moduleUrl}?right_id=${right_id}&userAccount=${searchParams.userAccount}"  
									  width="800" height="600"><span>${rightsub.moduleName}</span></a>
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
						<th width="3%"  align="center">序号</th>
						<th width="7%" align="center">用户名称</th>
						<th width="7%" align="center">积分类型</th>
						<th width="7%" align="center">变动类型</th>
						<th width="7%" align="center">变动积分</th>
						<th width="7%" align="center">时间</th>
						<th width="7%" align="center">IP</th>
						<th width="7%" align="center">操作人</th>
						<th width="7%" align="center">备注</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="credit" items="${pm.list }">
						<tr target="usrId" rel="${credit.id }">
							<th  align="center">
							    ${credit.id }
							</th>
							<th  align="center">
							<c:if test="${not empty credit.user }">
							    ${credit.user.userAccount }
							 </c:if>
							</th>
							<th  align="center">
							    ${credit.creditType.creditName }
							</th>
							<th  align="center">
								<c:if test="${credit.creditOperateType==1 }">
									增加
								</c:if>
								<c:if test="${credit.creditOperateType==2 }">
									减少
								</c:if>
							</th>
							<th  align="center">
							    ${credit.creditOperateValue }
							</th>
							<th  align="center">
								<fmt:formatDate value="${credit.creditOperateDatetime }" pattern="yyyy-MM-dd  HH:mm:ss" />
							</th>
							<th  align="center">
								${credit.creditOperateIp }
							</th>
							<th  align="center">
							<c:if test="${not empty credit.operUser }">
							   	${credit.operUser.userAccount }
							   	</c:if>
							</th>
							<th  align="center">
							   	${credit.creditOperateRemark }
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