<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="borrowTransfer/v_getBorrowTransferList?right_id=${right_id}"
		method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<input type="hidden" name="rbCashStatus" value=0 >
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名称:
							<input type="text" name="userAccount" value="${userAccount}" />
						</td>
						<td>
							名称:
							<input type="text" name="borrowTitle" value="${borrowTitle}" />
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrowTransfer/v_getBorrowTransferAuctionList')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?transferId={sid_support}&right_id=${right_id}"
									target="navTab"><span>${rightsub.moduleName}</span>
								</a>
							</li>
							<li class="line">
								line
							</li>
						</c:if>
						
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrowTransfer/v_toExcel')}">
							<li>
								<a class="add" href="${rightsub.moduleUrl}"><span>${rightsub.moduleName}</span>
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
						<th width="10%" align="center">借款标题</th>
						<th width="10%" align="center">转让人</th>
						<th width="5%" align="center">年收益率</th>
						<th width="10%" align="center">待收本金</th>
						<th width="10%" align="center">转让金额</th>
						<th width="10%" align="center">最新竞拍价格</th>
						<th width="5%" align="center">手续费</th>
						<th width="10%" align="center">创建时间</th>
						<th width="10%" align="center">结束投标时间</th>
						<th width="10%" align="center">创建ip</th>
						<th width="5%" align="center">状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pm.list }" var="borrowTransfer" varStatus="st">
						<tr target="sid_support" rel="${borrowTransfer.id}">
							<th  align="center">
							   ${pm.pageSize * (pm.currentPage-1) + st.index + 1}
							</th>
							 <th  align="center">
							 <a target="dialog" width="810" height="550" href="borrow/v_getBorrowDetailById?supportID=${borrowTransfer.borrowId}" target="navTab" >${borrowTransfer.borrow_title }</a>
							</th>
							<th  align="center">
							   	${borrowTransfer.user_account }
							</th>
							<th  align="center">
							   	${borrowTransfer.transfer_interest_rate }%
							</th>
							<th  align="center">
							   	${borrowTransfer.repossessed_capital }
							</th>
							<th  align="center">
							   	${borrowTransfer.transfer_money }
							</th>
							<th  align="center">
							   	${borrowTransfer.last_auction_money }
							</th>
							<th  align="center">
							   	${borrowTransfer.transfer_fee }
							</th>
							<th  align="center">
							   	<fmt:formatDate value="${borrowTransfer.create_time }" pattern="yyyy-MM-dd  HH:mm:ss" />
							</th>
							<th  align="center">
							   	<fmt:formatDate value="${borrowTransfer.end_time }" pattern="yyyy-MM-dd  HH:mm:ss" />
							</th>
							<th  align="center">
							   ${borrowTransfer.create_ip }
							</th>
							<th  align="center">
								${BORROW_TRANSFER_STATUS[borrowTransfer.transfer_status] } 
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
