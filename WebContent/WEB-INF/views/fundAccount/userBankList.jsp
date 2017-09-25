<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<form id="pagerForm" onsubmit="return navTabSearch(this);"
	action="userBank/${PRE_PATH_VIEW }getUserBankList?right_id=${right_id}"
	method="post">
	<div class="pageHeader">
		<input type="hidden" name="init" value=0>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>用户名： <input type="text" name="userAccount"
						value="${searchParams.userAccount }" />
					</td>
					<td>银行卡号： <input type="text" name="bankAccount"
						value="${searchParams.bankAccount }" />
					</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">查 询</button>
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
					<c:if test="${fn:contains(rightsub.moduleUrl,'add')}">
						<li><a class="add"
							href="${rightsub.moduleUrl}?right_id=${right_id}" target="dialog"
							rel="jbsxBoxUserBank" width="800"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
					</c:if>
					<c:if test="${fn:contains(rightsub.moduleUrl,'update')}">
						<li><a class="edit"
							href="${rightsub.moduleUrl}?id={userBankId}&right_id=${right_id}"
							target="dialog" rel="jbsxBoxUserBank" width="800"><span>${rightsub.moduleName}</span>
						</a></li>
						<li class="line">line</li>
					</c:if>

					<c:if test="${fn:contains(rightsub.moduleUrl,'userBank/o_delete')}">
						<li><a class="delete"
							href="${rightsub.moduleUrl}?id={userBankId}&right_id=${right_id}"
							target="ajaxTodo" rel="jbsxBoxUserBank" title="确认要删除银行账号？"><span>${rightsub.moduleName}</span>
						</a></li>
						<li class="line">line</li>
					</c:if>

					<c:if
						test="${fn:contains(rightsub.moduleUrl,'userBank/o_toExcel')}">
						<li><a class="add"
							href="${rightsub.moduleUrl}?right_id=${searchParams.right_id}&&userAccount=${searchParams.userAccount }&&bankAccount=${searchParams.bankAccount }"><span>${rightsub.moduleName}</span></a>
						</li>
						<li class="line">line</li>
					</c:if>
					
				</c:forEach>
			</ul>
		</div>
		<table class="list" width="100%" layoutH="90" fetchSize="200">
			<thead>
				<tr>
					<th width="10%" align="center">序&nbsp;&nbsp;号</th>
					<th width="10%" align="center">用户名</th>
					<th width="10%" align="center">真实姓名</th>
					<th width="15%" align="center">银行卡号</th>
					<th width="10%" align="center">开户银行</th>
					<th width="10%" align="center">银行分行</th>
					<th width="15%" align="center">创建时间</th>
					<th width="15%" align="center">创建ip地址</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="userBank" items="${pm.list }">
					<tr target="userBankId" rel="${userBank.id }">
						<th align="center">${userBank.id }</th>
						<th align="center">${userBank.userAccount }</th>
						<th align="center">${userBank.userRealname }</th>
						<th align="center">${userBank.bankAccount}</th>
						<th align="center">${userBank.bankName }</th>
						<th align="center">${userBank.bankBranch }</th>
						<th align="center"><fmt:formatDate
								value="${ userBank.bankAddtime }" pattern="yyyy-MM-dd HH:mm:ss" />
						</th>
						<th align="center">${userBank.bankAddip }</th>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<!-- 分页 -->
		<c:set var="page" value="${pm }"></c:set>
		<%@ include file="../page.jsp"%>
	</div>
</form>

