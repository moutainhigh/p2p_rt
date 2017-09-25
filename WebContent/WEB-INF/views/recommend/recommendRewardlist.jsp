<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="recommendRewardAd/${PRE_PATH_VIEW }getRecommendRAPage?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				 <table class="searchContent">
					<tr>
						
						<td>
							推荐人Id：
							<input type="text" name="recommendUserId" id="recommendUserId" value="${searchParams.recommendUserId}"/>
						</td>
						<td>
							推荐人用户名：
							<input type="text" name="rUserAccount" id="rUserAccount" value="${searchParams.rUserAccount}"/>
						</td>
						<td>
							推荐人姓名：
							<input type="text" name="rUserRealname" id="rUserRealname" value="${searchParams.rUserRealname}"/>
							
							&nbsp;&nbsp;&nbsp;添加时间：
							<input type="text" name="beginTime" id="beginTime" value="${searchParams.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
							到<input type="text" name="endTime" id="endTime" value="${searchParams.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
						</td>
						
					</tr>
					
					<tr>
						<td>
							被推荐人用户名：
							<input type="text" name="lUserAccount" id="lUserAccount" value="${searchParams.lUserAccount}"/>
						</td>
					<%-- 	 --%>
						<td>
							被推荐人姓名：
							<input type="text" name="lUserRealname" id="lUserRealname" value="${searchParams.lUserRealname}"/>
						</td>
						<td>
							
							被推荐人手机号：
							<input type="text" name="lUserPhone" id="lUserPhone" value="${searchParams.lUserPhone}"/>
							&nbsp;&nbsp;&nbsp;被推荐人Id：
							<input type="text" name="levelUserId" id="levelUserId" value="${searchParams.levelUserId}"/>
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
						test="${fn:contains(rightsub.moduleUrl,'recommendRewardAd/toExcelLevel')}">
						<li><a class="add" href="${rightsub.moduleUrl}?right_id=${searchParams.right_id}"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
						</c:if>
					</c:forEach>
			
			</ul>
			</div>
			<table class="list" width="100%" layoutH="113">
				<thead>
					<tr>
						<th width="4%"  align="center">序号</th>
						<th width="8%" align="center">推荐人Id</th>
						<th width="8%" align="center">推荐人用户名</th>
						<th width="8%" align="center">推荐人姓名</th>
						<th width="8%" align="center">被推荐人Id</th>
						<th width="8%" align="center">被推荐人用户名</th>
						<th width="8%" align="center">被推荐人手机号</th>
						<th width="9%" align="center">被推荐人姓名</th>
						<th width="8%" align="center">获得奖励金额</th>
						<th width="9%" align="center">下线用户本次收益</th>
						<th width="10%" align="center">下线用户本次投标本金</th>
						<th width="4%" align="center">来源</th>
						<th width="8%" align="center">添加时间</th>
						<th width="8%" align="center">添加IP</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="recommend" items="${pm.list }">
						<tr  target="sid_support" rel="${recommend.id }">
							<th  align="center">
							    ${recommend.id}
							</th>
							<th  align="center">
							    ${recommend.recommendUserId}
							</th>
							<th  align="center">
								${recommend.rUserAccount}
							</th>
							<th  align="center">
								${recommend.rUserRealname}
							</th>
							<th  align="center">
							    ${recommend.levelUserId}
							</th>
							<th  align="center">
								${recommend.lUserAccount}
							</th>
							<th  align="center">
								${recommend.lUserPhone}
							</th>
							<th  align="center">
								${recommend.lUserRealname}
							</th>
							<th  align="center">
								${recommend.recommendUserReward}
							</th>
							<th  align="center">
								${recommend.levelUserReward}
							</th>
							<th  align="center">
							    ${recommend.tenderMoney}
							</th>
							<th  align="center">
							    ${recommend.recommendLevel}级
							</th>
							<th  align="center">
							   <fmt:formatDate value="${recommend.addTimes}" pattern="yyyy-MM-dd"/> 
							</th>
							<th  align="center">
							    ${recommend.addIp}
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