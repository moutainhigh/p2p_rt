<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="borrow/v_borrowDeal?right_id=${right_id}" method="post">
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrow/v_toExcelBorrowDeal')}">
							<li>
								<a class="add"  href="${rightsub.moduleUrl }" ><span>${rightsub.moduleName}</span>
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
						<th width="7%" align="center">借款标题</th>
						<th width="7%" align="center">借款账号</th>
						<th width="9%" align="center">姓名</th>
						<th width="9%" align="center">身份证</th>
						<th width="9%" align="center">投标人</th>
						<th width="9%" align="center">签订日期</th>
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
								<a href="borrow/v_getBorrowDetailById?supportID=${repaymentBorrow.borrowId}&right_id=${right_id}"  target="dialog"  width="850" height="600" > ${repaymentBorrow.borrowTitle}</a>
							</th>
							<th  align="center">
							    ${repaymentBorrow.userAccount}
							</th>
							<th  align="center">
								 ${repaymentBorrow.userRealName}
							</th>
							<th  align="center">
								${repaymentBorrow.cardNumber}
							</th>
							<th  align="center">
								${repaymentBorrow.tenderUserAccount}
							</th>
							<th  align="center">
								${repaymentBorrow.agreementTime}
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