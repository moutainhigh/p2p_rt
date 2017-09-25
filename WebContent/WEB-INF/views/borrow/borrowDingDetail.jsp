<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="" method="post">
		<!-- <div class="pageHeader">
			<input type="hidden" name="init" value=0>
		</div> -->
		<div class="pageContent">
			<!-- <div class="panelBar">
			<ul class="toolBar">
				</ul>
			</div> -->
			<table class="list" width="100%" layoutH="10">
				<thead>
					<tr>
						<th width="5%"  align="center">序号</th>
						<th width="12%" align="center">借款标题</th>
						<th width="9%" align="center">借款金额</th>
						<th width="9%" align="center">已投金额</th>
						<th width="9%" align="center">投资进度</th>
						<th width="6%" align="center">利率</th>
						<th width="9%" align="center">借款期限</th>
						<th width="11%" align="center">发布时间</th>
						<th width="9%" align="center">状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="borrow" items="${borrow }">
						<tr  target="sid_support" rel="${borrow.id }">
							<th  align="center">
							    ${borrow.id}
							</th>
							<th  align="center">
								${borrow.borrowTitle}
							</th>
							<th  align="center">
								${borrow.borrowSum}
							</th>
							<th  align="center">
								${borrow.tenderSum}
							</th>
							<th  align="center">
							 	<c:if test="${not empty borrow.tenderProgressRate}">
								 	<fmt:formatNumber value="${borrow.tenderProgressRate}" pattern="#.##"/>%
								 </c:if>
								 <c:if test="${ empty borrow.tenderProgressRate}">
								 	0%
								 </c:if>
							</th>
							<th  align="center">
							    <fmt:formatNumber value="${borrow.annualInterestRate}" pattern="#.##"/>%
							</th>
							
								<th  align="center">
									${borrow.borrowTimeLimit}个月
									<%-- <c:if test="${borrow.isDay eq 1 }">天</c:if> 
									<c:if test="${borrow.isDay eq 2 }">月</c:if>--%>
								</th>
							<th  align="center">
								<fmt:formatDate value="${borrow.publishDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
							    ${BORROW_ALL_STATUS[borrow.borrowStatus] }
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