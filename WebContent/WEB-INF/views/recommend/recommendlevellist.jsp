<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="recommend/${PRE_PATH_VIEW }getRecommendsLists?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				 <table class="searchContent">
					<tr>
						<td>
							员工内部号：
							<input type="text" name="rtUserFlag" id="rtUserFlag" value="${searchParams.rtUserFlag}"/>
						</td>
						<td>
							推荐人Id：
							<input type="text" name="id" id="id" value="${searchParams.id}"/>
						</td>
						<td>
							推荐人用户名：
							<input type="text" name="userAccount" id="userAccount" value="${searchParams.userAccount}"/>
						</td>
						<td>
							推荐人姓名：
							<input type="text" name="userRealname" id="userRealname" value="${searchParams.userRealname}"/>
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
						test="${fn:contains(rightsub.moduleUrl,'recommend/toExcelLevel')}">
						<li><a class="add"
							href="${rightsub.moduleUrl}?right_id=${searchParams.right_id}&&beginTime=${searchParams.beginTime}&&endTime=${searchParams.endTime}&&userAccount=${searchParams.userAccount}&&recommendUser=${searchParams.recommendUser}&&recommendStatus=${searchParams.recommendStatus}"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
					</c:if>
						<c:if
						test="${fn:contains(rightsub.moduleUrl,'recommend/v_getSubRecommendsLists')}">
						<li>
							<a class="edit" href="${rightsub.moduleUrl}?right_id=${right_id}&recommendUserId={sid_support}"
									target="navTab" ><span>${rightsub.moduleName}</span>
								</a>
						</li>
						<li class="line">line</li>
					</c:if>
					
					<c:if
						test="${fn:contains(rightsub.moduleUrl,'recommend/v_getSubRecommendLevl1sLists')}">
						<li>
							<a class="edit" href="${rightsub.moduleUrl}?right_id=${right_id}&recommendUserId={sid_support}"
									target="navTab" ><span>${rightsub.moduleName}</span>
								</a>
						</li>
						<li class="line">line</li>
					</c:if>
					<c:if
						test="${fn:contains(rightsub.moduleUrl,'recommend/v_getSubRecommendLevl2sLists')}">
						<li>
							<a class="edit" href="${rightsub.moduleUrl}?right_id=${right_id}&recommendUserId={sid_support}"
									target="navTab" ><span>${rightsub.moduleName}</span>
								</a>
						</li>
						<li class="line">line</li>
					</c:if>
					<c:if
						test="${fn:contains(rightsub.moduleUrl,'recommend/v_getSubRecommendLevl3sLists')}">
						<li>
							<a class="edit" href="${rightsub.moduleUrl}?right_id=${right_id}&recommendUserId={sid_support}"
									target="navTab" ><span>${rightsub.moduleName}</span>
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
						<th width="8%" align="center">推荐人Id</th>
						<th width="8%" align="center">推荐人用户名</th>
						<th width="8%" align="center">内部员工号</th>
						<th width="8%" align="center">推荐人姓名</th>
						<th width="8%" align="center">推荐人手机</th>
						<th width="8%" align="center">推荐人邮箱</th>
						<!-- <th width="8%" align="center">一级会员(个)</th>
						 <th width="8%" align="center">二级会员(个)</th>
						<th width="8%" align="center">三级会员(个)</th>   -->
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
							    ${recommend.userAccount}
							</th>
							<th  align="center">
							    ${recommend.rtUserFlag}
							</th>
							<th  align="center">
								${recommend.userRealname}
							</th>
							<th  align="center">
								${recommend.userPhone}
							</th>
							<th  align="center">
							    ${recommend.userEmail}
							</th>
							<%--  <th  align="center">
								${recommend.levl1}
							</th>
							 <th  align="center">
								${recommend.levl2}
							</th>
							<th  align="center">
								${recommend.levl3}
							</th>   --%>
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