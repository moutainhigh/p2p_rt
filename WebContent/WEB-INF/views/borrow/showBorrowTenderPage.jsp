<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="borrow/${PRE_PATH_VIEW }getBorrowTenderPage?right_id=${right_id}" method="post">
		<input type="hidden" value="${supportID }" name="supportID">
		<div class="pageContent">
			<div class="panelBar">
			<ul class="toolBar">
					
							<li>
								<a class="edit" href="borrow/v_borrowTenderPageToExcel?supportID=${supportID }" ><span>导出</span></a>
							</li>
							<li class="line">
								line
							</li> 
				</ul>
			</div>
			<table class="list" width="100%" layoutH="50">
				<thead>
					<tr>
						<th width="10%"  align="center">序号</th>
						<th width="25%" align="center">投资人</th>
						<th width="25%" align="center">投资金额</th>
						<th width="20%" align="center">投资时间</th>
						<th width="20%" align="center">状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="borrowTender" items="${pm.list }">
						<tr  target="sid_support" rel="${borrowTender.id }">
							<th  align="center">
							    ${borrowTender.id}
							</th>
							<th  align="center">
							    ${borrowTender.investorAccount}
							</th>
							<th  align="center">
							    ${borrowTender.tender_amount}
							</th>
							<th  align="center">
							    <fmt:formatDate value="${borrowTender.tender_addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
							    ${TENDER_ALL_STATUS[borrowTender.tender_status]}
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