<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="recommendAccountAd/${PRE_PATH_VIEW }getRecommendRAPage?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				 <table class="searchContent">
					<tr>
						
						<td>
							推荐人Id：
							<input type="text" name="rUserId" id="rUserId" value="${searchParams.rUserId}"/>
						</td>
						<td>
							推荐人用户名：
							<input type="text" name="rUserAccount" id="rUserAccount" value="${searchParams.rUserAccount}"/>
						</td>
						<td>
							推荐人姓名：
							<input type="text" name="rUserRealname" id="rUserRealname" value="${searchParams.rUserRealname}"/>
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
						<c:if
						test="${fn:contains(rightsub.moduleUrl,'recommendAccountAd/toExcelLevel')}">
						<li><a class="add" href="${rightsub.moduleUrl}?right_id=${searchParams.right_id}"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
					</c:if>
					</c:forEach>
			
			</ul> 
			</div>
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th width="4%"  align="center">序号</th>
						<th width="8%" align="center">推荐人Id</th>
						<th width="8%" align="center">推荐人用户名</th>
						<th width="8%" align="center">推荐人姓名</th>
						<th width="8%" align="center">推荐人邮箱</th>
						<th width="8%" align="center">推荐人手机</th>
						<th width="8%" align="center">级别</th>
						<th width="8%" align="center">推荐总奖励</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="recommend" items="${pm.list }">
						<tr  target="sid_support" rel="${recommend.id }">
							<th  align="center">
							    ${recommend.id}
							</th>
							<th  align="center">
							    ${recommend.rUserId}
							</th>
							<th  align="center">
								${recommend.rUserAccount}
							</th>
							<th  align="center">
								${recommend.rUserRealname}
							</th>
							<th  align="center">
							    ${recommend.rUserEmail}
							</th>
							<th  align="center">
								${recommend.rUserPhone}
							</th>
							<th  align="center">
								${recommend.levels}级
							</th>
							<th  align="center">
								${recommend.rewards}
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