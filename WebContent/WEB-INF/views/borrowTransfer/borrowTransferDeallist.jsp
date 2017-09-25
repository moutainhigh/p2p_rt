<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="borrowTransfer/v_getBorrowTransferDealList?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							借款账号：
							<input type="text" name="userAccount" id="userAccount" value="${searchParams.userAccount }"/>
						</td>				
							<td>
							签订日期：
							<input type="text" size="10" name="beginTime" id="beginTime" value="${searchParams.beginTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
							到<input type="text" size="10" name="endTime" id="endTime" value="${searchParams.endTime}" class="date textInput readonly" datefmt="yyyy-MM-dd"  readonly="readonly"/>
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrow/v_toRepayPDF')}">
							<li>
								<a class="edit"  href="<%=request.getContextPath() %>/{agreePath}" ><span>${rightsub.moduleName}</span>
								</a>
							</li>
						</c:if>
					</c:forEach>
					</ul>
			</div>
			<table class="list" width="100%" layoutH="90">
				<thead>
					<tr>
						<th width="5%"  align="center">序号</th>
						<th width="7%" align="center">转让者</th>
						<th width="7%" align="center">受让者</th>
						<th width="9%" align="center">转让债权（元）</th>
						<th width="9%" align="center">受让价格</th>
						<th width="9%" align="center">转让日期</th>
						<th width="9%" align="center">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="repaymentBorrow" items="${pm.list }" varStatus="status">
						<tr  target="agreePath" rel="${repaymentBorrow.agreePath}">
							<th  align="center">
							    ${status.count}
							</th>
							<th  align="center">
							    ${repaymentBorrow.userAccount}
							</th>
							<th  align="center">
							    ${repaymentBorrow.tansferAccount}
							</th>
							<th  align="center">
								 ${repaymentBorrow.transferMoney}
							</th>
							<th  align="center">
								${repaymentBorrow.lastMoney}
							</th>
							<th  align="center">
								${repaymentBorrow.agreeTime}
							</th>
							<th  align="center">
								<a href="borrowTransfer/v_showPDF?agreePath=${repaymentBorrow.agreePath }" target="dialog" width="1000" height="800" title="查看合同">查看</a>
								<a href="<%=request.getContextPath() %>${repaymentBorrow.agreePath}">下载</a>
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