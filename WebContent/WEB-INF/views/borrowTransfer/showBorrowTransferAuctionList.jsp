<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="borrowTransfer/v_getBorrowTransferAuctionList?right_id=${right_id}"
		method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<input type="hidden" name="transferId" value="${transferId }" >
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名称:
							<input type="text" name="userAccount" value="${userAccount}" />
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
					
				</ul>
			</div>
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th width="10%"  align="center">序号</th>
						<th width="10%" align="center">竞拍人</th>
						<th width="10%" align="center">竞拍价格</th>
						<th width="10%" align="center">当前年利率</th>
						<th width="10%" align="center">竞拍时间</th>
						<th width="10%" align="center">状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pm.list }" var="borrowTransferAuction" varStatus="st">
						<tr target="sid_support" rel="${borrowTransferAuction.id}">
							<th  align="center">
							   ${pm.pageSize * (pm.currentPage-1) + st.index + 1}
							</th>
							 <th  align="center">
							 	${borrowTransferAuction.userAccount }
							</th>
							<th  align="center">
							   	${borrowTransferAuction.auction_money }
							</th>
							<th  align="center">
							   	${borrowTransferAuction.cur_interest_rate }%
							</th>
							<th  align="center">
								<fmt:formatDate value="${borrowTransferAuction.create_time }" pattern="yyyy-MM-dd  HH:mm:ss" />
							</th>
							<th  align="center">
							<c:if test="${borrowTransferAuction.status==1 }">
							   	有效
							</c:if>
							<c:if test="${borrowTransferAuction.status==2 }">
							   	无效
							</c:if>
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
