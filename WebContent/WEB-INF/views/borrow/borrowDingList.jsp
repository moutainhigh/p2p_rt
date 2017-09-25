<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="borrow/${PRE_PATH_VIEW }getBorrowDingList?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<input type="hidden" name="borrowStatuss" value="${searchParams.borrowStatus}">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							发布时间：
							<input type="text" name="beginTime" id="beginTime" value="${searchParams.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
							到<input type="text" name="endTime" id="endTime" value="${searchParams.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
						</td>
						<td>
							截止投标时间：
							<input type="text" name="allowbeginTime" id="allowbeginTime" value="${searchParams.allowbeginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
							到<input type="text" name="allowendTime" id="allowendTime" value="${searchParams.allowendTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrow/v_getBorrowDingDetail')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?ids={ids}&right_id=${right_id}"
									target="dialog" rel="jbsxBox2" width="830" height="250"><span>${rightsub.moduleName}</span>
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
						<th width="5%"  align="center">序号</th>
						<th width="9%" align="center">期数</th>
						<th width="12%" align="center">发布时间</th>
						<th width="9%" align="center">截止投标时间</th>
						<th width="9%" align="center">状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="borrowR" items="${pm.list }">
						<tr  target="ids" rel="${borrowR.borrowoneId},${borrowR.borrowtwoId },${borrowR.borrowthreeId}">
							<th  align="center">
							    ${borrowR.id}
							</th>
							<th  align="center">
							    ${borrowR.relatedPeriodNum}
							</th>
							<th  align="center">
								<fmt:formatDate value="${borrowR.relatedPublishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
								<fmt:formatDate value="${borrowR.relatedAllowTenderTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
								 ${BORROW_ALL_STATUS[borrowR.status] }
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