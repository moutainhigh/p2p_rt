<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
%>

	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="borrowRedeem/${PRE_PATH_VIEW }getBorrowRedeemList?right_id=${right_id}" method="post">
		<div class="pageHeader">
			<input type="hidden" name="init" value=0>
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							用户名：
							<input type="text" name="userAccount" id="userAccount" value="${params.userAccount }"/>
						</td>
						<td>
							状态：
							<select name="redeemStatus">
								<option value="0">全部</option>
								<c:forEach var="item" items="${BorrowRedeem_ALL_STATUS}">   
								<option value="${item.key}">${item.value}</option>
								</c:forEach>
							</select>
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
						<c:if test="${fn:contains(rightsub.moduleUrl,'borrowRedeem/o_checkBorrowRedeem')}">
							<li>
								<a class="edit" href="${rightsub.moduleUrl}?supportID={sid_support}&right_id=${right_id}&type=toJsp"
									target="navTab" width="800" height="600" ><span>${rightsub.moduleName}</span>
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
						<th width="10%" align="center">用户名称</th>
						<th width="5%" align="center">赎回金额</th>
						<th width="5%" align="center">手续费</th>
						<th width="5%" align="center">到账金额</th>
						<th width="10%" align="center">申请时间</th>
						<th width="10%" align="center">申请ip</th>
						<th width="10%" align="center">审核人</th>
						<th width="10%" align="center">审核时间</th>
						<th width="10%" align="center">审核ip</th>
						<th width="5%" align="center">状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="borrowRedeem" items="${pm.list }" varStatus="st">
						<tr  target="sid_support" rel="${borrowRedeem.id }">
							<th  align="center">
							    ${pm.pageSize * (pm.currentPage-1) + st.index + 1}
							</th>
							<th  align="center">
							    ${borrowRedeem.borrow_title}
							</th>
							<th  align="center">
							    ${borrowRedeem.user_account}
							</th>
							<th  align="center">
								 ${borrowRedeem.redeem_money}
							</th>
							<th  align="center">
								${borrowRedeem.redeem_fee}
							</th>
							<th  align="center">
								${borrowRedeem.redeem_backMoney}
							</th>
							<th  align="center">
								<fmt:formatDate value="${borrowRedeem.create_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
								${borrowRedeem.create_ip}
							</th>
							<th  align="center">
								${borrowRedeem.auditUserAccount}
							</th>
							<th  align="center">
								<fmt:formatDate value="${borrowRedeem.audit_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</th>
							<th  align="center">
								${borrowRedeem.audit_ip}
							</th>
							<th  align="center">
							    ${BorrowRedeem_ALL_STATUS[borrowRedeem.redeem_status] }
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